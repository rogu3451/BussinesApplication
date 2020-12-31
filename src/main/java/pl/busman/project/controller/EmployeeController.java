package pl.busman.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.busman.project.model.Project;
import pl.busman.project.service.ProjectService;

import java.util.List;


@Controller()
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    ProjectService projectService;

    @GetMapping("/allProjects")
    public String allProjects(Model model){
        List<Project> myProjects = projectService.getAllProjectsByUsername(getCurrentUserName(model));
        model.addAttribute("project",myProjects);
        return "employee/allProjects";
    }

    private String getCurrentUserName(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        model.addAttribute("name",currentPrincipalName);
        return  currentPrincipalName;
    }
}
