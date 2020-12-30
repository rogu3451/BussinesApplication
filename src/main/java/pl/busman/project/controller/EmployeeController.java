package pl.busman.project.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.busman.project.model.Project;

import java.util.List;

@Controller()
@RequestMapping("/employee")
public class EmployeeController {

    @GetMapping("/allProjects")
    public String allProjects(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        System.out.println(currentPrincipalName);
        return "employee/allProjects";
    }
}
