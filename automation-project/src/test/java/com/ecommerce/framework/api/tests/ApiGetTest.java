package com.ecommerce.framework.api.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.ecommerce.framework.api.api.BaseApiTest;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ApiGetTest extends BaseApiTest {

    @Test
    public void getAllPostsShouldReturn200() {
        Response response = given()
                .when()
                .get("/posts");

        response.then()
                .statusCode(200)
                .contentType("application/json");

        Assert.assertTrue(response.jsonPath().getList("$").size() > 0,
                "The GET /posts response should contain at least one post");
    }

    @Test
    public void getPostByIdShouldReturnExpectedPost() {
        Response response = given()
                .pathParam("id", 1)
                .when()
                .get("/posts/{id}");

        response.then()
                .statusCode(200)
                .contentType("application/json");

        Assert.assertEquals(response.jsonPath().getInt("id"), 1,
                "The returned post ID should match the requested ID");
        Assert.assertFalse(response.jsonPath().getString("title").isEmpty(),
                "The title field should be present in the response");
        Assert.assertFalse(response.jsonPath().getString("body").isEmpty(),
                "The body field should be present in the response");
        Assert.assertEquals(response.jsonPath().getInt("userId"), 1,
                "The returned post should belong to userId 1");
    }

    @Test
    public void getPostsByUserIdShouldReturnMatchingPosts() {
        Response response = given()
                .queryParam("userId", 1)
                .when()
                .get("/posts");

        response.then()
                .statusCode(200)
                .contentType("application/json");

        List<Integer> userIds = response.jsonPath().getList("userId", Integer.class);
        Assert.assertFalse(userIds.isEmpty(), "The response should include posts for userId 1");

        for (Integer userId : userIds) {
            Assert.assertEquals(userId.intValue(), 1,
                    "Each returned post should belong to userId 1");
        }
    }

    @Test
    public void invalidPostIdShouldReturn404() {
        Response response = given()
                .when()
                .get("/posts/999999");

        response.then()
                .statusCode(404)
                .contentType("application/json");

        Assert.assertEquals(response.asString(), "{}",
                "An invalid post ID should return an empty JSON object");
    }

    @Test
    public void getCommentsForMissingPostIdShouldReturnEmptyArray() {
        Response response = given()
                .queryParam("postId", 999999)
                .when()
                .get("/comments");

        response.then()
                .statusCode(200)
                .contentType("application/json");

        Assert.assertEquals(response.asString(), "[]",
                "A missing postId should return an empty array for comments");
    }

    @Test
    public void getPostByIdShouldExtractIdFromResponse() {
        Response response = given()
                .pathParam("id", 1)
                .when()
                .get("/posts/{id}");

        int extractedId = response.jsonPath().getInt("id");

        Assert.assertEquals(extractedId, 1,
                "The extracted ID from the response should match the requested post");
    }
}
