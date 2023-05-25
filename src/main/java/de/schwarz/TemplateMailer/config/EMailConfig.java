package de.schwarz.TemplateMailer.config;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Configuration
public class EMailConfig {


    private static final String SMTP_PROPERTIES_FILE_NAME = "/smtp.properties";

    @Value("${template.config.folder}")
    private String templateConfigFolder;

    @Bean
    public JavaMailSender javaMailSender() throws IOException {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        Properties smtpProperties = new Properties();
        String templateFolder = templateConfigFolder + "/" + getTemplateId();
        smtpProperties.load(new FileInputStream(templateFolder + SMTP_PROPERTIES_FILE_NAME));


        mailSender.setHost(smtpProperties.getProperty("mail.smtp.host"));
        mailSender.setPort(Integer.parseInt(smtpProperties.getProperty("mail.smtp.port")));
        mailSender.setUsername(smtpProperties.getProperty("mail.smtp.username"));
        mailSender.setPassword(smtpProperties.getProperty("mail.smtp.password"));

        Properties additionalProperties = new Properties();
        additionalProperties.put("mail.transport.protocol", "smtp");
        additionalProperties.put("mail.smtp.auth", smtpProperties.getProperty("mail.smtp.auth"));
        additionalProperties.put("mail.smtp.starttls.enable", smtpProperties.getProperty("mail.smtp.starttls.enable"));
        additionalProperties.put("mail.debug", smtpProperties.getProperty("mail.debug"));

        mailSender.setJavaMailProperties(additionalProperties);

        return mailSender;
    }

    public String setTemplateId(String templateID) {
        return templateID;
    }
}