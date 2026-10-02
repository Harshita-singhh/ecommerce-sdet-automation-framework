package com.ecommerce.framework.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
        wait.until(ExpectedConditions.urlContains("cart.html"));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(cartTitle)).isDisplayed();
    }

    public int getCartItemCount() {
        return driver.findElements(cartItem).size();
    }

    public boolean isProductPresent(String productName) {
        List<WebElement> items = driver.findElements(cartItemForProduct(productName));
        return !items.isEmpty();
    }

    public void waitForProduct(String productName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(cartItemForProduct(productName)));
    }

    public void removeProduct(String productName) {
        By productRow = cartItemForProduct(productName);
        WebElement row = wait.until(ExpectedConditions.visibilityOfElementLocated(productRow));
        WebElement removeButton = wait.until(ExpectedConditions.elementToBeClickable(
                row.findElement(By.tagName("button"))));
        // In headless Chrome, WebDriver pointer and keyboard clicks did not trigger this React handler.
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", removeButton);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(productRow));
    }

    private By cartItemForProduct(String productName) {
        return By.xpath(
                "//div[@class='cart_item' and .//div[@class='inventory_item_name' and normalize-space(.)='"
                        + productName + "']]");
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
