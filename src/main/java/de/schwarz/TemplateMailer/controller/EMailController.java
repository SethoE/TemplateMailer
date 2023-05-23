package de.schwarz.TemplateMailer.controller;

import de.schwarz.TemplateMailer.builder.EMailGenerator;
import de.schwarz.TemplateMailer.model.EMail;
import de.schwarz.TemplateMailer.services.EMailService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class EMailController {

    private final EMailService emailService;
    private final String mailTo;

    public EMailController(EMailService emailService, @Value("${mail.to}") String mailTo) {
        this.emailService = emailService;
        this.mailTo = mailTo;
    }

    // Send a simple HTML email to the mail.to
    @RequestMapping(value ="/test")
    public String sendTestReport(HttpServletRequest request){
        final EMail mail = new EMailGenerator()
                .From("ehrmannsetho@gmail.com") // For gmail, this field is ignored.
                .To(this.mailTo)
                .Template("mail-template.html")
                .AddContext("subject", "Test Email")
                .AddContext("content", "Hello World!")
                .Subject("Hello")
                .createMail();
        String responseMessage = request.getRequestURI();
        try {
            this.emailService.sendHTMLEmail(mail);
        }
        catch (Exception e) {
            responseMessage = "Request Unsuccessful \n" + e.getMessage() + "\n" + responseMessage;
            return responseMessage;
        }
        responseMessage = "Request Successful \n" + responseMessage;
        return responseMessage;
    }

    @RequestMapping(value ="/")
    public String homePage(HttpServletRequest request) {
        String responseMessage = request.getRequestURI();
        responseMessage = "Welcome to mailing service. \n" +
                "Please use /test to send sample report \n" + responseMessage;
        return responseMessage;
    }

}
