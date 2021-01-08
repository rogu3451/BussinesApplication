package pl.busman.project.service.validation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import pl.busman.project.model.Project;
import pl.busman.project.model.dto.DataReportEmployeeForm;
import pl.busman.project.repository.ReportRepository;
import pl.busman.project.repository.SystemUserRepository;

import java.util.List;

@Component
public class ReportValidation {

    @Autowired
    SystemUserRepository systemUserRepository;

    @Autowired
    ReportRepository reportRepository;

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
    private boolean checkIfDoneTasksExistsByEmployeeUsername(String username, Model model,String convertedDate){
        Long employeeId = systemUserRepository.getIdByUsername(username);
        List<Project> projects= reportRepository.getProjectsByEmployeeAndDateIdWhereExistsDoneTasks(employeeId,convertedDate);
        if(projects.isEmpty()){
            model.addAttribute("anyDoneTasks","We couldn't generate report for you because you don't have any completed tasks");
            return true;
        }else{
            return false;
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
