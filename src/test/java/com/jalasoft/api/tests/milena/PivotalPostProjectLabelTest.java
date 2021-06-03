package com.jalasoft.api.tests.milena;

import com.jalasoft.api.client.RequestManager;
import com.jalasoft.api.utils.AssertSchemaUtils;
import com.jalasoft.api.utils.JsonPathUtils;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 * Defines Pivotal Tracker POST Label tests.
 */
public class PivotalPostProjectLabelTest {
    private String projectId;
    private static final Logger LOGGER = LogManager.getLogger(PivotalPostProjectLabelTest.class);

    /**
     * * Test to create a Label specifying name.
     */
    @Test
    void createLabelTest() {
        var projectName = "Project with Labels Mile to test POST";
        var body = "{\"name\": \"" + projectName + "\"}";
        var response = RequestManager.sendPostRequest("projects", body);
        projectId = JsonPathUtils.getValue(response, "id");
        var labelName = "label mile";
        var bodyLabel = "{\"name\": \"" + labelName + "\",\"project_id\": \"" + projectId + "\" }";
        var responseLabel = RequestManager.sendPostRequest("projects/" + projectId + "/labels", bodyLabel);

        final int expectedStatusCode = 200;
        int actualStatusCode = responseLabel.statusCode();
        Assert.assertEquals(actualStatusCode, expectedStatusCode, "The expected status code does not match.");

        AssertSchemaUtils.validateSchema(responseLabel, "milena/postLabelSchema.json");

        String actualLabelName = JsonPathUtils.getValue(responseLabel, "name");
        String expectedLabelName = "label mile";
        Assert.assertEquals(actualLabelName, expectedLabelName, "The project name does not match.");

    }

    /**
     * Clean the Project.
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
