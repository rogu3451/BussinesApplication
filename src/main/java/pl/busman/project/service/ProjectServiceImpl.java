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
import pl.busman.project.model.dto.ProjectWithCustomerDetalis;
import pl.busman.project.repository.ProjectRespository;
import pl.busman.project.repository.RoleRepository;
import pl.busman.project.repository.SystemUserRepository;
import pl.busman.project.service.validation.ProjectValidation;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectRespository projectRespository;

    @Autowired
    SystemUserRepository systemUserRepository;

    @Autowired
    ProjectValidation projectValidation;

    @Autowired
    RoleRepository roleRepository;

    public List<Project> getAllProjects() {
        return projectRespository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }


    public List<Project> getAllProjectsForEmployeeByUsername(String username) {
        Long employeeId = systemUserRepository.getIdByUsername(username);
        return (List<Project>) projectRespository.getAllProjectsForEmployeeById(employeeId);
    }

    @Override
    public List<Project> getAllProjectsForCustomerByUsername(String username) {
        Long customerId = systemUserRepository.getIdByUsername(username);
        return (List<Project>) projectRespository.getAllProjectsForCustomerById(customerId);
    }

    public void addProject(Project project, BindingResult bindingResult, Model model) {
        if(projectValidation.validateProject(bindingResult,project,model,false)){
            projectRespository.save(project);
            Project emptyProject = new Project();
            model.addAttribute("project",emptyProject);
            model.addAttribute("successMessage","The project has been added.");
        }else{
            model.addAttribute("errorMessage","There were errors.");
        }
    }

    @Transactional
    public void updateProject(Project project, BindingResult bindingResult, Model model) {
        if(projectValidation.validateProject(bindingResult,project,model,true)){
            projectRespository.updateProject(project.getId(),project.getName(), project.getDescription(), project.getCustomerId());
            model.addAttribute("successMessage","The project has been modified.");
        }else{
            model.addAttribute("errorMessage","There were errors.");
        }
    }


    public String getProjectNameById(Long projectId) {
        return projectRespository.getProjectNameById(projectId);
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

    public List<ProjectWithCustomerDetalis> getAllProjectsWithCustomerDetails() {
        return projectRespository.getAllProjectsWithCustomerDetails();
    }



}
