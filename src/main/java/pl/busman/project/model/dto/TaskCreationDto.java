package pl.busman.project.model.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import pl.busman.project.model.Task;

@Getter
@Setter
@Data
public class TaskCreationDto {
    private java.util.List<Task> tasks;

    public TaskCreationDto(java.util.List<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task){
        this.tasks.add(task);
    }

}
