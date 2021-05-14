package com.jalasoft.api.config;

/**
 * Defines environment singleton class to read environment variables.
 */
public final class Environment {

    private static final String GRADLE_PROPERTIES_PATH = "gradle.properties";
    private static Environment instance;

    private final PropertiesReader properties;

    /**
     * Private constructor for singleton class.
     */
    private Environment() {
        properties = new PropertiesReader(GRADLE_PROPERTIES_PATH);
    }

    /**
     * Gets singleton instance of environment class.
     *
     * @return singleton instance.
     */
    public static Environment getInstance() {
        if (instance == null) {
            instance = new Environment();
        }
        return instance;
    }

    /**
     * Gets token.
     *
     * @return token value.
     */
    public String getToken() {
        return properties.getVariable("token");
    }

    /**
     * Gets service base URL.
     *
     * @return base URL.
     */
    public String getBaseURL() {
        return properties.getVariable("baseURL");
    }
}
