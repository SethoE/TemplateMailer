package de.schwarz.TemplateMailer.services;

import de.schwarz.TemplateMailer.model.EMail;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
public class EMailService {

        private final JavaMailSender mailSender;

        @Autowired
        public EMailService(JavaMailSender javamailSender) {
            this.mailSender = javamailSender;
        }

        public void sendHTMLEmail(EMail message) throws MessagingException {
            MimeMessage emailMessage = mailSender.createMimeMessage();
            MimeMessageHelper mailBuilder = new MimeMessageHelper(emailMessage, true);
            mailBuilder.setTo(message.getMailTo());
            mailBuilder.setFrom(message.getMailFrom());
            mailBuilder.setText(message.getMailContent(), true);
            mailBuilder.setSubject(message.getMailSubject());
            mailSender.send(emailMessage);
        }
}
