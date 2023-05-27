package de.schwarz.TemplateMailer.manager;

public interface EmailTemplate {

    public void setContextValue(String key, Object value);

    public String generate();
}
