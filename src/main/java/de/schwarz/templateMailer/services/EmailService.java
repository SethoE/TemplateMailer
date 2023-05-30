package de.schwarz.templateMailer.services;

import com.github.cliftonlabs.json_simple.JsonObject;
import de.schwarz.templateMailer.builder.EMailGenerator;
import de.schwarz.templateMailer.config.EmailConfig;
import de.schwarz.templateMailer.config.VelocityEngineConfigurator;
import de.schwarz.templateMailer.dto.TemplateRequestDTO;
import de.schwarz.templateMailer.dto.TemplateRequestWithObject;
import de.schwarz.templateMailer.exceptions.SMTPFileNotFoundException;
import de.schwarz.templateMailer.exceptions.SubjectNotFoundException;
import de.schwarz.templateMailer.exceptions.TemplateNotFoundException;
import de.schwarz.templateMailer.model.EMail;
import de.schwarz.templateMailer.templateEngine.TemplateManager;
import de.schwarz.templateMailer.templateEngine.VelocityEmailTemplate;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


@Service
public class EmailService {

    private final JavaMailSender mailSender;

    private EmailConfig eMailConfig = new EmailConfig();

    private final TemplateManager templateManager;
    private final VelocityEngineConfigurator velocityEngineConfigurator = new VelocityEngineConfigurator();
    private final VelocityEmailTemplate velocityEmailTemplate = new VelocityEmailTemplate(velocityEngineConfigurator.getVelocityEngine());
    private final EMailGenerator emailGenerator = new EMailGenerator(velocityEmailTemplate);


    @Autowired
    public EmailService(JavaMailSender javaMailSender, TemplateManager templateManager) {
        this.mailSender = javaMailSender;
        this.templateManager = templateManager;
    }

    public void sendHTMLEmail(TemplateRequestDTO templateRequestDTO) throws MessagingException, SubjectNotFoundException, TemplateNotFoundException, SMTPFileNotFoundException {

        EMail createdEmail = createAndPopulateEmail(templateRequestDTO);

        MimeMessage emailMessage = mailSender.createMimeMessage();
        MimeMessageHelper mailBuilder = new MimeMessageHelper(emailMessage, true);
        mailBuilder.setTo(createdEmail.getMailTo());
        mailBuilder.setFrom(createdEmail.getMailFrom());
        mailBuilder.setText(createdEmail.getMailContent(), true);
        mailBuilder.setSubject(createdEmail.getMailSubject());
        mailSender.send(emailMessage);
    }

    public void sendHTMLEmail(TemplateRequestWithObject templateRequestWithObject) throws MessagingException, SubjectNotFoundException, TemplateNotFoundException, SMTPFileNotFoundException, IOException {

        EMail createdEmail = createAndPopulateEmail(templateRequestWithObject);

        MimeMessage emailMessage = mailSender.createMimeMessage();
        MimeMessageHelper mailBuilder = new MimeMessageHelper(emailMessage, true);
        mailBuilder.setTo(createdEmail.getMailTo());
        mailBuilder.setFrom(createdEmail.getMailFrom());
        mailBuilder.setText(createdEmail.getMailContent(), true);
        mailBuilder.setSubject(createdEmail.getMailSubject());
        mailSender.send(emailMessage);
    }

    private EMail createAndPopulateEmail(TemplateRequestDTO templateRequestDTO) throws SubjectNotFoundException, TemplateNotFoundException, SMTPFileNotFoundException {
        String templateId = templateRequestDTO.templateID();
        HashMap<String, String> templateData = templateRequestDTO.templateData();

        this.eMailConfig.setSmtpFilePath(templateManager.getSmtpById(templateId));

        for (String key : templateData.keySet()) {
            emailGenerator.addContext(key, templateData.get(key));
        }

        Properties smtpProperties = loadPropertiesFromFile(eMailConfig.getSmtpFilePath(), templateId);

        EMail email = new EMail();
        email.setMailTo(smtpProperties.getProperty("mail.smtp.mailTo"));
        email.setMailFrom(smtpProperties.getProperty("mail.smtp.username"));

        emailGenerator.template(templateId);
        emailGenerator.email(email);
        return emailGenerator.createMail();

    }

    private EMail createAndPopulateEmail(TemplateRequestWithObject templateRequestWithObject) throws SubjectNotFoundException, TemplateNotFoundException, SMTPFileNotFoundException {
        String templateId = templateRequestWithObject.templateID();
        HashMap<String, Object> templateData = templateRequestWithObject.templateData();

        this.eMailConfig.setSmtpFilePath(templateManager.getSmtpById(templateId));

        for (Map.Entry<String, Object> entry : templateData.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            emailGenerator.addContext(key, value);
        }

        Properties smtpProperties = loadPropertiesFromFile(eMailConfig.getSmtpFilePath(), templateId);

        EMail email = new EMail();
        email.setMailTo(smtpProperties.getProperty("mail.smtp.mailTo"));
        email.setMailFrom(smtpProperties.getProperty("mail.smtp.username"));

        emailGenerator.template(templateId);
        emailGenerator.email(email);
        return emailGenerator.createMail();
    }

    private Properties loadPropertiesFromFile(String filePath, String templateId) throws SMTPFileNotFoundException {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(filePath)) {
            properties.load(fis);
        } catch (IOException e) {
            throw new SMTPFileNotFoundException(templateId);
        }
        return properties;
    }


}
