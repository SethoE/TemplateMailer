package de.schwarz.templateMailer.templateEngine;

import de.schwarz.templateMailer.exceptions.SMTPFileNotFoundException;
import de.schwarz.templateMailer.exceptions.TemplateNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Component
public class TemplateManager {


    //@Value("${template.config.folder}")
    private String templateConfigFolder = "C:/Users/Setho Ehrmann/IdeaProjects/TemplateMailer/src/main/resources/templates";

    private static final String TEMPLATE_FILE_NAME = "template.vm";
    private static final String SMTP_FILE_NAME = "smtp.properties";


    private final Map<String, TemplateInfo> templateMap;

    public TemplateManager() {
        templateMap = new HashMap<>();
        loadTemplates();
    }

    private void loadTemplates() {
        System.out.println("Loading templates from " + templateConfigFolder);
        File templateFolder = new File(templateConfigFolder);
        System.out.println("templateFolder = " + templateFolder.getAbsolutePath());
        if (templateFolder.isDirectory()) {
            File[] templateFolders = templateFolder.listFiles();
            if (templateFolders != null) {
                for (File folder : templateFolders) {
                    if (folder.isDirectory()) {
                        String templateId = folder.getName();
                        String templatePath = folder.getAbsolutePath() + "\\" + TEMPLATE_FILE_NAME;
                        String smtpPath = folder.getAbsolutePath() + "\\" + SMTP_FILE_NAME;
                        TemplateInfo templateInfo = new TemplateInfo(templatePath, smtpPath);

                        templateMap.put(templateId, templateInfo);
                    }
                }

            }
        }
    }

    public String getTemplateById(String templateId) throws TemplateNotFoundException {
        if (!templateMap.containsKey(templateId)) {
            throw new TemplateNotFoundException("Template with id " + templateId + " not found");
        }
        return templateMap.get(templateId).templatePath();
    }

    public String getSmtpById(String templateId) throws SMTPFileNotFoundException {
        if(!templateMap.containsKey(templateId)){
            throw new SMTPFileNotFoundException("Template with id " + templateId + " not found");
        }
        return templateMap.get(templateId).smtpPath();
    }


}
