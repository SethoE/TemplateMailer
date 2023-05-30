package de.schwarz.templateMailer.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TemplateMailerLogger {
    private static final Logger logger = LoggerFactory.getLogger(TemplateMailerLogger.class);

    public static void logInfo(String message) {
        logger.info(message);
    }

    public static void logError(String message, Throwable throwable) {
        logger.error(message, throwable);
    }


    public static void logResourceNotFoundException(String message, Throwable throwable) {
        logger.error("Resource not found: " + message, throwable);
    }

    public static void logMessagingException(String message, Throwable throwable) {
        logger.error("Messaging Exception: " + message, throwable);
    }

    public static void logSMTPFileNotFoundException(String message, Throwable throwable) {
        logger.error("SMTP File not found: " + message, throwable);
    }

    public static void logTemplateNotFoundException(String message, Throwable throwable) {
        logger.error("Template not found: " + message, throwable);
    }

    public static void logSubjectNotFoundException(String message, Throwable throwable) {
        logger.error("Subject not found: " + message, throwable);
    }

    public static void logIllegalArgumentException(String message, Throwable throwable) {
        logger.error("Illegal Argument: " + message, throwable);
    }


    public static void logBadRequestException(String message, Throwable throwable) {
        logger.error("Bad Request: " + message, throwable);
    }
}