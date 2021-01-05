package pl.busman.project.model;



import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    @Size(min=10, message = "Description of project should have minimum 10 characters")
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
