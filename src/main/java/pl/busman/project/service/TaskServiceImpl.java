package pl.busman.project.service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import pl.busman.project.model.Task;
import pl.busman.project.repository.SystemUserRepository;
import pl.busman.project.repository.TaskRepository;
import pl.busman.project.service.validation.TaskValidation;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TaskValidation taskValidation;

    @Autowired
    SystemUserRepository systemUserRepository;

    public void addTask(Task taskToSave, Model model) {
        if(taskValidation.taskValidation(taskToSave,model)){
            taskRepository.save(taskToSave);
            model.addAttribute("successMessage","Task for project id: "+taskToSave.getProject_id()+" has been added.");
            Task task = new Task();
            task.setProject_id(taskToSave.getProject_id());
            model.addAttribute("task",task);
        }else{
            model.addAttribute("task",taskToSave);
            model.addAttribute("errorMessage","There were errors.");
        }

    }

    public void updateTasks(List<Task> tasks) {
        for(Task task : tasks){
            checkStatus(task);
            calculateCostForSingleTask(task);
            taskRepository.updateTask(task.getStatus(),task.getNeededTime(),task.getCost(),task.getDateOfEnd(),task.getId());
        }
    }

    private void calculateCostForSingleTask(Task task) {
        if(task.getNeededTime()!=null){
            Double costPerHour = 115.00;
            DecimalFormat newFormat = new DecimalFormat("#.##");
            double cost = task.getNeededTime()*costPerHour;
            DecimalFormat df = new DecimalFormat("###.##");
            Double roundedCost = Double.parseDouble(df.format(cost));
            task.setCost(roundedCost);
        }
    }

    private void checkStatus(Task task) {
        if("DONE".equals(task.getStatus()))
        {
            DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            task.setDateOfEnd(LocalDateTime.now().format(date));
        }else{
            task.setDateOfEnd(null);
        }
    }

    public List<Task> getAllTasksById(Long id) {
        return  (List<Task>)taskRepository.findAllByProject_id(id);
    }


    public List<Task> getAllTasksByUsernameAndProjectId(String username, Long projectId) {
        Long usernameId = systemUserRepository.getIdByUsername(username);
        return (List<Task>)taskRepository.findAllByEmployeeIdAndProjectId(usernameId,projectId);
    }

}
