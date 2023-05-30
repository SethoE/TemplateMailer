package de.schwarz.templateMailer.controllers;


import de.schwarz.templateMailer.dto.TemplateRequestDTO;
import de.schwarz.templateMailer.exceptions.SMTPFileNotFoundException;
import de.schwarz.templateMailer.exceptions.SubjectNotFoundException;
import de.schwarz.templateMailer.exceptions.TemplateNotFoundException;
import de.schwarz.templateMailer.services.EmailService;
import jakarta.mail.MessagingException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.mockito.Mockito.*;

class EMailControllerTest {

    @Test
    void sendMail() {
    }

    @Test
    void sendMailWithObject() {
    }

    @Mock
    private EmailService emailService;

    @InjectMocks
    private EMailController emailController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendMail_ValidRequest_Successful() throws MessagingException, SubjectNotFoundException,
            TemplateNotFoundException, SMTPFileNotFoundException, ResourceNotFoundException {
        TemplateRequestDTO templateRequestDTO = new TemplateRequestDTO("templateID", null);

        doNothing().when(emailService).sendHTMLEmail(templateRequestDTO);

        emailController.sendMail(templateRequestDTO);

        verify(emailService, times(1)).sendHTMLEmail(templateRequestDTO);
    }

    @Test
    void sendMail_ExceptionThrown_InternalServerError() throws MessagingException, SubjectNotFoundException,
            TemplateNotFoundException, SMTPFileNotFoundException, ResourceNotFoundException {
        TemplateRequestDTO templateRequestDTO = new TemplateRequestDTO("templateID", null);

        doThrow(new MessagingException("Email sending failed")).when(emailService).sendHTMLEmail(templateRequestDTO);

        try {
            emailController.sendMail(templateRequestDTO);
        } catch (ResponseStatusException e) {
            assert e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR;
            assert e.getReason().contains("Email could not be sent");
            assert e.getReason().contains("Email sending failed");
        }

        verify(emailService, times(1)).sendHTMLEmail(templateRequestDTO);
    }
}