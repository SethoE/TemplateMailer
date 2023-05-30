package de.schwarz.templateMailer.exceptions;

public class TemplateNotFoundException extends Exception {

    private static final String DEFAULT_EXCEPTION_MESSAGE = "Template not found";
    private static final String FORMATTED_EXCEPTION_MESSAGE = "Template with id %s not found";

        public TemplateNotFoundException(String templateId) {
            super(String.format(FORMATTED_EXCEPTION_MESSAGE, templateId));
        }

        public TemplateNotFoundException() {
            super(DEFAULT_EXCEPTION_MESSAGE);
        }
}
