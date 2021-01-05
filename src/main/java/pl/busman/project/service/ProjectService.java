package pl.busman.project.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import pl.busman.project.model.Project;
import pl.busman.project.model.dto.ProjectWithCustomerDetalis;

import java.util.List;


public interface ProjectService {

    List<Project> getAllProjects();

    List<Project> getAllProjectsByUsername(String username);

    void addProject(Project project, BindingResult bindingResult, Model model);

    Project getProject(Long id);

    void deleteProject(Long id);


    List<ProjectWithCustomerDetalis> getAllProjectsWithCustomerDetails();

    void updateProject(Project project, BindingResult bindingResult, Model model);
}
