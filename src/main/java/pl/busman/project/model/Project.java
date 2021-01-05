package pl.busman.project.model;



import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min=2, max=50, message = "Name of project should be between 2 and 50 characters")
    private String name;

    @NotNull
    @Column(length = 5000)
    @Size(min=10, max=5000, message = "Description of project should be between 10 and 5000 characters")
    private String description;

    @NotNull
    @Min(value=0, message = "Incorrect customer id. Id should be a number higher or equal 0")
    private Long customerId;

    public Project(Long id, String name, String description,  Long customerId) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Project(){}

}
