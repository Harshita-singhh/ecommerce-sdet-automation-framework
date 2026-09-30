package com.ecommerce.framework.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.ecommerce.framework.pages.CartPage;
import com.ecommerce.framework.pages.LoginPage;
import com.ecommerce.framework.pages.ProductsPage;

public class CartTest extends BaseTest {
    private static final String USERNAME = "standard_user";
    private static final String PASSWORD = "secret_sauce";
    private static final String PRODUCT_NAME = "Sauce Labs Backpack";

    @Test
    public void addProductAndRemoveItFromCart() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(USERNAME, PASSWORD);

        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.addProductToCart(PRODUCT_NAME);
        productsPage.openCart();

        CartPage cartPage = new CartPage(driver);
        Assert.assertTrue(cartPage.isCartPageDisplayed(), "Cart page should be displayed");
        Assert.assertTrue(cartPage.isProductPresent(PRODUCT_NAME),
                "Selected product should be present in the cart");
        Assert.assertEquals(cartPage.getCartItemCount(), 1,
                "The cart should contain exactly one product");

        cartPage.removeProduct(PRODUCT_NAME);

        Assert.assertFalse(cartPage.isProductPresent(PRODUCT_NAME),
                "Removed product should no longer be present in the cart");
        Assert.assertEquals(cartPage.getCartItemCount(), 0,
                "The cart should be empty after removing the product");
    }
}
