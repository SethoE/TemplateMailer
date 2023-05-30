package de.schwarz.templateMailer.templateEngine;

import de.schwarz.templateMailer.exceptions.TemplateNotFoundException;

public interface EmailTemplate {
    void setContextValue(String key, Object value);
    String generate(String templateId) throws TemplateNotFoundException;
}
