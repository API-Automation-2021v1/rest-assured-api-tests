package com.jalasoft.api.tests.weimar;

import com.jalasoft.api.client.RequestManager;
import com.jalasoft.api.utils.AssertSchemaUtils;
import com.jalasoft.api.utils.JsonPathUtils;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class PivotalPutStoryTests {
    private String projectId;
    private String storiesId;

    /**
     * Tests pre conditions.
     */
    @BeforeMethod
    /**
     * Tests pre conditions, create a project.
     */
    public void createProject() {
        /**
         * Create a new Project
         */
        String body = "{\"name\": \"API Automation\"}";
        Response response = RequestManager.sendPostRequest("projects", body);
        final int expectedStatusCode = 200;
        Assert.assertEquals(response.statusCode(), expectedStatusCode, "The project was not created.");
        projectId = response.jsonPath().getString("id");
        /**
         * Create a new Story
         */
        String bodyStory = "{\"name\": \"API Automation Story\"}";
        Response responseStory = RequestManager.sendPostRequest("projects/".concat(projectId) + "/stories", bodyStory);
        final int expectedStatusCodeStory = 200;
        int actualStatusCode = responseStory.statusCode();
        Assert.assertEquals(actualStatusCode, expectedStatusCodeStory, "The expected status code does not match.");
        storiesId = JsonPathUtils.getValue(responseStory, "id");
    }

    /**
     * Test to create a project specifying name.
     */
    @Test
    public void updateStoryTest() {
        String body = "{\"current_state\":\"accepted\",\"estimate\":1,\"name\":\"new name\"}";
        System.out.println("StoryID " + body);
        String endpoint = "projects/" + projectId + "/stories/" + storiesId;
        Response response = RequestManager.sendPutRequest(endpoint, body);
        final int expectedStatusCode = 200;
        int actualStatusCode = response.statusCode();
        Assert.assertEquals(actualStatusCode, expectedStatusCode, "The expected status code does not match.");
        storiesId = JsonPathUtils.getValue(response, "id");
        AssertSchemaUtils.validateSchema(response, "weimar/putStoriesSchema.json");
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
