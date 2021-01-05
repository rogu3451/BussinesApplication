package pl.busman.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import pl.busman.project.exception.ProjectException.ProjectError;
import pl.busman.project.exception.ProjectException.ProjectException;
import pl.busman.project.model.Project;
import pl.busman.project.model.SystemUser;
import pl.busman.project.repository.ProjectRespository;
import pl.busman.project.repository.SystemUserRepository;
import pl.busman.project.service.validation.ProjectValidation;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectRespository projectRespository;

    @Autowired
    SystemUserRepository systemUserRepository;

    @Autowired
    ProjectValidation projectValidation;

    public List<Project> getAllProjects() {
        return projectRespository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public List<Project> getAllProjectsByUsername(String username) {
        Long usernameId = systemUserRepository.getIdByUsername(username);
        return (List<Project>) projectRespository.getAllProjectsByUserId(usernameId);
    }

    public void addProject(Project project, BindingResult bindingResult, Model model) {
        if(projectValidation.validateProject(bindingResult,project,model)){
            projectRespository.save(project);
            Project emptyProject = new Project();
            model.addAttribute("project",emptyProject);
            model.addAttribute("successMessage","The project has been added.");
        }else{
            model.addAttribute("errorMessage","There were errors.");
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
