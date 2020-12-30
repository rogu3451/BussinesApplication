package pl.busman.project.service.validation;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import pl.busman.project.model.Task;

@Component
public class TaskValidation {

    public boolean taskValidation(Task task, Model model){

        int errors = 0;

        if(task.getEmployee_id()==null){
            model.addAttribute("emptyEmployeeId","Employee id can not be empty.");
            errors++;
        }


        if(task.getTitle()==null){
            model.addAttribute("emptyTitle","Title can not be empty.");
            errors++;
        }

        if(task.getTitle().length()<3){
            model.addAttribute("tooShortTitle","Title should have minimum 3 characters.");
            errors++;
        }

        if(errors!=0){
            return false; // something went wrong
        }else{
            return true; // validation correct
        }

    }

}
