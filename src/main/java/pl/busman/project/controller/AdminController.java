package pl.busman.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.busman.project.model.Project;
import pl.busman.project.model.Role;
import pl.busman.project.model.SystemUser;
import pl.busman.project.model.dto.UserWithRole;
import pl.busman.project.service.ProjectService;
import pl.busman.project.service.RoleService;
import pl.busman.project.service.SystemUserService;
import pl.busman.project.service.validation.UserValidation;

import javax.validation.Valid;
import java.util.List;

@Controller()
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    ProjectService projectService;

    @Autowired
    SystemUserService systemUserService;

    @Autowired
    RoleService roleService;

    @Autowired
    UserValidation userValidation;

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

    @GetMapping("/addUser")
    public String addUserToDB(Model model){
        UserWithRole userWithRole = new UserWithRole();
        model.addAttribute("userWithRole", userWithRole);
        return "admin/addUser";
    }

    @PostMapping("/addUser")
    public String addUser(UserWithRole userWithRole, BindingResult bindingResult, Model model){

        if(userValidation.validateUser(userWithRole, bindingResult, model)){
            systemUserService.addSystemUser(userWithRole.getSystemUser());
            userWithRole.getRole().setUsername(userWithRole.getSystemUser().getUsername());
            roleService.addRole(userWithRole.getRole());
            model.addAttribute("successMessage","The user has been added.");
            UserWithRole userWithRoleEmpty = new UserWithRole();  // to clean form
            model.addAttribute("userWithRole", userWithRoleEmpty);
            return "admin/addUser";
        } else {
            model.addAttribute("userWithRole", userWithRole);
            model.addAttribute("errorMessage","There were errors.");
            return "admin/addUser";
        }








        /*if(bindingResult.hasErrors()) {
            System.out.println("There were errors");
            bindingResult.getAllErrors().forEach(error -> {
                System.out.println(error.getObjectName() + " " + error.getDefaultMessage());
            });
            model.addAttribute("userWithRole", userWithRole);
            model.addAttribute("invalidPassword","Invalid password. Password should have minimum 6 characters, one  uppercase letter and one digit.");
            return "admin/addUser";
        }
        else{

                if(userWithRole.getSystemUser().getId()==null)
                {
                        try{
                            systemUserService.addSystemUser(userWithRole.getSystemUser());
                            userWithRole.getRole().setUsername(userWithRole.getSystemUser().getUsername());
                            roleService.addRole(userWithRole.getRole());
                            UserWithRole userWithRoleEmpty = new UserWithRole();
                            model.addAttribute("userWithRole", userWithRoleEmpty);
                            model.addAttribute("message","The user has been added.");
                        }catch (Exception e){
                                if(SystemUser.validatePassword(userWithRole.getSystemUser().getPassword()))
                                {
                                    model.addAttribute("userWithRole", userWithRole);
                                    model.addAttribute("incorrectUsername", "Username already exist!");
                                }else{
                                    model.addAttribute("userWithRole", userWithRole);
                                    model.addAttribute("incorrectUsername","Username should be between 5 and 20 characters.");
                                }
                        }
                }else{
                    try{
                        roleService.addRole(userWithRole.getRole());
                        systemUserService.addSystemUser(userWithRole.getSystemUser());
                        model.addAttribute("message","The user has been modified.");
                    }catch (Exception e){
                        model.addAttribute("incorrectUsername","Username should be between 5 and 20 characters.");
                    }

                    }
                }*/

    }

    @GetMapping("/allUsers")
    public String allUsers(Model model){
        List<SystemUser> allSystemUsers = systemUserService.getAllSystemUsers();
        model.addAttribute("systemUsers", allSystemUsers);
        return "admin/allUsers";
    }

}
