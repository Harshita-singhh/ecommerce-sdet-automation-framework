package com.ecommerce.framework.api.api;

import org.testng.annotations.BeforeClass;

import com.ecommerce.framework.utils.ConfigLoader;

import static io.restassured.RestAssured.baseURI;

public class BaseApiTest {

    @BeforeClass(alwaysRun = true)
    public void setUpBaseApi() {
        baseURI = ConfigLoader.getApiBaseUrl();
    }
}
