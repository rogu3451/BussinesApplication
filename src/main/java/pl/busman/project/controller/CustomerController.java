package pl.busman.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.busman.project.model.Project;
import pl.busman.project.model.Task;
import pl.busman.project.model.dto.DataReportCustomerForm;
import pl.busman.project.service.ProjectService;
import pl.busman.project.service.ReportGeneratorService;
import pl.busman.project.service.TaskService;
import pl.busman.project.service.Utils;

import javax.validation.Valid;
import java.util.List;



@Controller()
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    ProjectService projectService;

    @Autowired
    TaskService taskService;

    @Autowired
    ReportGeneratorService reportGeneratorService;


    @GetMapping("/myProjects")
    public String allProjects(Model model){
        List<Project> myProjects = projectService.getAllProjectsForCustomerByUsername(Utils.getCurrentUserName(model));
        model.addAttribute("project",myProjects);
        return "customer/myProjects";
    }

    @GetMapping("/project/{projectId}/progress")
    public String getAllTasksForSpecificProject(@PathVariable("projectId") Long projectId, Model model){
        List<Task> tasks = taskService.getAllTasksByProjectId(projectId);
        model.addAttribute("tasks",tasks);
        model.addAttribute("projectId",projectId);
        return "customer/tasks";
    }

    @GetMapping("/generateReport")
    public String generateFinancialReportForCustomer(Model model){
        String currentUserName = Utils.getCurrentUserName(model);
        DataReportCustomerForm data = new DataReportCustomerForm();
        model.addAttribute("data", data);
        return "customer/generateReport";
    }

    @PostMapping("/generateReport")
    public String generateFinancialReportForCustomer(@Valid @ModelAttribute("data") DataReportCustomerForm data, BindingResult bindingResult, Model model){
        String currentUserName = Utils.getCurrentUserName(model);
        reportGeneratorService.sendReport(data,bindingResult,model,currentUserName);
        return "customer/generateReport";
    }

}
