package com.jalasoft.api.tests;

import com.jalasoft.api.client.RequestManager;
import com.jalasoft.api.utils.AssertSchemaUtils;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Defines Pivotal Tracker PUT project tests.
 */
public class PivotalPutProjectTests {

    private String projectId;

    /**
     * Tests pre conditions.
     */
    @BeforeMethod
    public void createProject() {
        String body = "{\"name\": \"API Automation\"}";
        Response response = RequestManager.sendPostRequest("projects", body);

        final int expectedStatusCode = 200;
        Assert.assertEquals(response.statusCode(), expectedStatusCode, "The project was not created.");

        projectId = response.jsonPath().getString("id");
    }

    /**
     * Test to modify a project.
     */
    @Test
    public void createStoryTest() {
        String body = "{\"name\": \"API Automation - Modified Name\"}";
        Response response = RequestManager.sendPutRequest("projects/".concat(projectId), body);

        final int expectedStatusCode = 200;
        int actualStatusCode = response.statusCode();
        Assert.assertEquals(actualStatusCode, expectedStatusCode, "The expected status code does not match.");

        AssertSchemaUtils.validateSchema(response, "putProjectSchema.json");

        String actualProjectName = response.jsonPath().getString("name");
        String expectedProjectName = "API Automation - Modified Name";
        Assert.assertEquals(actualProjectName, expectedProjectName, "The project name does not match.");
    }

    /**
     * @return the projectID for use in another classes.
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * Tests post conditions.
     */
    @AfterMethod
    public void deleteProject() {
        if (projectId != null && !projectId.isEmpty()) {
            Response response = RequestManager.sendDeleteRequest("projects/".concat(projectId));

            final int expectedStatusCode = 204;
            Assert.assertEquals(response.statusCode(), expectedStatusCode, "The project was not deleted.");
        }
    }
}
