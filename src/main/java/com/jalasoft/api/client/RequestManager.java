package com.jalasoft.api.client;

import com.jalasoft.api.utils.ReqSpecUtils;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

/**
 * Defines methods to send requests.
 */
public final class RequestManager {

    private static final Logger LOGGER = LogManager.getLogger(RequestManager.class);

    /**
     * Private constructor for RequestManager utility class.
     */
    private RequestManager() {
        // Default constructor for utility class.
    }

    /**
     * Sends a GET request.
     *
     * @param endpoint service endpoint.
     * @return response object.
     */
    public static Response sendGetRequest(final String endpoint) {
        LOGGER.info("Sending GET request");
        LOGGER.info("Endpoint: " + endpoint);
        return RestAssured.given(ReqSpecUtils.buildBaseRequest()).when().get(endpoint);
    }

    /**
     * Sends a GET request with custom headers.
     *
     * @param endpoint service endpoint.
     * @param headers  custom headers.
     * @return response object.
     */
    public static Response sendGetRequest(final String endpoint, final Map<String, String> headers) {
        LOGGER.info("Sending GET request");
        LOGGER.info("Endpoint: " + endpoint);
        LOGGER.info("Headers: " + headers.toString());
        return RestAssured.given(ReqSpecUtils.buildBaseRequest(headers)).when().get(endpoint);
    }

    /**
     * Sends POST request.
     *
     * @param endpoint service endpoint.
     * @param body     request body.
     * @return response object.
     */
    public static Response sendPostRequest(final String endpoint, final String body) {
        LOGGER.info("Sending POST request");
        LOGGER.info("Endpoint: " + endpoint);
        LOGGER.info("Body: " + body);
        return RestAssured.given(ReqSpecUtils.buildBaseRequest(body)).when().post(endpoint);
    }

    /**
     * Sends POST request with custom headers.
     *
     * @param endpoint service endpoint.
     * @param body     request body.
     * @param headers  custom headers.
     * @return response object.
     */
    public static Response sendPostRequest(final String endpoint, final String body,
                                           final Map<String, String> headers) {
        LOGGER.info("Sending POST request");
        LOGGER.info("Endpoint: " + endpoint);
        LOGGER.info("Body: " + body);
        LOGGER.info("Headers: " + headers.toString());
        return RestAssured.given(ReqSpecUtils.buildBaseRequest(body, headers)).when().post(endpoint);
    }

    /**
     * Sends PUT request.
     *
     * @param endpoint service endpoint.
     * @param body     request body.
     * @return response object.
     */
    public static Response sendPutRequest(final String endpoint, final String body) {
        LOGGER.info("Sending PUT request");
        LOGGER.info("Endpoint: " + endpoint);
        LOGGER.info("Body: " + body);
        return RestAssured.given(ReqSpecUtils.buildBaseRequest(body)).when().put(endpoint);
    }

    /**
     * Sends PUT request with custom headers.
     *
     * @param endpoint service endpoint.
     * @param body     request body.
     * @param headers  custom headers.
     * @return response object.
     */
    public static Response sendPutRequest(final String endpoint, final String body, final Map<String, String> headers) {
        LOGGER.info("Sending PUT request");
        LOGGER.info("Endpoint: " + endpoint);
        LOGGER.info("Body: " + body);
        LOGGER.info("Headers: " + headers.toString());
        return RestAssured.given(ReqSpecUtils.buildBaseRequest(body, headers)).when().put(endpoint);
    }

    /**
     * Sends DELETE request.
     *
     * @param endpoint service endpoint.
     * @return response object.
     */
    public static Response sendDeleteRequest(final String endpoint) {
        LOGGER.info("Sending DELETE request");
        LOGGER.info("Endpoint: " + endpoint);
        return RestAssured.given(ReqSpecUtils.buildBaseRequest()).when().delete(endpoint);
    }
}
