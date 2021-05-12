package com.jalasoft.api;

/**
 * Defines environment singleton class to read environment variables.
 */
public final class Environment {

    private static Environment instance;

    /**
     * Private constructor for singleton class.
     */
    private Environment() {
        // Default constructor.
    }

    /**
     * Gets singleton instance of environment class.
     *
     * @return singleton instance.
     */
    public static Environment getInstance() {
        if (instance != null) {
            instance = new Environment();
        }
        return instance;
    }
}
