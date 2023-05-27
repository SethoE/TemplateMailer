package de.schwarz.TemplateMailer.manager;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Value;

import java.io.StringWriter;

public class VelocityEmailTemplate implements EmailTemplate {


    @Value("${template.config.folder}")
    private String templateConfigFolder;
    private VelocityContext velocityContext;
    private VelocityEngine velocityEngine;
    @Override
    public void setContextValue(String key, Object value) {

    }

    @Override
    public String generate() {
        final Template templateEngine = velocityEngine.getTemplate(templateConfigFolder);
        final StringWriter stringWriter = new StringWriter();
        templateEngine.merge(this.velocityContext, stringWriter);

        return stringWriter.toString();
    }
}
