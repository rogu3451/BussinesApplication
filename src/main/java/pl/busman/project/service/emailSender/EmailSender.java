package pl.busman.project.service.emailSender;


public interface EmailSender {

    void sendEmail(String email, String htmlTemplate);

}
