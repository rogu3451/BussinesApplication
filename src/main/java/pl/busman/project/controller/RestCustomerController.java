package pl.busman.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.busman.project.model.Project;
import pl.busman.project.service.ProjectService;
import pl.busman.project.service.Utils;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class RestCustomerController {

    @Autowired
    ProjectService projectService;

    @GetMapping("/getAllProjects")
    public List<Project> getProjectsForSpecificCustomer(){
        return projectService.getAllProjectsForCustomerByUsername(Utils.getCurrentUserName());
    }

}
