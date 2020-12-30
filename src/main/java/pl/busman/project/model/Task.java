package pl.busman.project.model;

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

    private Integer neededTime;

    @NotNull
    private LocalDateTime dateOfCreation = LocalDateTime.now();

    private LocalDateTime dateOfEnd;

}
