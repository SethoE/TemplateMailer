package de.schwarz.templateMailer.dto;

import java.util.HashMap;


public record TemplateRequestWithObject(String templateID, HashMap<String, Object> templateData) {
}
