package pl.busman.project.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private Long project_id;

    @NotNull
    private Long employee_id;

    @NotNull
    private String title;

    private String description;

    @NotNull
    private String status = "NEW";

    private Double neededTime = 0.0;

    @NotNull
    private LocalDateTime dateOfCreation = LocalDateTime.now();

    private Double cost = 0.0;

    private LocalDateTime dateOfEnd;

    public Task(){}

    public Task(Long id, Long project_id,  Long employee_id,  String title, String description,  String status, Double neededTime,  LocalDateTime dateOfCreation, Double cost, LocalDateTime dateOfEnd) {
        this.id = id;
        this.project_id = project_id;
        this.employee_id = employee_id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.neededTime = neededTime;
        this.dateOfCreation = dateOfCreation;
        this.cost = cost;
        this.dateOfEnd = dateOfEnd;
    }

}
