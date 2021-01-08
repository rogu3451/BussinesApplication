package pl.busman.project.model.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import pl.busman.project.model.Task;

import java.util.*;


@Getter
@Setter
@Data
public class EmployeeReportBuilder {

    String projectTitle;
    List<Task> projectTasks;

    public EmployeeReportBuilder(String projectTitle){
        this.projectTitle = projectTitle;
    }

}
