package pl.busman.project.service.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import pl.busman.project.model.Task;
import pl.busman.project.service.TaskService;

import java.util.List;

@Component
public class TaskValidation {

    @Autowired
    TaskService taskService;

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

    public boolean validateTasks(List<Task> tasksAfterModification, String currentUsername, Long projectId){
        List<Task> tasksBeforeModification = taskService.getAllTasksByUsernameAndProjectId(currentUsername,projectId);
        int numberOfTasks = tasksBeforeModification.size();
        System.out.println("Tasks before modification: ");
        System.out.println(tasksBeforeModification);

        System.out.println("Tasks after modification: ");
        System.out.println(tasksAfterModification);

        int numberOfDifferences = 0;

        for(int i=0; i<numberOfTasks; i++){
            if(!tasksBeforeModification.get(i).getStatus().equals(tasksAfterModification.get(i).getStatus())){
                numberOfDifferences++;
            }
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

}
