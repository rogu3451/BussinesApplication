package pl.busman.project.service.validation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import pl.busman.project.model.Project;
import pl.busman.project.model.Task;
import pl.busman.project.model.dto.DataReportCustomerForm;
import pl.busman.project.model.dto.DataReportEmployeeForm;
import pl.busman.project.repository.ProjectRespository;
import pl.busman.project.repository.ReportRepository;
import pl.busman.project.repository.SystemUserRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReportValidation {

    @Autowired
    SystemUserRepository systemUserRepository;

    @Autowired
    ReportRepository reportRepository;

    @Autowired
    ProjectRespository projectRespository;

    public boolean validateEmployeeFormData(DataReportEmployeeForm data, BindingResult bindingResult, Model model, String employeeUsername, String convertedDate) { // used while adding

        if(bindingResult.hasErrors() | checkIfEmailIsNull(data.getEmail(),model)){
            bindingResult.getAllErrors().forEach(error -> {
                System.out.println(error.getObjectName() + " " + error.getDefaultMessage());
            });
            return false; // something went wrong
        }else if(checkIfDoneTasksExistsByEmployeeUsername(employeeUsername,model,convertedDate)){
            return false;
        }
        else {
            model.addAttribute("successMessage","We sent report to email: "+data.getEmail());
            return true; // validation correct
        }
    }


    public boolean validateCustomerFormData(DataReportCustomerForm data, BindingResult bindingResult, Model model, String customerUsername) {
        if(bindingResult.hasErrors() | checkIfEmailIsNull(data.getEmail(),model)){
            bindingResult.getAllErrors().forEach(error -> {
                System.out.println(error.getObjectName() + " " + error.getDefaultMessage());
            });
            return false; // something went wrong
        }else if(!checkIfProjectIdIsCorrect(data.getProjectId(),model,customerUsername)) {
            return false; // something went wrong
        }else if(!checkIfAllTasksAreCompletedByProjectId(data.getProjectId(),model)){
            return false; // something went wrong
        }
        else {
            model.addAttribute("successMessage","We sent report to email: "+data.getEmail());
            return true; // validation correct
        }
    }


    private boolean checkIfDoneTasksExistsByEmployeeUsername(String username, Model model,String convertedDate){
        Long employeeId = systemUserRepository.getIdByUsername(username);
        List<Project> projects= reportRepository.getProjectsByEmployeeAndDateIdWhereExistsDoneTasks(employeeId,convertedDate);
        if(projects.isEmpty()){
            model.addAttribute("anyDoneTasks","We couldn't generate report for you because you don't have any completed tasks in this date");
            return true;
        }else{
            return false;
        }
    }

    private boolean checkIfProjectIdIsCorrect(Long projectId, Model model, String username) {
        Long customerId = systemUserRepository.getIdByUsername(username);
        List<Project> project = projectRespository.getProjectByCustomerIdAndProjectId(customerId, projectId);
        if (project.isEmpty()) {
            model.addAttribute("customerAuthorization","Incorrect project id");
            return false;
        } else {
            return true;
        }
    }


    private boolean checkIfAllTasksAreCompletedByProjectId(Long projectId, Model model){
        List<Task> tasks = new ArrayList<>();

        if(reportRepository.getAllUncompletedTasksByProjectId(projectId).isEmpty()){
            tasks=reportRepository.getAllDoneTasksByProjectId(projectId);
        }

        if(tasks.isEmpty()){
            model.addAttribute("projectNotFinish","We couldn't generate report for you because the project is not completed yet");
            return false;
        }else{
            return true;
        }
    }

    private boolean checkIfEmailIsNull(String email, Model model) {
        if(email.isEmpty()){
            model.addAttribute("emptyEmail","Email can not be empty");
            return true;
        }else{
            return false;
        }
    }


}
