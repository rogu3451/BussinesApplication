package pl.busman.project.service;

import java.util.List;
import pl.busman.project.model.Task;

public interface TaskService {

    void addTask(Task task);

    void saveTasks(List<Task> tasks);

    List<Task> getAllTasksById(Long id);

    List<Task> getAllTasksByUsernameAndProjectId(String username, Long projectId);



}
