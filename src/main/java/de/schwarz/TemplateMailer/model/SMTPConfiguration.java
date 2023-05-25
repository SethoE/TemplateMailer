package de.schwarz.TemplateMailer.model;

public class SMTPConfiguration {
        private String host;
        private int port;
        private String username;
        private String password;
        private boolean auth;
        private boolean starttls;
        private boolean debug;

        // Getters and setters

        @Override
        public String toString() {
            return "SmtpConfiguration{" +
                    "host='" + host + '\'' +
                    ", port=" + port +
                    ", username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    ", auth=" + auth +
                    ", starttls=" + starttls +
                    ", debug=" + debug +
                    '}';
        }
}
