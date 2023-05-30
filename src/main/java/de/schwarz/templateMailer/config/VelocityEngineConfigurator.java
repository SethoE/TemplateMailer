package de.schwarz.templateMailer.config;

import org.apache.velocity.app.VelocityEngine;

import java.util.Properties;

public class VelocityEngineConfigurator {

    final private VelocityEngine velocityEngine;



    public VelocityEngineConfigurator() {
        final Properties properties = new Properties();
        properties.setProperty("input.encoding", "UTF-8");
        properties.setProperty("output.encoding", "UTF-8");
        properties.setProperty("resource.loader", "file, class, jar");
        properties.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        this.velocityEngine = new VelocityEngine(properties);
    }

    public VelocityEngine getVelocityEngine() {
        return this.velocityEngine;
    }
}
