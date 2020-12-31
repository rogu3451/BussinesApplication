package pl.busman.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.busman.project.model.Project;
import pl.busman.project.model.Task;
import pl.busman.project.service.ProjectService;
import pl.busman.project.service.TaskService;

import java.util.List;


@Controller()
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    ProjectService projectService;

    @Autowired
    TaskService taskService;

    @GetMapping("/myProjects")
    public String allProjects(Model model){
        List<Project> myProjects = projectService.getAllProjectsByUsername(getCurrentUserName(model));
        model.addAttribute("project",myProjects);
        return "employee/myProjects";
    }

    @GetMapping("/project/{id}/tasks")
    public String myTasksInSpecificProject(@PathVariable("id") Long projectId, Model model){

        List<Task> tasks = taskService.getAllTasksByUsernameAndProjectId(getCurrentUserName(model),projectId);
        model.addAttribute("tasks",tasks);
        model.addAttribute("projectId",projectId);
        return "employee/myTasks";
    }


    private String getCurrentUserName(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        model.addAttribute("name",currentPrincipalName);
        return  currentPrincipalName;
    }
}
