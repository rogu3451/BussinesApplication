package pl.busman.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.busman.project.model.Project;
import pl.busman.project.model.Role;
import pl.busman.project.model.SystemUser;
import pl.busman.project.model.Task;
import pl.busman.project.model.dto.UserWithRole;
import pl.busman.project.model.dto.UsersWithRoleQuery;
import pl.busman.project.service.ProjectService;
import pl.busman.project.service.RoleService;
import pl.busman.project.service.SystemUserService;
import pl.busman.project.service.TaskService;
import pl.busman.project.service.validation.TaskValidation;
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
    TaskService taskService;

    @Autowired
    UserValidation userValidation;

    @Autowired
    TaskValidation taskValidation;

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

    @GetMapping("/editProject/{id}")
    public String editProject(@PathVariable("id") Long id, Model model) {
        Project project = projectService.getProject(id);
        model.addAttribute("project", project);
        return "admin/editProject";
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

    }

    @GetMapping("/allUsers")
    public String allUsers(Model model){
        List<UsersWithRoleQuery> allSystemUsersWithRole = systemUserService.getAllUsersWithRole();
        model.addAttribute("systemUsers", allSystemUsersWithRole);
        return "admin/allUsers";
    }

    @GetMapping("/editUser/{id}")
    public String editUser(@PathVariable("id") Long id, Model model){
        UsersWithRoleQuery usersWithRoleQuery = systemUserService.getAllUsersWithRoleById(id);
        model.addAttribute("userFromDb", usersWithRoleQuery);
        return "/admin/editUser";

    }

    @PostMapping("/editUser/{id}")
    public String editUser(UsersWithRoleQuery usersWithRoleQuery, BindingResult bindingResult, Model model){

        if(userValidation.validateUser(usersWithRoleQuery, bindingResult, model)){
            systemUserService.updateSystemUser(usersWithRoleQuery);
            model.addAttribute("userFromDb", usersWithRoleQuery);
            model.addAttribute("successMessage","The user has been modified.");
            return "/admin/editUser";
        } else {
            model.addAttribute("userFromDb", usersWithRoleQuery);
            model.addAttribute("errorMessage","There were errors.");
            return "/admin/editUser";
        }
    }

    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") Long id){
            systemUserService.deleteSystemUser(id);
            return "redirect:/admin/allUsers";
    }

    @GetMapping("/editProject/addTask/{id}")
    public String addTask(@PathVariable("id") Long id, Model model){
        Task task = new Task();
        task.setProject_id(id);
        model.addAttribute("task",task);
        return "admin/addTask";
    }

    @PostMapping("/editProject/addTask/{id}")
    public String addTask(Task taskToSave, Model model){
        System.out.println("taskToSave: "+taskToSave);

        if(taskValidation.taskValidation(taskToSave,model)){
            taskService.addTask(taskToSave);
            model.addAttribute("successMessage","Task for project id: "+taskToSave.getProject_id()+" has been added.");
            Task task = new Task();
            task.setProject_id(taskToSave.getProject_id());
            model.addAttribute("task",task);
            return "admin/addTask";
        }else{
            model.addAttribute("task",taskToSave);
            model.addAttribute("errorMessage","There were errors.");
            return "admin/addTask";
        }
    }

    @GetMapping("/project/{id}/tasks")
    public String projectsTasks(@PathVariable("id") Long id, Model model){
        List<Task> tasks = taskService.getAllTasksById(id);
        model.addAttribute("tasks",tasks);
        model.addAttribute("projectId",id);
        return "admin/allTasks";
    }

}
