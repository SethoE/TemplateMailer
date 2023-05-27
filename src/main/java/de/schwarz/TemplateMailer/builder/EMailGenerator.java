package de.schwarz.TemplateMailer.builder;
import java.io.StringWriter;
import java.util.Properties;


import de.schwarz.TemplateMailer.manager.EmailTemplate;
import de.schwarz.TemplateMailer.manager.TemplateManager;
import de.schwarz.TemplateMailer.model.EMail;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Value;


public class EMailGenerator {

        @Value("${template.config.folder}")
        private String templateConfigFolder;
        private String subject;
        private String mailTo;
        private String mailFrom;
        private String template;
        private final VelocityContext velocityContext;
        private final VelocityEngine velocityEngine;

        private EmailTemplate emailTemplate;

        public EMailGenerator(EmailTemplate emailTemplate) {

            this.mailTo = "";
            this.mailFrom = "";
            this.subject = "";
            this.template = "";
            this.velocityContext = new VelocityContext();
            final Properties properties = new Properties();
            properties.setProperty("input.encoding", "UTF-8");
            properties.setProperty("output.encoding", "UTF-8");
            properties.setProperty("resource.loader", "file, class, jar");
            properties.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
            this.velocityEngine = new VelocityEngine(properties);
        }

        public EMailGenerator subject(String subject) {
            this.subject = subject;
            return this;
        }

        public EMailGenerator to(String to) {
            this.mailTo = to;
            return this;
        }

        public EMailGenerator from(String from) {
            this.mailFrom = from;
            return this;
        }

        public EMailGenerator template(String template) {
            this.template = template;
            return this;
        }


        public EMailGenerator addContext(String key, Object value) {
            velocityContext.put(key, value);
            return this;
        }

        public EMail createMail() throws IllegalArgumentException {
            if(this.mailTo.isEmpty() || this.mailFrom.isEmpty()) {
                throw new IllegalArgumentException("Missing mail headers");
            }

            template =  emailTemplate.generate(); // Parse string and get subject etc.


            final EMail email = new EMail();
            email.setMailTo(this.mailTo);
            email.setMailFrom(this.mailFrom);
            email.setMailContent(template);
            email.setMailSubject(this.subject);

            return email;
        }
}


