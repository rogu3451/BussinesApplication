package pl.busman.project.service;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import pl.busman.project.model.dto.DataReportCustomerForm;
import pl.busman.project.model.dto.DataReportEmployeeForm;

public interface ReportGeneratorService {

    void sendReport(DataReportEmployeeForm data, BindingResult bindingResult, Model model, String employeeUsername);

    void sendReport(DataReportCustomerForm data, BindingResult bindingResult, Model model, String customerUsername);
}
