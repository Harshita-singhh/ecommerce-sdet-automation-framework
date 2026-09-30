package com.ecommerce.framework.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.ecommerce.framework.pages.CartPage;
import com.ecommerce.framework.pages.CheckoutPage;
import com.ecommerce.framework.pages.LoginPage;
import com.ecommerce.framework.pages.ProductsPage;

public class CheckoutTest extends BaseTest {
    private static final String USERNAME = "standard_user";
    private static final String PASSWORD = "secret_sauce";
    private static final String PRODUCT_NAME = "Sauce Labs Backpack";

    @Test
    public void checkoutShouldCompleteSuccessfully() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(USERNAME, PASSWORD);

        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.addProductToCart(PRODUCT_NAME);
        productsPage.openCart();

        CartPage cartPage = new CartPage(driver);
        cartPage.clickCheckout();

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.enterFirstName("Harshita");
        checkoutPage.enterLastName("Test");
        checkoutPage.enterPostalCode("282001");
        checkoutPage.clickContinue();

        Assert.assertTrue(checkoutPage.isCheckoutOverviewDisplayed(),
                "Checkout overview should be visible after continuing");

        checkoutPage.clickFinish();

        Assert.assertEquals(checkoutPage.getConfirmationMessage(), "Thank you for your order!",
                "Order confirmation should be displayed after finishing checkout");
    }
}
