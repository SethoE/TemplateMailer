package de.schwarz.templateMailer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.test.context.SpringBootTest;
import springfox.documentation.oas.annotations.EnableOpenApi;

@SpringBootTest
@EnableOpenApi
@ConfigurationPropertiesScan
class TemplateMailerApplicationTests {

	@Test
	void contextLoads() {
	}

}
