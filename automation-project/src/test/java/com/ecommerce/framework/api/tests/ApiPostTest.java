package com.ecommerce.framework.api.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.ecommerce.framework.api.api.ApiConstants;
import com.ecommerce.framework.api.api.BaseApiTest;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ApiPostTest extends BaseApiTest {

    @Test
    public void createPostShouldReturn201() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("title", "SDET Automation");
        payload.put("body", "REST Assured API test");
        payload.put("userId", 1);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post(ApiConstants.POSTS_ENDPOINT);

        response.then()
                .statusCode(201)
                .contentType("application/json");

        Assert.assertEquals(response.jsonPath().getString("title"), "SDET Automation",
                "The created post title should match the request payload");
        Assert.assertEquals(response.jsonPath().getString("body"), "REST Assured API test",
                "The created post body should match the request payload");
        Assert.assertEquals(response.jsonPath().getInt("userId"), 1,
                "The created post should be assigned to userId 1");
        Assert.assertTrue(response.jsonPath().getInt("id") > 0,
                "The response should include an ID for the created post");
    }
}
