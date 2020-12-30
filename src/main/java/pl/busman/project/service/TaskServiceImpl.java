package pl.busman.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.busman.project.model.Task;
import pl.busman.project.repository.TaskRepository;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskRepository taskRepository;

    public void addTask(Task task) {
        taskRepository.save(task);
    }
}
