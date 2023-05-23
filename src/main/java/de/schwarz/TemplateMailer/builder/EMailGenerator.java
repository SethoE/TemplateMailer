package de.schwarz.TemplateMailer.builder;
import java.io.StringWriter;
import java.util.Properties;


import de.schwarz.TemplateMailer.model.EMail;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;


public class EMailGenerator {

        private String subject;
        private String mailTo;
        private String mailFrom;
        private String template;
        private final VelocityContext velocityContext;
        private final VelocityEngine velocityEngine;

        public EMailGenerator() {
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

        public EMailGenerator Subject(String subject) {
            this.subject = subject;
            return this;
        }

        public EMailGenerator To(String to) {
            this.mailTo = to;
            return this;
        }

        public EMailGenerator From(String from) {
            this.mailFrom = from;
            return this;
        }

        public EMailGenerator Template(String template) {
            this.template = template;
            return this;
        }

        public EMailGenerator AddContext(String key, String value) {
            velocityContext.put(key, value);
            return this;
        }

        public EMailGenerator AddContext(String key, Object value) {
            velocityContext.put(key, value);
            return this;
        }

        public EMail createMail() throws IllegalArgumentException {
            final Template templateEngine = velocityEngine.getTemplate("templates/" + this.template);
            final StringWriter stringWriter = new StringWriter();
            templateEngine.merge(this.velocityContext, stringWriter);
            if(this.mailTo.isEmpty() || this.mailFrom.isEmpty()) {
                throw new IllegalArgumentException("Missing mail headers");
            }
            final EMail eMail = new EMail();
            eMail.setMailTo(this.mailTo);
            eMail.setMailFrom(this.mailFrom);
            eMail.setMailContent(stringWriter.toString());
            eMail.setMailSubject(this.subject);

            return eMail;
        }
}


