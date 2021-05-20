package com.jalasoft.api.tests.naira;

import com.jalasoft.api.client.RequestManager;
import com.jalasoft.api.utils.AssertSchemaUtils;
import com.jalasoft.api.utils.JsonPathUtils;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Defines Pivotal Tracker PUT labels tests.
 */
public class PivotalPutLabelTests {
    private String projectId = "";
    private String labelId =  "";
    private String projectUrl = "";

    /**
     * Tests pre conditions.
     */
    @BeforeMethod
    public void createProject() {
        String body = "{\"name\": \"API Automation01\"}";
        Response response = RequestManager.sendPostRequest("projects", body);

        final int expectedStatusCode = 200;
        Assert.assertEquals(response.statusCode(), expectedStatusCode, "The project was not created.");

        projectId = response.jsonPath().getString("id");
        projectUrl = "projects/".concat(projectId);
        createLabelTest();
    }

    /**
     * Tests pre conditions.
     */
    public void createLabelTest() {
        String body = "{\"name\": \"LabelTwo\"}";
        Response response = RequestManager.sendPostRequest(projectUrl.concat("/labels"), body);

        final int expectedStatusCode = 200;
        int actualStatusCode = response.statusCode();
        Assert.assertEquals(actualStatusCode, expectedStatusCode, "The expected status code does not -create label.");

        labelId = JsonPathUtils.getValue(response, "id");
    }
        /**
         * Test to modify a label providing a new name.
         */
    @Test
    public void modifyLabelNameTest() {
        String body = "{\"name\": \"LabelOne - Modified Name\"}";
        Response response = RequestManager.sendPutRequest(projectUrl.concat("/labels/").concat(labelId), body);

        final int expectedStatusCode = 200;
        int actualStatusCode = response.statusCode();
        Assert.assertEquals(actualStatusCode, expectedStatusCode, "The expected status code does not matchc.");

        AssertSchemaUtils.validateSchema(response, "naira\\putLabelSchema.json");

        String actualProjectName = response.jsonPath().getString("name");
        String expectedProjectName = "labelone - modified name";
        Assert.assertEquals(actualProjectName, expectedProjectName, "The project name does not match.");
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
