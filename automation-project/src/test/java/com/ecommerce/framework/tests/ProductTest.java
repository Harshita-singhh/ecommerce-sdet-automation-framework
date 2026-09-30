package com.ecommerce.framework.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.ecommerce.framework.pages.LoginPage;
import com.ecommerce.framework.pages.ProductsPage;

public class ProductTest extends BaseTest {
    private static final String USERNAME = "standard_user";
    private static final String PASSWORD = "secret_sauce";
    private static final String PRODUCT_NAME = "Sauce Labs Backpack";

    @Test
    public void productsShouldBeDisplayedAfterLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(USERNAME, PASSWORD);

        ProductsPage productsPage = new ProductsPage(driver);

        Assert.assertTrue(productsPage.isProductsPageDisplayed(),
                "Products page should be displayed after login");
        Assert.assertTrue(productsPage.getProductCount() > 0,
                "At least one product should be visible on the page");
    }

    @Test
    public void addProductToCartShouldIncreaseCartCount() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(USERNAME, PASSWORD);

        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.addProductToCart(PRODUCT_NAME);

        Assert.assertEquals(productsPage.getCartItemCount(), 1,
                "Cart count should increase when a product is added");
    }

    @Test
    public void removeProductFromCartShouldDecreaseCartCount() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(USERNAME, PASSWORD);

        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.addProductToCart(PRODUCT_NAME);
        productsPage.removeProductFromCart(PRODUCT_NAME);

        Assert.assertEquals(productsPage.getCartItemCount(), 0,
                "Cart count should decrease after removing the product");
    }
}
