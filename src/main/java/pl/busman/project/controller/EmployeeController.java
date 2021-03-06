package pl.busman.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.busman.project.model.Project;
import pl.busman.project.model.Task;
import pl.busman.project.model.dto.DataReportEmployeeForm;
import pl.busman.project.model.dto.TaskCreationDto;
import pl.busman.project.service.ProjectService;
import pl.busman.project.service.ReportGeneratorService;
import pl.busman.project.service.TaskService;
import pl.busman.project.service.Utils;
import pl.busman.project.service.emailSender.EmailSender;
import pl.busman.project.service.validation.TaskValidation;

import javax.validation.Valid;
import java.util.List;


@Controller()
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    ProjectService projectService;

    @Autowired
    TaskService taskService;

    @Autowired
    TaskValidation taskValidation;

    @Autowired
    ReportGeneratorService reportGeneratorService;

    @GetMapping("/myProjects")
    public String allProjects(Model model){
        List<Project> myProjects = projectService.getAllProjectsForEmployeeByUsername(Utils.getCurrentUserName(model));
        model.addAttribute("project",myProjects);
        return "employee/myProjects";
    }

    @GetMapping("/project/{projectId}/tasks")
    public String myTasksInSpecificProject(@PathVariable("projectId") Long projectId, Model model){
        List<Task> tasks = taskService.getAllTasksByUsernameAndProjectId(Utils.getCurrentUserName(model),projectId);
        TaskCreationDto taskForm = new TaskCreationDto(tasks);
        model.addAttribute("taskForm",taskForm);
        model.addAttribute("projectId",projectId);
        return "employee/myTasks";
    }

    @PostMapping("/project/{projectId}/tasks")
    public String saveTasks(@PathVariable("projectId") Long projectId, TaskCreationDto taskForm, Model model, BindingResult bindingResult){
        if(taskValidation.validateTasks(taskForm.getTasks(), Utils.getCurrentUserName(model), projectId, bindingResult)){
            taskService.updateTasks(taskForm.getTasks());
            model.addAttribute("successMessage","Tasks modified");
            model.addAttribute("projectId",projectId);
            model.addAttribute("taskForm",taskForm);
        }else{
            Utils.getCurrentUserName(model);
            model.addAttribute("nothingHasChanged","Nothing has changed");
            model.addAttribute("errorMessage","There were errors");
            model.addAttribute("projectId",projectId);
            model.addAttribute("taskForm",taskForm);
        }

        return "employee/myTasks";
    }

    @GetMapping("/generateReport")
    public String generateMonthlyReport(Model model){
        String currentUserName = Utils.getCurrentUserName(model);
        DataReportEmployeeForm data = new DataReportEmployeeForm();
        model.addAttribute("data", data);
        return "employee/generateReport";
    }

    @PostMapping("/generateReport")
    public String generateMonthlyReport(@Valid @ModelAttribute("data") DataReportEmployeeForm data, BindingResult bindingResult, Model model){
        String currentUserName = Utils.getCurrentUserName(model);
        reportGeneratorService.sendReport(data,bindingResult,model,currentUserName);
        return "employee/generateReport";
    }

}
