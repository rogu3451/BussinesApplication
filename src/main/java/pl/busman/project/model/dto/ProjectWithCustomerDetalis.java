package pl.busman.project.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
public class ProjectWithCustomerDetalis {

    private Long id;
    private String name;
    private String description;
    private String firstName;
    private String lastName;

    public ProjectWithCustomerDetalis(Long id, String name, String description, String firstName, String lastName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
