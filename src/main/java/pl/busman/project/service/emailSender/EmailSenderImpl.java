package pl.busman.project.service.emailSender;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import pl.busman.project.model.Role;
import pl.busman.project.model.SystemUser;


@Service
public class EmailSenderImpl implements EmailSender {

    @Autowired
    private JavaMailSender sender;

    public void sendEmail(String email, String htmlTemplate) {

        try {
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            // add attachment
            // helper.addAttachment("logo.png", new ClassPathResource("logo.png"));

            helper.setTo(email);
            helper.setSubject("Busman Report");
            helper.setText(htmlTemplate, true);

            sender.send(message);

        } catch (Exception e) {
            System.out.println("Sending email error"+e);
        }

    }
}
