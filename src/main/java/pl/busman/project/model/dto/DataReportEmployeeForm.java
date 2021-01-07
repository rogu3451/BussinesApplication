package pl.busman.project.model.dto;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
public class DataReportEmployeeForm extends  DataToCreateReportFromForm{

    @Min(value=2020, message = "Year should by higher or equal 2020")
    private Long year;

    @Min(value=1, message = "Incorrect value of month")
    @Max(value=12, message = "Incorrect value of month")
    private Long month;

}
