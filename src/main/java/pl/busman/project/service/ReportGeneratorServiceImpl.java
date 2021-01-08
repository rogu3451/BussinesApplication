package pl.busman.project.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.validation.BindingResult;
import pl.busman.project.model.Project;
import pl.busman.project.model.dto.DataReportEmployeeForm;
import pl.busman.project.model.dto.EmployeeReportBuilder;
import pl.busman.project.repository.ReportRepository;
import pl.busman.project.repository.SystemUserRepository;
import pl.busman.project.service.emailSender.EmailSender;
import pl.busman.project.service.validation.ReportValidation;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ReportGeneratorServiceImpl implements ReportGeneratorService {

    @Autowired
    ReportValidation reportValidation;

    @Autowired
    EmailSender emailSender;

    @Autowired
    Configuration config;

    @Autowired
    ReportRepository reportRepository;

    @Autowired
    SystemUserRepository systemUserRepository;

    @Autowired
    SystemUserService systemUserService;

    public void sendReport(DataReportEmployeeForm data, BindingResult bindingResult, Model model, String employeeUsername) {
        if (reportValidation.validateEmployeeFormData(data, bindingResult, model, employeeUsername, getDateString(data.getYear(),data.getMonth()))) {
            try{
                Map<String, Object> reportData = employeeDataReportBuilder(employeeUsername, data);
                emailSender.sendEmail(data.getEmail(),getEmployeeHTMLTemplate(reportData));
            }catch (Exception e){
                System.out.println("Error email sending "+e);
            }

            DataReportEmployeeForm reportGeneratorData = new DataReportEmployeeForm();
            model.addAttribute("data", reportGeneratorData);
        }else{
            model.addAttribute("errorMessage","There were errors.");
        }
    }

    public Map<String, Object> employeeDataReportBuilder(String employeeUsername, DataReportEmployeeForm data){

        Long employeeId = systemUserRepository.getIdByUsername(employeeUsername);
        String fullName = systemUserService.getFullNameOfUserById(employeeId);
        String convertedDate = getDateString(data.getYear(),data.getMonth());
        List<Project> projects= reportRepository.getProjectsByEmployeeAndDateIdWhereExistsDoneTasks(employeeId,convertedDate);
        Double totalHours = reportRepository.getTotalWorkHoursPerMonthAndYear(employeeId,convertedDate);
        EmployeeReportBuilder projectsWithTasks[] = new EmployeeReportBuilder[projects.size()];


        for(int i=0; i<projects.size(); i++) {
            projectsWithTasks[i] = new EmployeeReportBuilder(projects.get(i).getName());
            projectsWithTasks[i].setProjectTasks(reportRepository.getDoneProjectTasksByProjectIdAndDate(projects.get(i).getId(), convertedDate));
         }

        Map<String, Object> model = new HashMap<>();
        model.put("projects",projectsWithTasks);
        model.put("date",getCurrentDate());
        model.put("time",getCurrentTime());
        model.put("month",getMonthAsString(data.getMonth()));
        model.put("year",data.getYear().toString());
        model.put("fullName",fullName);
        model.put("totalHours",totalHours);

        return model;
    }

    private String getMonthAsString(Long month) {
        if(month == 1){
            return "January";
        }
        if(month == 2){
            return "February";
        }
        if(month == 3){
            return "March";
        }
        if(month == 4){
            return "April";
        }
        if(month == 5){
            return "May";
        }
        if(month == 6){
            return "June";
        }
        if(month == 7){
            return "July";
        }
        if(month == 8){
            return "August";
        }
        if(month == 9){
            return "September";
        }
        if(month == 10){
            return "October";
        }
        if(month == 11){
            return "November";
        }
        if(month == 12){
            return "December";
        }
        else{
            return "Incorrect month";
        }
    }

    private String getCurrentDate() {
        DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDateTime.now().format(date);
    }

    private String getCurrentTime() {
        DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm:ss");
        return LocalDateTime.now().format(time);
    }

    public String getEmployeeHTMLTemplate(Map<String, Object> model) throws IOException, TemplateException {
        Template templatePath = config.getTemplate("/dto/employeeReport.ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(templatePath, model);
        return html;
    }

    private String getDateString(Long year, Long month){
        if(month<10){
            return year+"-0"+month;
        }else{
            return year+"-"+month;
        }
    }

}
