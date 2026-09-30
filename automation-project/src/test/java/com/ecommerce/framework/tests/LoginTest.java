package com.ecommerce.framework.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.ecommerce.framework.pages.LoginPage;
import com.ecommerce.framework.pages.ProductsPage;

public class LoginTest extends BaseTest {
    private static final String USERNAME = "standard_user";
    private static final String PASSWORD = "secret_sauce";
    private static final String INVALID_PASSWORD = "wrong_password";
    private static final String INVALID_LOGIN_MESSAGE =
            "Epic sadface: Username and password do not match any user in this service";

    @Test
    public void validLoginShouldDisplayProductsPage() {
        LoginPage loginPage = new LoginPage(driver);

        loginPage.login(USERNAME, PASSWORD);

        ProductsPage productsPage = new ProductsPage(driver);
        Assert.assertTrue(productsPage.isProductsPageDisplayed(),
                "Products page should be visible after a valid login");
    }

    @Test
    public void invalidLoginShouldDisplayErrorMessage() {
        LoginPage loginPage = new LoginPage(driver);

        loginPage.login(USERNAME, INVALID_PASSWORD);

        Assert.assertEquals(loginPage.getErrorMessage(), INVALID_LOGIN_MESSAGE,
                "Invalid login should show the expected error message");
    }
}
