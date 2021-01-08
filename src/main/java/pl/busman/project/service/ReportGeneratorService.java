package pl.busman.project.service;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import pl.busman.project.model.dto.DataReportEmployeeForm;
import pl.busman.project.model.dto.DataToCreateReportFromForm;

public interface ReportGeneratorService {

    void sendReport(DataReportEmployeeForm data, BindingResult bindingResult, Model model, String currentUserName);
}
