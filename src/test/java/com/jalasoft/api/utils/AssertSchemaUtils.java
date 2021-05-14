package com.jalasoft.api.utils;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

/**
 * Defines schema assertion utils.
 */
public final class AssertSchemaUtils {

    private static final Logger LOGGER = LogManager.getLogger(AssertSchemaUtils.class);
    private static final String SCHEMAS_PATH = "src/test/resources/schemas/";

    /**
     * Private constructor for AssertSchemaUtils utility class.
     */
    private AssertSchemaUtils() {
        // Default constructor for utility class.
    }

    /**
     * Validates schema according to parameters.
     *
     * @param response   response object.
     * @param schemaName schema name.
     */
    public static void validateSchema(final Response response, final String schemaName) {
        LOGGER.info("Validating schema from file: ".concat(schemaName));
        File schema = new File(SCHEMAS_PATH.concat(schemaName));
        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(schema));
    }
}
