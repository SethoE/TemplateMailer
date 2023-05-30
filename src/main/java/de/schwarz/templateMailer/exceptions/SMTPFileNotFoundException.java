package de.schwarz.templateMailer.exceptions;

public class SMTPFileNotFoundException extends Exception {
    private static final String DEFAULT_EXCEPTION_MESSAGE = "SMTP file not found";
    private static final String FORMATTED_EXCEPTION_MESSAGE = "SMTP file with id %s not found";

    public SMTPFileNotFoundException(String templateId) {
        super(String.format(FORMATTED_EXCEPTION_MESSAGE, templateId));
    }

    public SMTPFileNotFoundException() {
        super(DEFAULT_EXCEPTION_MESSAGE);
    }
}
