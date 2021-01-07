package pl.busman.project.service.validation;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import pl.busman.project.model.dto.DataReportEmployeeForm;

@Component
public class ReportValidation {
    public boolean validateEmployeeFormData(DataReportEmployeeForm data, BindingResult bindingResult, Model model) { // used while adding

        if(bindingResult.hasErrors() | checkIfEmailIsNull(data.getEmail(),model)){
            bindingResult.getAllErrors().forEach(error -> {
                System.out.println(error.getObjectName() + " " + error.getDefaultMessage());
            });
            return false; // something went wrong
        }else {
            model.addAttribute("successMessage","We sent report to email: "+data.getEmail());
            return true; // validation correct
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
