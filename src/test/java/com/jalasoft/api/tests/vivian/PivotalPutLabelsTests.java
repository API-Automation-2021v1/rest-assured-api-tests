package com.jalasoft.api.tests.vivian;

import com.jalasoft.api.client.RequestManager;
import com.jalasoft.api.utils.AssertSchemaUtils;
import com.jalasoft.api.utils.JsonPathUtils;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterClass;

public class PivotalPutLabelsTests {
    private String projectId;
    private String labelId;

    /**
     * Tests pre conditions.
     */
    @BeforeClass
    public void createProjectTest() {
        String body = "{\"name\": \"API Automation\"}";
        Response response = RequestManager.sendPostRequest("projects", body);

        final int expectedStatusCode = 200;
        int actualStatusCode = response.statusCode();
        Assert.assertEquals(actualStatusCode, expectedStatusCode, "The expected status code does not match.");

        projectId = JsonPathUtils.getValue(response, "id");

        AssertSchemaUtils.validateSchema(response, "postProjectSchema.json");

        String actualProjectName = JsonPathUtils.getValue(response, "name");
        String expectedProjectName = "API Automation";
        Assert.assertEquals(actualProjectName, expectedProjectName, "The project name does not match.");
    }

    /**
     * Tests pre conditions.
     */
    @BeforeMethod
    public void createLabelTest() {
        String body = "{\"name\":\"testing creation label\"}";
        String endPointFirstPart = "projects/".concat(projectId);
        String endPointFinal = endPointFirstPart.concat("/labels");
        Response response = RequestManager.sendPostRequest(endPointFinal, body);

        final int expectedStatusCode = 200;
        int actualStatusCode = response.statusCode();
        Assert.assertEquals(actualStatusCode, expectedStatusCode,
                "The expected status code does not match in the creation of label.");

        String actualLabelName = JsonPathUtils.getValue(response, "name");
        String expectedLabelName = "testing creation label";
        Assert.assertEquals(actualLabelName, expectedLabelName,
                "The expected name of label does not match in the creation of label.");

        String actualProjectId = JsonPathUtils.getValue(response, "project_id");
        Assert.assertEquals(actualProjectId, projectId,
                "The expected project ID does not match in the creation of label.");
        labelId = JsonPathUtils.getValue(response, "id");

        AssertSchemaUtils.validateSchema(response, "/vivian/postLabelsSchema.json");
    }

    /**
     * Test update labels in a project.
     */
    @Test
    public void putLabel() {
        String body = "{\"name\":\"updating label name\"}";
        String endPointFirstPart = "projects/".concat(projectId);
        String endPointSecondPart = endPointFirstPart.concat("/labels/");
        String endPointFinal = endPointSecondPart.concat(labelId);
        Response response = RequestManager.sendPutRequest(endPointFinal, body);

        final int expectedStatusCode = 200;
        int actualStatusCode = response.statusCode();
        Assert.assertEquals(actualStatusCode, expectedStatusCode,
                "The expected status code does not match in the updating of label.");

        String actualLabelName = JsonPathUtils.getValue(response, "name");
        String expectedLabelName = "updating label name";
        Assert.assertEquals(actualLabelName, expectedLabelName,
                "The expected name of label does not match in the updating of label.");

        String actualProjectId = JsonPathUtils.getValue(response, "project_id");
        Assert.assertEquals(actualProjectId, projectId,
                "The expected project ID does not match in the updating of label.");

        String actualLabelId = JsonPathUtils.getValue(response, "id");
        Assert.assertEquals(actualLabelId, labelId, "The expected label ID does not match in the updating of label.");

        AssertSchemaUtils.validateSchema(response, "/vivian/putLabelsSchema.json");
    }

    /**
     * Tests post conditions.
     */
    @AfterMethod
    public void deleteLabel() {
        if (labelId != null && !labelId.isEmpty()) {
            String endPointFirstPart = "projects/".concat(projectId);
            String endPointSecondPart = endPointFirstPart.concat("/labels/");
            String endPointFinal = endPointSecondPart.concat(labelId);
            Response response = RequestManager.sendDeleteRequest(endPointFinal);
            final int expectedStatusCode = 204;
            Assert.assertEquals(response.statusCode(), expectedStatusCode, "The label was not deleted.");
        }
    }

    /**
     * Tests post conditions.
     */
    @AfterClass
    public void deleteProject() {
        if (projectId != null && !projectId.isEmpty()) {
            Response response = RequestManager.sendDeleteRequest("projects/".concat(projectId));

            final int expectedStatusCode = 204;
            Assert.assertEquals(response.statusCode(), expectedStatusCode, "The project was not deleted.");
        }
    }
}
