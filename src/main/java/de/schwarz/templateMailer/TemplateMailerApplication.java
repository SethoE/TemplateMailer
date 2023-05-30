package de.schwarz.templateMailer;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @io.swagger.v3.oas.annotations.info.Info(
				title = "Template Mailer",
				version = "1.0",
				description = "A simple REST-API for sending emails based on predefined templates email templates.",
				contact = @Contact(
						name = "Setho Ehrmann",
						email = "setho.ehrmann@mail.schwarz"
		),
		license = @License(
				name = "MIT License",
				url = "https://opensource.org/licenses/MIT"
		)
))
public class TemplateMailerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TemplateMailerApplication.class, args);
	}

}
