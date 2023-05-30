package de.schwarz.templateMailer.security;

public class TemplateAuthorization {
    public static boolean isUserAuthorized(String token, String templateId) {
        String userRole = JwtTokenUtil.getUserRole(token);
        return templateId.equals(userRole);
    }
}
