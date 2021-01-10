package pl.busman.project.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    @Column(length = 5000)
    @Size(min=10, max=5000)
    private String description;

    @NotNull
    private String status = "NEW";

    private Double neededTime = 0.0;

    @Transient
    DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @NotNull
    private String dateOfCreation =  LocalDateTime.now().format(date);


    private Double cost = 0.0;

    private String dateOfEnd;

    public Task(){}

    public Task(Long id, Long project_id,  Long employee_id,  String title, String description,  String status, Double neededTime,  String dateOfCreation, Double cost, String dateOfEnd) {
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
