package com.jalasoft.api.tests.shayra;

import com.jalasoft.api.client.RequestManager;
import com.jalasoft.api.utils.AssertSchemaUtils;
import com.jalasoft.api.utils.JsonPathUtils;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Pivotal Tracker POST Labels tests.
 */
public class PivotalPostLabelTest {

    private String projectId;

    /**
     * Tests pre conditions - Create Project.
     */
    @BeforeMethod
    public void createProject() {
        String body = "{\"name\": \"API Automation\"}";
        Response response = RequestManager.sendPostRequest("projects", body);

        final int expectedStatusCode = 200;
        Assert.assertEquals(response.statusCode(), expectedStatusCode, "The project was not created.");

        projectId = JsonPathUtils.getValue(response, "id");
    }

    /**
     * Test to create a Label on created project providing a name.
     */
    @Test
    public void createLabelTests() {
        String body = "{\"name\": \"my first label\"}";
        String endpoint = "projects/".concat(projectId).concat("/labels");

        Response response = RequestManager.sendPostRequest(endpoint, body);

        final int expectedStatusCode = 200;
        int actualStatusCode = response.statusCode();
        Assert.assertEquals(actualStatusCode, expectedStatusCode, "The expected status code does not match.");

        AssertSchemaUtils.validateSchema(response, "shayra/postLabelSchema.json");

        String actualLabelName = JsonPathUtils.getValue(response, "name");
        String expectedLabelName = "my first label";
        Assert.assertEquals(actualLabelName, expectedLabelName, "The label name does not match.");
    }

    /**
     * Tests post conditions - Delete Project.
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
