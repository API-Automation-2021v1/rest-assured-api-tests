package com.jalasoft.api;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Defines Pivotal Tracker POST projects tests.
 */
public class PivotalPostProjectTests {

    private String projectId;

    /**
     * Test to create a project specifying name.
     */
    @Test
    public void createProjectTest() {
        String endpoint = "https://www.pivotaltracker.com/services/v5/projects";
        String body = "{\"name\": \"API Automation\"}";
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-TrackerToken", "");

        Response response = RestAssured.given()
                .headers(headers)
                .body(body)
                .when()
                .post(endpoint);

        final int expectedStatusCode = 200;
        int actualStatusCode = response.statusCode();
        Assert.assertEquals(actualStatusCode, expectedStatusCode, "The expected status code does not match.");

        projectId = response.jsonPath().getString("id");

        File schema = new File("src/test/resources/schemas/postProjectSchema.json");
        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(schema));

        String actualProjectName = response.jsonPath().getString("name");
        String expectedProjectName = "API Automation";
        Assert.assertEquals(actualProjectName, expectedProjectName, "The project name does not match.");
    }

    /**
     * Tests post conditions.
     */
    @AfterMethod
    public void deleteProject() {
        if (projectId != null && !projectId.isEmpty()) {
            String endpoint = "https://www.pivotaltracker.com/services/v5/projects/" + projectId;
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json");
            headers.put("X-TrackerToken", "");

            Response response = RestAssured.given()
                    .headers(headers)
                    .when()
                    .delete(endpoint);

            final int expectedStatusCode = 204;
            Assert.assertEquals(response.statusCode(), expectedStatusCode, "The project was not deleted.");
        }
    }
}
