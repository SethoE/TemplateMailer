package de.schwarz.templateMailer.services;

import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;

import de.schwarz.templateMailer.dto.TemplateRequestDTO;
import de.schwarz.templateMailer.dto.TemplateRequestWithObject;
import de.schwarz.templateMailer.exceptions.SMTPFileNotFoundException;
import de.schwarz.templateMailer.exceptions.SubjectNotFoundException;
import de.schwarz.templateMailer.exceptions.TemplateNotFoundException;
import de.schwarz.templateMailer.templateEngine.TemplateManager;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;

import java.io.IOException;
import java.util.HashMap;

import static org.mockito.Mockito.when;

import static org.mockito.Mockito.*;

class EmailServiceTest {

    @Test
    void sendHTMLEmail() {
    }

    @Test
    void testSendHTMLEmail() {
    }

    @Mock
    private JavaMailSender mailSender;
    @Mock
    private TemplateManager templateManager;

    private EmailService emailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        emailService = new EmailService(mailSender, templateManager);
    }

    @Test
    void sendHTMLEmail_TemplateRequestDTO_Successful() throws MessagingException, SubjectNotFoundException, TemplateNotFoundException, SMTPFileNotFoundException {
        TemplateRequestDTO templateRequestDTO = new TemplateRequestDTO("templateId", new HashMap<>());

        when(templateManager.getSmtpById("TID1684996727_testing-account-status-update_2023-05-25")).thenReturn("C:\\Users\\Setho Ehrmann\\IdeaProjects\\TemplateMailer\\src\\main\\resources\\templates\\TID1684996727_testing-account-status-update_2023-05-25\\smtp.properties");

        emailService.sendHTMLEmail(templateRequestDTO);

        verify(mailSender, times(1)).createMimeMessage();
        // Add more verification as needed
    }

    @Test
    void sendHTMLEmail_TemplateRequestWithObject_Successful() throws MessagingException, SubjectNotFoundException, TemplateNotFoundException, SMTPFileNotFoundException, IOException {
        TemplateRequestWithObject templateRequestWithObject = new TemplateRequestWithObject("templateId", new HashMap<>());

        when(templateManager.getSmtpById("TID1684996727_testing-account-status-update_2023-05-25")).thenReturn("C:\\Users\\Setho Ehrmann\\IdeaProjects\\TemplateMailer\\src\\main\\resources\\templates\\TID1684996727_testing-account-status-update_2023-05-25\\smtp.properties");
        ;
        emailService.sendHTMLEmail(templateRequestWithObject);

        verify(mailSender, times(1)).createMimeMessage();
    }
}