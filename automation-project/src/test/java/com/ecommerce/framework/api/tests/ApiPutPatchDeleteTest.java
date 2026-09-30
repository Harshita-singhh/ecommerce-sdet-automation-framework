package com.ecommerce.framework.api.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.ecommerce.framework.api.api.BaseApiTest;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ApiPutPatchDeleteTest extends BaseApiTest {

    @Test
    public void updatePostShouldReturnUpdatedData() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("id", 1);
        payload.put("title", "Updated SDET Test");
        payload.put("body", "Updated using PUT");
        payload.put("userId", 1);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .put("/posts/1");

        response.then()
                .statusCode(200)
                .contentType("application/json");

        Assert.assertEquals(response.jsonPath().getInt("id"), 1,
                "The updated post ID should remain 1");
        Assert.assertEquals(response.jsonPath().getString("title"), "Updated SDET Test",
                "The PUT response should reflect the updated title");
        Assert.assertEquals(response.jsonPath().getString("body"), "Updated using PUT",
                "The PUT response should reflect the updated body");
        Assert.assertEquals(response.jsonPath().getInt("userId"), 1,
                "The PUT response should keep the correct userId");
    }

    @Test
    public void patchPostShouldUpdateTitle() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("title", "Partially Updated SDET Test");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .patch("/posts/1");

        response.then()
                .statusCode(200)
                .contentType("application/json");

        Assert.assertEquals(response.jsonPath().getString("title"), "Partially Updated SDET Test",
                "PATCH should update only the title field in the response");
    }

    @Test
    public void deletePostShouldReturnSuccess() {
        Response response = given()
                .when()
                .delete("/posts/1");

        response.then()
                .statusCode(200)
                .contentType("application/json");

        Assert.assertEquals(response.asString(), "{}",
                "DELETE should return a successful empty JSON response for JSONPlaceholder");
    }
}
