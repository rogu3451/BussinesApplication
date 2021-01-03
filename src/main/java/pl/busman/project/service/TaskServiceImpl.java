package pl.busman.project.service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.busman.project.model.Task;
import pl.busman.project.repository.SystemUserRepository;
import pl.busman.project.repository.TaskRepository;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    SystemUserRepository systemUserRepository;

    public void addTask(Task task) {
        taskRepository.save(task);
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
            task.setDateOfEnd(LocalDateTime.now());
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
