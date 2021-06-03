package com.jalasoft.api.tests;

import com.jalasoft.api.client.RequestManager;
import com.jalasoft.api.utils.AssertSchemaUtils;
import com.jalasoft.api.utils.JsonPathUtils;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

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
        String body = "{\"name\": \"API Automation\"}";
        Response response = RequestManager.sendPostRequest("projects", body);

        final int expectedStatusCode = 200;
        int actualStatusCode = response.statusCode();
        Assert.assertEquals(actualStatusCode, expectedStatusCode, "The expected status code does not match.");

        projectId = JsonPathUtils.getValue(response, "id");

        AssertSchemaUtils.validateSchema(response, "postProjectSchema.json");

        String actualProjectName = JsonPathUtils.getValue(response, "name");
        String expectedProjectName = "API Automation";
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
