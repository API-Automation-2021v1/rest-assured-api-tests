package com.jalasoft.api.tests.milena;

import com.jalasoft.api.client.RequestManager;
import com.jalasoft.api.utils.AssertSchemaUtils;
import com.jalasoft.api.utils.JsonPathUtils;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Defines Pivotal Tracker PUT Label tests.
 */
public class PivotalPutProjectLabelTest {
    private String projectId;
    private String labelId;


    /**
     * Tests pre conditions.
     */
    @BeforeMethod
    void createLabelTest() {
        var projectName = "Project with Labels Mile to test PUT";
        var body = "{\"name\": \"" + projectName + "\"}";
        var response = RequestManager.sendPostRequest("projects", body);
        projectId = JsonPathUtils.getValue(response, "id");

        var labelName = "label mile";
        var bodyLabel = "{\"name\": \"" + labelName + "\",\"project_id\": \"" + projectId + "\" }";
        var responseLabel = RequestManager.sendPostRequest("projects/" + projectId + "/labels", bodyLabel);

        final int expectedStatusCode = 200;
        int actualStatusCode = responseLabel.statusCode();
        Assert.assertEquals(actualStatusCode, expectedStatusCode, "The expected status code does not match.");
        labelId = responseLabel.jsonPath().getString("id");

    }
        /**
         * Test to modify a Label providing a new name.
         */
    @Test
    public void modifyLabelNameTest() {

        var labelName = "label mile - modified name";
        var bodyLabel = "{\"name\": \"" + labelName + "\" }";
        var endPoint = "projects/" + projectId + "/labels/" + labelId + "";
        var responseLabel = RequestManager.sendPutRequest(endPoint, bodyLabel);

        final int expectedStatusCode = 200;
        int actualStatusCode = responseLabel.statusCode();

        Assert.assertEquals(actualStatusCode, expectedStatusCode, "The expected status code does not match.");
        AssertSchemaUtils.validateSchema(responseLabel, "milena/putLabelSchema.json");

        String actualLabelName = JsonPathUtils.getValue(responseLabel, "name");
        String expectedLabelName = "label mile - modified name";
        Assert.assertEquals(actualLabelName, expectedLabelName, "The project name does not match.");

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
