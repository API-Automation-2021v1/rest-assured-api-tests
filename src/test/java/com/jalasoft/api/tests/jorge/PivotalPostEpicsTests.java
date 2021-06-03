package com.jalasoft.api.tests.jorge;

import com.jalasoft.api.client.RequestManager;
import com.jalasoft.api.tests.PivotalPostProjectTests;
import com.jalasoft.api.utils.AssertSchemaUtils;
import com.jalasoft.api.utils.JsonPathUtils;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 * Defines Pivotal Tracker POST projects tests.
 */
public class PivotalPostEpicsTests {
    private PivotalPostProjectTests p = new PivotalPostProjectTests();
    private String epicId;

    /**
     * Test to create a project specifying name.
     */
    @Test
    public void createEpicTest() {
        String body = "{\"name\": \"API Automation - Epic\"}";
        Response response = RequestManager.sendPostRequest("projects/" + p.getProjectId() + "/epics", body);

        final int expectedStatusCode = 200;
        int actualStatusCode = response.statusCode();
        Assert.assertEquals(actualStatusCode, expectedStatusCode, "The expected status code does not match.");

        epicId = JsonPathUtils.getValue(response, "id");

        AssertSchemaUtils.validateSchema(response, "jorge/putEpicsSchema.json");

        String actualProjectName = JsonPathUtils.getValue(response, "name");
        String expectedProjectName = "API Automation - Epic";
        Assert.assertEquals(actualProjectName, expectedProjectName, "The Epic name does not match.");
    }

    /**
     * Tests post conditions.
     */
    @AfterMethod
    public void deleteEpic() {
        if (epicId != null && !epicId.isEmpty()) {
            Response response = RequestManager.sendDeleteRequest("projects/" + p.getProjectId() + "epics" + epicId);

            final int expectedStatusCode = 204;
            Assert.assertEquals(response.statusCode(), expectedStatusCode, "The Epic was not deleted.");
        }
    }
}
