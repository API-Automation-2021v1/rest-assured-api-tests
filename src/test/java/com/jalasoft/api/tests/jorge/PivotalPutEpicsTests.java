package com.jalasoft.api.tests.jorge;

import com.jalasoft.api.client.RequestManager;
import com.jalasoft.api.tests.PivotalPutProjectTests;
import com.jalasoft.api.utils.AssertSchemaUtils;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Defines Pivotal Tracker PUT projects tests.
 */
public class PivotalPutEpicsTests {

    private PivotalPutProjectTests p = new PivotalPutProjectTests();
    private String epicId;

    /**
     * Tests pre conditions.
     */
    @BeforeMethod
    public void createEpic() {
        String body = "{\"name\": \"API Automation\"}";
        Response response = RequestManager.sendPostRequest("projects/" + p.getProjectId() + "/epics", body);

        final int expectedStatusCode = 200;
        Assert.assertEquals(response.statusCode(), expectedStatusCode, "The epic was not created.");

        epicId = response.jsonPath().getString("id");

    }

    /**
     * Test to modify a project providing a new name.
     */
    @Test
    public void modifyEpicNameTest() {
        String body = "{\"name\": \"API Automation - Epic Modified Name\"}";
        Response response = RequestManager.sendPutRequest("projects/".concat(p.getProjectId()), body);

        final int expectedStatusCode = 200;
        int actualStatusCode = response.statusCode();
        Assert.assertEquals(actualStatusCode, expectedStatusCode, "The expected status code does not match.");

        AssertSchemaUtils.validateSchema(response, "jorge/putProjectSchema.json");

        String actualProjectName = response.jsonPath().getString("name");
        String expectedProjectName = "API Automation - Modified Name";
        Assert.assertEquals(actualProjectName, expectedProjectName, "The project name does not match.");
    }

    /**
     * Tests post conditions.
     */
    @AfterMethod
    public void deleteProject() {
        if (epicId != null && !epicId.isEmpty()) {
            Response response = RequestManager.sendDeleteRequest("projects/" + p.getProjectId() + "epics" + epicId);

            final int expectedStatusCode = 204;
            Assert.assertEquals(response.statusCode(), expectedStatusCode, "The project was not deleted.");
        }
    }
}
