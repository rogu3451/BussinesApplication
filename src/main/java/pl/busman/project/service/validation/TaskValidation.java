package pl.busman.project.service.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import pl.busman.project.model.SystemUser;
import pl.busman.project.model.Task;
import pl.busman.project.service.SystemUserService;
import pl.busman.project.service.TaskService;
import pl.busman.project.service.Utils;

import java.util.List;

@Component
public class TaskValidation {

    @Autowired
    TaskService taskService;

    @Autowired
    SystemUserService systemUserService;

    public boolean taskValidation(Task task, Model model){ // Request from Admin Controller
        int errors = 0;

        if(task.getEmployee_id()==null){
            model.addAttribute("emptyEmployeeId","Employee id can not be empty.");
            errors++;
        }


        if(!checkIfEmployeeExists(task.getEmployee_id(), model)){
            errors++;
        }
        if(task.getTitle()==null){
            model.addAttribute("emptyTitle","Title can not be empty.");
            errors++;
        }

        if(task.getTitle().length()<3){
            model.addAttribute("tooShortTitle","Title should have minimum 3 characters.");
            errors++;
        }else{
            if(Utils.checkIfSpecialCharactersExists(task.getTitle())){
                model.addAttribute("specialCharactersExists1","You can't use special characters in this field");
                errors++;
            }
        }

        if(task.getDescription().length() > 5000){
            model.addAttribute("tooLongDescription","Description should have maximum 5000 characters. Now is "+task.getDescription().length()+" characters in your description.");
            errors++;
        }

        if(task.getDescription().length() < 10){
            model.addAttribute("tooShortDescription","Description should have minimum 10 characters. Now is "+task.getDescription().length()+" characters in your description.");
            errors++;
        }

        if(task.getDescription().length() > 10 && task.getDescription().length() < 5000){
                if(Utils.checkIfSpecialCharactersExists(task.getDescription())){
                    model.addAttribute("specialCharactersExists2","You can't use special characters in this field");
                    errors++;
                }
        }


        if(errors!=0){
            return false; // something went wrong
        }else{
            return true; // validation correct
        }
    }

    public boolean validateTasks(List<Task> tasksAfterModification, String currentUsername, Long projectId, BindingResult bindingResult){  // Request from Employee Controller
        List<Task> tasksBeforeModification = taskService.getAllTasksByUsernameAndProjectId(currentUsername,projectId);
        int numberOfTasks = tasksBeforeModification.size();
        int errors = 0;
        int numberOfDifferences = 0;

        for(int i=0; i<numberOfTasks; i++){

            if(!tasksBeforeModification.get(i).getStatus().equals(tasksAfterModification.get(i).getStatus())){
                numberOfDifferences++;
            }

            if(!tasksBeforeModification.get(i).getNeededTime().equals(tasksAfterModification.get(i).getNeededTime())){
                numberOfDifferences++;
            }

            if(!"NEW".equals(tasksAfterModification.get(i).getStatus()) &&
               !"IN_REALIZATION".equals(tasksAfterModification.get(i).getStatus())  &&
               !"DONE".equals(tasksAfterModification.get(i).getStatus())){
                errors++;
            }

        }


        if(numberOfDifferences!=0 && errors==0){
            return true; // There are differences so we can update the data
        }else{
            return false; // There are not any differences so we sould not update the data
        }

    }


    private boolean checkIfEmployeeExists(Long employeeId,Model model) {
        SystemUser employeeFromDB = systemUserService.getEmployeeById(employeeId);
        if(employeeFromDB==null){
            model.addAttribute("employeeDoesNotExist","Employee with this id does not exist in database");
            return false; // employee does not exist
        }else {
            return true; // employee exists
        }
    }

}
