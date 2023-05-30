package de.schwarz.templateMailer.dto;

import java.util.HashMap;

public record TemplateRequestDTO(String templateID, HashMap<String, String> templateData) {

}
