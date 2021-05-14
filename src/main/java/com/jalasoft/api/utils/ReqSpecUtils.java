package com.jalasoft.api.utils;

import com.jalasoft.api.config.Environment;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

/**
 * Defines methods to build request specifications.
 */
public final class ReqSpecUtils {

    /**
     * Private constructor for ReqSpecUtils utility class.
     */
    private ReqSpecUtils() {
        // Default constructor.
    }

    /**
     * Builds base request.
     *
     * @return request specification.
     */
    public static RequestSpecification buildBaseRequest() {
        return new RequestSpecBuilder()
                .setBaseUri(Environment.getInstance().getBaseURL())
                .addHeaders(getDefaultHeaders())
                .build();
    }

    /**
     * Builds base request with a custom request body.
     *
     * @param body request body.
     * @return request specification.
     */
    public static RequestSpecification buildBaseRequest(final String body) {
        return new RequestSpecBuilder()
                .setBaseUri(Environment.getInstance().getBaseURL())
                .addHeaders(getDefaultHeaders())
                .setBody(body)
                .build();
    }

    /**
     * Builds base request with custom request body and headers.
     *
     * @param body    request body.
     * @param headers request headers.
     * @return request specification.
     */
    public static RequestSpecification buildBaseRequest(final String body, final Map<String, String> headers) {
        return new RequestSpecBuilder()
                .setBaseUri(Environment.getInstance().getBaseURL())
                .addHeaders(headers)
                .setBody(body)
                .build();
    }

    /**
     * Builds base request with custom headers.
     *
     * @param headers request headers.
     * @return request specification.
     */
    public static RequestSpecification buildBaseRequest(final Map<String, String> headers) {
        return new RequestSpecBuilder()
                .setBaseUri(Environment.getInstance().getBaseURL())
                .addHeaders(headers)
                .build();
    }

    /**
     * Builds default headers.
     *
     * @return default headers map.
     */
    private static Map<String, String> getDefaultHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-TrackerToken", Environment.getInstance().getToken());
        return headers;
    }
}
