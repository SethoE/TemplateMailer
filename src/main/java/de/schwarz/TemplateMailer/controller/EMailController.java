package de.schwarz.TemplateMailer.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.schwarz.TemplateMailer.builder.EMailGenerator;
import de.schwarz.TemplateMailer.manager.EmailTemplate;
import de.schwarz.TemplateMailer.manager.TemplateManager;
import de.schwarz.TemplateMailer.model.EMail;
import de.schwarz.TemplateMailer.services.EMailService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class EMailController {
    private final EMailService emailService;
    private TemplateManager templateManager;

    public EMailController(EMailService emailService, TemplateManager templateManager) {
        this.emailService = emailService;
        this.templateManager = templateManager;
    }

    @PostMapping("/sendEmail")
    public ResponseEntity<String> sendEmail(@RequestBody String json) {
        try {

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(json);


            String templateId = jsonNode.get("templateId").asText();


            EmailTemplate emailTemplate = templateManager.resolveTemplate(templateId); // Throws template not found exception

            // @ControllerAdvice

            // template get

            JsonNode templateData = jsonNode.get("templateData");


            EMail mail = new EMailGenerator(emailTemplate)
                    .template(templateId)
                    .subject("Email Subject")
                    .addContext("templateData", templateData)
                    .createMail();

            emailService.sendHTMLEmail(mail);

            return ResponseEntity.ok("Email sent successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid JSON format!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email: " + e.getMessage());
        }
    }

    @RequestMapping(value ="/")
    public String homePage(HttpServletRequest request) {
        String responseMessage = request.getRequestURI();
        responseMessage = "Welcome to mailing service. \n" +
                "Please use /test to send sample report \n" + responseMessage;
        return responseMessage;
    }

}
