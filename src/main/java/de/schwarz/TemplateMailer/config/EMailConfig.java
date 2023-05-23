package de.schwarz.TemplateMailer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EMailConfig {

    @Bean
    public JavaMailSender javaMailSender() {
        final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("localhost");
        mailSender.setPort(1025);
        mailSender.setUsername("ehrmannsetho@gmail.com");
        //Gmail now requires you to setup App password. See https://support.google.com/accounts/answer/185833
        mailSender.setPassword("blabla"); //  xqrblprpwspmofem


        final Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "false");
        props.put("mail.smtp.starttls.enable", "false");
        props.put("mail.debug", "true");
        return mailSender;
    }

}