package de.schwarz.templateMailer.exceptions;

public class SubjectNotFoundException extends Exception {

    private static final String DEFAULT_EXCEPTION_MESSAGE = "Subject section not found in template";
    private static final String FORMATTED_EXCEPTION_MESSAGE = "Subject section with id %s not found";

    public SubjectNotFoundException(String templateId) {
            super(String.format(FORMATTED_EXCEPTION_MESSAGE, templateId));
        }



    public SubjectNotFoundException() {
            super(DEFAULT_EXCEPTION_MESSAGE);
        }
}
