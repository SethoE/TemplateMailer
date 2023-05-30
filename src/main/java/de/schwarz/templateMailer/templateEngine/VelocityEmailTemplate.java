package de.schwarz.templateMailer.templateEngine;

import de.schwarz.templateMailer.exceptions.TemplateNotFoundException;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.StringWriter;
import java.nio.file.Path;

public class VelocityEmailTemplate implements EmailTemplate {


    private final VelocityContext velocityContext = new VelocityContext();
    private final VelocityEngine velocityEngine;

    public VelocityEmailTemplate(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }

    private final TemplateManager templateManager = new TemplateManager();


    @Override
    public void setContextValue(String key, Object value) {
        velocityContext.put(key, value);
    }

    @Override
    public String generate(String templateId) throws TemplateNotFoundException {

        String templatePath = templateManager.getTemplateById(templateId);

        // TODO: This is a workaround, because the absolute path is not working
        String templatesFolderPath = "C:\\Users\\Setho Ehrmann\\IdeaProjects\\TemplateMailer\\src\\main\\resources";

        Path relativePath = Path.of(templatesFolderPath).relativize(Path.of(templatePath));

        final Template template = velocityEngine.getTemplate(relativePath.toString());
        final StringWriter stringWriter = new StringWriter();
        template.merge(this.velocityContext, stringWriter);

        return stringWriter.toString();
    }


}
