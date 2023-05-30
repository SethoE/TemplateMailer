package de.schwarz.templateMailer.builder;


import de.schwarz.templateMailer.exceptions.SubjectNotFoundException;
import de.schwarz.templateMailer.exceptions.TemplateNotFoundException;
import de.schwarz.templateMailer.model.EMail;
import de.schwarz.templateMailer.templateEngine.EmailTemplate;



public class EMailGenerator {

    private final EmailTemplate emailTemplate;

    private String templateId;

    private EMail email;


    public EMailGenerator(EmailTemplate emailTemplate) {

        this.emailTemplate = emailTemplate;

    }

    public EMailGenerator(EmailTemplate emailTemplate, EMail email, String templateId) {
        this.emailTemplate = emailTemplate;
        this.email = email;
        this.templateId = templateId;
    }

    public EMailGenerator email(EMail email) {
        this.email = email;
        return this;
    }


    public EMailGenerator template(String templateId) {
        this.templateId = templateId;
        return this;
    }

    public EMailGenerator addContext(String key, Object value) {
        emailTemplate.setContextValue(key, value);
        return this;
    }

    public EMail createMail() throws IllegalArgumentException, SubjectNotFoundException, TemplateNotFoundException {
        if (this.email.getMailTo().isEmpty() || this.email.getMailFrom().isEmpty() || this.email == null) {
            throw new IllegalArgumentException("Missing mail headers");
        }

        String template = emailTemplate.generate(templateId);

        setSubjectAndContent(template, templateId);


        return email;
    }

    private void setSubjectAndContent(String template, String templateId) throws SubjectNotFoundException {
        String subjectVariableName = "Subject: ";

        // Extract the subject and trim the subject section from the template string
        StringBuilder templateContentBuilder = new StringBuilder(template);
        StringBuilder subjectBuilder = new StringBuilder();
        boolean subjectSectionFound = false;

        int subjectStartIndex = templateContentBuilder.indexOf(subjectVariableName);
        if (subjectStartIndex != -1) {
            int subjectEndIndex = templateContentBuilder.indexOf("\n", subjectStartIndex);
            if (subjectEndIndex != -1) {
                String subject = templateContentBuilder.substring(subjectStartIndex + subjectVariableName.length(), subjectEndIndex).trim();
                templateContentBuilder.delete(subjectStartIndex, subjectEndIndex);
                subjectBuilder.append(subject);
                subjectSectionFound = true;
            }
        }

        if (subjectSectionFound == false) {
            throw new SubjectNotFoundException(templateId);
        }

        String subject = subjectBuilder.toString().trim();
        String templateContent = templateContentBuilder.toString().trim();

        // Set the subject and template content to the email object

        this.email.setMailSubject(subject);
        this.email.setMailContent(templateContent);
    }
}
