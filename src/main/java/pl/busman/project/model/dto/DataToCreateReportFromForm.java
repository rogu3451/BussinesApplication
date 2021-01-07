package pl.busman.project.model.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class DataToCreateReportFromForm {

    @Email(message = "Invalid email address format")
    private String email;

}
