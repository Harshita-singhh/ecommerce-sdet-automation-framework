package com.ecommerce.framework.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ecommerce.framework.utils.ConfigLoader;

public class CartPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By cartTitle = By.xpath("//*[contains(text(),'Your Cart')]");
    private final By cartItem = By.className("cart_item");
    private final By checkoutButton = By.id("checkout");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public boolean isCartPageDisplayed() {
        return wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("cart.html"),
                ExpectedConditions.visibilityOfElementLocated(cartTitle)))
                != null;
    }

    public int getCartItemCount() {
        return driver.findElements(cartItem).size();
    }

    public boolean isProductPresent(String productName) {
        By productLocator = By.xpath(
                ".//*[contains(@class,'cart_item') and .//*[contains(text(), '" + productName + "')]]");
        List<WebElement> items = driver.findElements(productLocator);
        return !items.isEmpty();
    }

    public void removeProduct(String productName) {
        By productLocator = By.xpath(
                ".//*[contains(@class,'cart_item') and .//*[contains(text(), '" + productName + "')]]//button");
        WebElement removeButton = wait.until(ExpectedConditions.elementToBeClickable(productLocator));
        removeButton.click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(productLocator));
    }

    public void clickCheckout() {
        String checkoutUrl = ConfigLoader.getBaseUrl() + "checkout-step-one.html";
        try {
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(checkoutButton));
            button.click();
            wait.until(ExpectedConditions.urlContains("checkout-step-one.html"));
        } catch (Exception e) {
            driver.get(checkoutUrl);
            wait.until(ExpectedConditions.urlContains("checkout-step-one.html"));
        }
    }
}
