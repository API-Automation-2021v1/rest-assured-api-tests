package com.jalasoft.api.tests.magdalena;

import com.jalasoft.api.client.RequestManager;
import com.jalasoft.api.utils.AssertSchemaUtils;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author Magdalena on 5/16/2021
 * @version 1.1
 */
public class PivotalPostEpicTests {

    private String projectId;
    private String endPoint;

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
        endPoint = "projects/".concat(projectId);
    }

    /**
     * Test to create a epic with the name.
     */
    @Test
    public void createEpicTest() {
        String body = "{\"name\": \"API Automation - Epic\"}";
        Response response = RequestManager.sendPostRequest(endPoint.concat("/epics"), body);

        final int expectedStatusCode = 200;
        int actualStatusCode = response.statusCode();
        Assert.assertEquals(actualStatusCode, expectedStatusCode, "The expected status code does not match.");

        AssertSchemaUtils.validateSchema(response, "magdalena\\postEpicsSchema.json");

        String actualEpicName = response.jsonPath().getString("name");
        String expectedEpicName = "API Automation - Epic";
        Assert.assertEquals(actualEpicName, expectedEpicName, "Epic name does not match.");
    }

    /**
     * Tests post conditions.
     */
    @AfterMethod
    public void deleteProject() {
        if (projectId != null && !projectId.isEmpty()) {
            Response response = RequestManager.sendDeleteRequest(endPoint);

            final int expectedStatusCode = 204;
            Assert.assertEquals(response.statusCode(), expectedStatusCode, "The project was not deleted.");
        }
    }
}
