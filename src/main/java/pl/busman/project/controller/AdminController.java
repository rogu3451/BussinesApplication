package pl.busman.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.busman.project.model.Project;
import pl.busman.project.service.ProjectService;

import javax.validation.Valid;
import java.util.List;

@Controller()
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    ProjectService projectService;

    @GetMapping("")
    public String login(){
        return "login";
    }

    @GetMapping("/addProject")
    public String addProject(Model model){
        Project project = new Project();
        model.addAttribute("project",project);
        return "admin/addProject";
    }

    @GetMapping("/allProjects")
    public String allProjects(Model model){
        List<Project> allProjects = projectService.getAllProjects();
        model.addAttribute("project",allProjects);
        return "admin/allProjects";
    }

    @PostMapping("/addProject")
    public String addProject(@Valid Project project, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            System.out.println("There were errors");
            bindingResult.getAllErrors().forEach(error -> {
                System.out.println(error.getObjectName() + " " + error.getDefaultMessage()  );
            });
            return "admin/addProject";
        }else {


            if(project.getId()==null){
                projectService.addProject(project);
                Project emptyProject = new Project();
                model.addAttribute("project",emptyProject);
                model.addAttribute("message","The project has been added.");
            } else {
                projectService.addProject(project);
                model.addAttribute("message","The project has been modified.");
            }

            return "admin/addProject";
        }
    }

    @GetMapping("/addProject/{id}")
    public String editProject(@PathVariable("id") Long id, Model model) {
        Project project = projectService.getProject(id);
        model.addAttribute("project", project);
        return "admin/addProject";
    }

    @GetMapping("/deleteProject/{id}")
    public String deleteProject(@PathVariable("id") Long id){
        projectService.deleteProject(id);
        return "redirect:/admin/allProjects";
    }

}
