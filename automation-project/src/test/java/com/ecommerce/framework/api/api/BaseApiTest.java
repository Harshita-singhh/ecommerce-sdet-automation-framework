package com.ecommerce.framework.api.api;

import org.testng.annotations.BeforeClass;

import static io.restassured.RestAssured.baseURI;

public class BaseApiTest {

    @BeforeClass(alwaysRun = true)
    public void setUpBaseApi() {
        baseURI = ApiConstants.BASE_URL;
    }
}
