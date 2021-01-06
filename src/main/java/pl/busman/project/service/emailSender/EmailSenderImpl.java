package pl.busman.project.service.emailSender;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;


@Service
public class EmailSenderImpl implements EmailSender {

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private Configuration config;

    public void sendEmail() {
        Map<String, Object> model = new HashMap<>();
        model.put("user","karolek");
        MimeMessage message = sender.createMimeMessage();
        try {
            // set mediaType
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            // add attachment
           // helper.addAttachment("logo.png", new ClassPathResource("logo.png"));

            Template t = config.getTemplate("/dto/email-template.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

            helper.setTo("rogu3451@interia.eu");
            helper.setSubject("Busman");
            helper.setText(html, true);

            sender.send(message);

        } catch (MessagingException | IOException | TemplateException e) {
            System.out.println(e);
        }

    }
}
