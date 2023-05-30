package de.schwarz.templateMailer.controllers;

import de.schwarz.templateMailer.dto.TemplateRequestDTO;
import de.schwarz.templateMailer.dto.TemplateRequestWithObject;
import de.schwarz.templateMailer.exceptions.SMTPFileNotFoundException;
import de.schwarz.templateMailer.exceptions.SubjectNotFoundException;
import de.schwarz.templateMailer.exceptions.TemplateNotFoundException;
import de.schwarz.templateMailer.logger.TemplateMailerLogger;
import de.schwarz.templateMailer.services.EmailService;
import jakarta.mail.MessagingException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/email")
public class EMailController {

    private final EmailService emailService;

    private static final String BAD_REQUEST_MESSAGE = "Email could not be sent. Please check your request.";
    private static final String INTERNAL_SERVER_ERROR_MESSAGE = "Email could not be sent. Please try again later.";
    private final Logger logger = LoggerFactory.getLogger(EMailController.class);


    public EMailController(EmailService emailService) {
        this.emailService = emailService;
    }


    @PostMapping("/simple/sendEmail")
    public @ResponseBody
    String sendMail(@RequestBody TemplateRequestDTO templateRequestDTO){

        try {
            emailService.sendHTMLEmail(templateRequestDTO);
            return "Request Successful \n" + templateRequestDTO.toString();
        }
        catch(MessagingException e){
            TemplateMailerLogger.logMessagingException(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, BAD_REQUEST_MESSAGE + "\n" + e.getMessage(), e); // 400
        } catch (SubjectNotFoundException e) {
            TemplateMailerLogger.logSubjectNotFoundException(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, BAD_REQUEST_MESSAGE + "\n" + e.getMessage(), e); // 400
        } catch (TemplateNotFoundException e) {
            TemplateMailerLogger.logTemplateNotFoundException(templateRequestDTO.templateID() + "\n" + e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, BAD_REQUEST_MESSAGE + "\n" + e.getMessage(), e); // 400
        } catch (SMTPFileNotFoundException e) {
            TemplateMailerLogger.logSMTPFileNotFoundException(templateRequestDTO.templateID() + "\n" + e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, BAD_REQUEST_MESSAGE + "\n" + e.getMessage(), e); // 400
        } catch (ResourceNotFoundException e) {
            TemplateMailerLogger.logResourceNotFoundException(templateRequestDTO.templateID() + "\n" + e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, BAD_REQUEST_MESSAGE, e); // 404
        }
        catch (Exception e) {
            TemplateMailerLogger.logIllegalArgumentException(templateRequestDTO.templateID() + "\n" + e.getMessage() , e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR_MESSAGE + "\n" + e.getMessage(), e); // 500
        }

    }
    @PostMapping("/sendEmail/")
    public @ResponseBody
    String sendMailWithObject(@RequestBody TemplateRequestWithObject templateRequestWithObject){

        try {
            emailService.sendHTMLEmail(templateRequestWithObject);
            return "Request Successful \n" + templateRequestWithObject.toString();
        }
        catch(MessagingException e){
            TemplateMailerLogger.logMessagingException(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, BAD_REQUEST_MESSAGE + "\n" + e.getMessage(), e); // 400
        } catch (SubjectNotFoundException e) {
            TemplateMailerLogger.logSubjectNotFoundException(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, BAD_REQUEST_MESSAGE + "\n" + e.getMessage(), e); // 400
        } catch (TemplateNotFoundException e) {
            TemplateMailerLogger.logTemplateNotFoundException(templateRequestWithObject.templateID() + "\n" + e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, BAD_REQUEST_MESSAGE + "\n" + e.getMessage(), e); // 400
        } catch (SMTPFileNotFoundException e) {
            TemplateMailerLogger.logSMTPFileNotFoundException(templateRequestWithObject.templateID() + "\n" + e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, BAD_REQUEST_MESSAGE + "\n" + e.getMessage(), e); // 400
        } catch (ResourceNotFoundException e) {
            TemplateMailerLogger.logResourceNotFoundException(templateRequestWithObject.templateID() + "\n" + e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, BAD_REQUEST_MESSAGE, e); // 404
        }
        catch (Exception e) {
            TemplateMailerLogger.logIllegalArgumentException(templateRequestWithObject.templateID() + "\n" + e.getMessage() , e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR_MESSAGE + "\n" + e.getMessage(), e); // 500
        }

    }


}
