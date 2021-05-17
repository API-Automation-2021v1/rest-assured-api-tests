package com.jalasoft.api.tests.magdalena;

import com.jalasoft.api.client.RequestManager;
import com.jalasoft.api.utils.AssertSchemaUtils;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author Magdalena on 5/17/2021
 * @version 1.1
 */
public class PivotalPutEpicTests {

    private String projectId;
    private String epicId;
    private String endPoint;

    /**
     * Tests pre conditions.
     */
    @BeforeMethod
    public void createProject() {
        String projectBody = "{\"name\": \"API Automation\"}";
        String epicBody = "{\"name\": \"First Epic\"}";
        Response projectResponse = RequestManager.sendPostRequest("projects", projectBody);

        projectId = projectResponse.jsonPath().getString("id");
        endPoint = "projects/".concat(projectId);
        Response epicResponse = RequestManager.sendPostRequest(endPoint.concat("/epics"), epicBody);

        final int expectedStatusCode = 200;
        Assert.assertEquals(projectResponse.statusCode(), expectedStatusCode, "The project was not created.");

        Assert.assertEquals(epicResponse.statusCode(), expectedStatusCode, "The epic was not created.");

        epicId = epicResponse.jsonPath().getString("id");
    }

    /**
     * Test to modify a project providing a new name.
     */
    @Test
    public void modifyEpicNameTest() {
        String body = "{\"name\": \"Epic - name updated\"}";
        Response response = RequestManager.sendPutRequest(endPoint.concat("/epics/").concat(epicId), body);

        final int expectedStatusCode = 200;
        int actualStatusCode = response.statusCode();
        Assert.assertEquals(actualStatusCode, expectedStatusCode, "The expected status code does not match.");

        AssertSchemaUtils.validateSchema(response, "magdalena\\putEpicsSchema.json");

        String actualEpicName = response.jsonPath().getString("name");
        String expectedEpicName = "Epic - name updated";
        Assert.assertEquals(actualEpicName, expectedEpicName, "The Epic name does not match.");
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
