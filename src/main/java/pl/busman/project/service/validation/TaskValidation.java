package pl.busman.project.service.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import pl.busman.project.model.SystemUser;
import pl.busman.project.model.Task;
import pl.busman.project.service.SystemUserService;
import pl.busman.project.service.TaskService;

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
        }

        if(task.getDescription().length() > 5000){
            model.addAttribute("tooLongDescription","Description should have maximum 5000 characters. Now is "+task.getDescription().length()+" characters in your description.");
            errors++;
        }

        if(errors!=0){
            return false; // something went wrong
        }else{
            return true; // validation correct
        }
    }

    public boolean validateTasks(List<Task> tasksAfterModification, String currentUsername, Long projectId){  // Request from Employee Controller
        List<Task> tasksBeforeModification = taskService.getAllTasksByUsernameAndProjectId(currentUsername,projectId);
        int numberOfTasks = tasksBeforeModification.size();
        int numberOfDifferences = 0;

        for(int i=0; i<numberOfTasks; i++){
            System.out.println("Int i: "+i + "A");
            if(!tasksBeforeModification.get(i).getStatus().equals(tasksAfterModification.get(i).getStatus())){
                numberOfDifferences++;
            }
            System.out.println("Int i: "+i + "B");
            if(!tasksBeforeModification.get(i).getNeededTime().equals(tasksAfterModification.get(i).getNeededTime())){
                    numberOfDifferences++;
            }

        }


        if(numberOfDifferences!=0){
            return true; // There were any differences so we can update the data
        }else{
            return false; // There were not any differences so we sould not update the data
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
