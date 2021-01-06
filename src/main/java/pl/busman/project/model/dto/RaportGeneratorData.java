package pl.busman.project.model.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.Email;

@Getter
@Setter
@Data
public class RaportGeneratorData {

    @Email()
    private String email;

    private String date;
}
