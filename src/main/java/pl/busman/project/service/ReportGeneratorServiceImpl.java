package pl.busman.project.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.validation.BindingResult;
import pl.busman.project.model.Role;
import pl.busman.project.model.dto.DataReportEmployeeForm;
import pl.busman.project.model.dto.DataToCreateReportFromForm;
import pl.busman.project.service.emailSender.EmailSender;
import pl.busman.project.service.validation.ReportValidation;

import java.io.IOException;
import java.util.*;

@Service
public class ReportGeneratorServiceImpl implements ReportGeneratorService {

    @Autowired
    ReportValidation reportValidation;

    @Autowired
    EmailSender emailSender;

    @Autowired
    private Configuration config;

    public void sendReport(DataReportEmployeeForm data, BindingResult bindingResult, Model model) {
        if (reportValidation.validateEmployeeFormData(data, bindingResult, model)) {
            try{
                emailSender.sendEmail(data.getEmail(),getEmployeeHTMLTemplate(employeeDataReportBuilder()));
            }catch (Exception e){
                System.out.println("Error email sending "+e);
            }

            DataReportEmployeeForm reportGeneratorData = new DataReportEmployeeForm();
            model.addAttribute("data", reportGeneratorData);
        }else{
            model.addAttribute("errorMessage","There were errors.");
        }
    }

    public Map<String, Object> employeeDataReportBuilder(){

        List<String> messages = Arrays.asList("1", "2", "3", "4", "5");
        List<Role> roles = new ArrayList<>();
        Role role1 = new Role("ADMIN1","ADMIN1");
        Role role2 = new Role("ADMIN1","ADMIN1");
        Role role3 = new Role("ADMIN1","ADMIN1");
        Role role4 = new Role("ADMIN1","ADMIN1");
        Role role5 = new Role("ADMIN1","ADMIN1");

        roles.add(role1);
        roles.add(role2);
        roles.add(role3);
        roles.add(role4);
        roles.add(role5);
        Map<String, Object> model1 = new HashMap<>();
        model1.put("user","karolek");
        model1.put("messages",messages);
        model1.put("roles",roles);

        return model1;
    }

    public String getEmployeeHTMLTemplate(Map<String, Object> model) throws IOException, TemplateException {
        Template templatePath = config.getTemplate("/dto/employeeReport.ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(templatePath, model);
        return html;
    }

}
