package com.jalasoft.api.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * Reads properties file.
 */
public class PropertiesReader {

    private static final Logger LOGGER = LogManager.getLogger(PropertiesReader.class);

    private Properties properties;

    /**
     * Initializes an instance of Properties reader class.
     *
     * @param path properties file path.
     */
    public PropertiesReader(final String path) {
        LOGGER.info(String.format("Reading '%s' properties file", path));
        try (InputStreamReader input = new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8)) {
            properties = new Properties();
            properties.load(input);
        } catch (IOException e) {
            LOGGER.error("Error when reading properties file.");
            LOGGER.error(e.getMessage());
        }
    }

    /**
     * Gets variable from properties object.
     *
     * @param variableName variable name.
     * @return variable content.
     */
    public String getVariable(final String variableName) {
        return properties.getProperty(variableName);
    }
}
