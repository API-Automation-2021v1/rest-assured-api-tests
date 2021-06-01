package com.jalasoft.api.tests.weimar;

import com.jalasoft.api.client.RequestManager;
import com.jalasoft.api.utils.AssertSchemaUtils;
import com.jalasoft.api.utils.JsonPathUtils;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Defines Pivotal Tracker POST stories tests.
 */
public class PivotalPostStoryTests {

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
     * Test to create a project specifying name.
     */
    @Test
    public void createStoryTest() {
        String body = "{\"name\": \"API Automation Story\"}";
        String endpoint = "/projects/"+ projectId +"/stories";
        Response response = RequestManager.sendPostRequest(endpoint, body);
        final int expectedStatusCode = 200;
        int actualStatusCode = response.statusCode();
        Assert.assertEquals(actualStatusCode, expectedStatusCode, "The expected status code does not match.");
        AssertSchemaUtils.validateSchema(response, "weimar/postStoriesSchema.json");
        String actualStoryName = JsonPathUtils.getValue(response, "name");
        String expectedProjectName = "API Automation Story";
        Assert.assertEquals(actualStoryName, expectedProjectName, "The project name does not match.");
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
