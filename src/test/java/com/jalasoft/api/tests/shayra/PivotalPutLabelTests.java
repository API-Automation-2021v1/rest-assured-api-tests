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
 * Pivotal Tracker PUT Labels tests.
 */
public class PivotalPutLabelTests {

    private String projectId;
    private String labelId;

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
     * Tests pre conditions - Create Label.
     */
    @BeforeMethod (dependsOnMethods = {"createProject"})
    public void createLabel() {
        String body = "{\"name\": \"my first label\"}";
        String endpoint = "projects/".concat(projectId).concat("/labels");

        Response response = RequestManager.sendPostRequest(endpoint, body);

        final int expectedStatusCode = 200;
        Assert.assertEquals(response.statusCode(), expectedStatusCode, "The label was not created.");

        labelId = JsonPathUtils.getValue(response, "id");
    }

    /**
     * Test to modify a label providing a new name.
     */
    @Test
    public void modifyLabelNameTest() {
        String body = "{\"name\": \"my first label - modified\"}";
        String endpoint = "projects/".concat(projectId).concat("/labels/").concat(labelId);

        Response response = RequestManager.sendPutRequest(endpoint, body);

        final int expectedStatusCode = 200;
        int actualStatusCode = response.statusCode();
        Assert.assertEquals(actualStatusCode, expectedStatusCode, "The expected status code does not match.");

        AssertSchemaUtils.validateSchema(response, "shayra/putLabelSchema.json");

        String actualLabelName = JsonPathUtils.getValue(response, "name");
        String expectedLabelName = "my first label - modified";
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
