package de.schwarz.TemplateMailer.manager;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Value;



public class TemplateManager {

    @Value("${template.config.folder}")
    private String templateConfigFolder;




    public TemplateManager() {

    }


}
