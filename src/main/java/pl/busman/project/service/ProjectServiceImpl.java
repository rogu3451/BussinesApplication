package pl.busman.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.busman.project.exception.ProjectException.ProjectError;
import pl.busman.project.exception.ProjectException.ProjectException;
import pl.busman.project.model.Project;
import pl.busman.project.model.SystemUser;
import pl.busman.project.repository.ProjectRespository;
import pl.busman.project.repository.SystemUserRepository;

import java.util.List;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectRespository projectRespository;

    @Autowired
    SystemUserRepository systemUserRepository;

    public List<Project> getAllProjects() {
        return projectRespository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public List<Project> getAllProjectsByUsername(String username) {
        Long usernameId = systemUserRepository.getIdByUsername(username);
        return (List<Project>) projectRespository.getAllProjectsByUserId(usernameId);
    }

    public void addProject(Project project) {
        if(project.getId()!=null){
           updateProject(project);
        }else{
            projectRespository.save(project);
        }
    }

    public void updateProject(Project project){
        projectRespository.findById(project.getId())
                .map(projectFromDb -> {
                    projectFromDb.setName(project.getName());
                    projectFromDb.setDescription(project.getDescription());
                    return projectRespository.save(projectFromDb);
                }).orElseThrow(()-> new ProjectException(ProjectError.PROJECT_NOT_FOUND));
    }




    public Project getProject(Long id) {
            return projectRespository.findById(id).orElseThrow(() -> new ProjectException(ProjectError.PROJECT_NOT_FOUND));
    }


    public void deleteProject(Long id) {
        Project project = projectRespository.findById(id).orElseThrow(() -> new ProjectException(ProjectError.PROJECT_NOT_FOUND));
        if(project!=null){
            projectRespository.delete(project);
        }

    }

}
