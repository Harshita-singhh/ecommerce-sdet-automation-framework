package com.ecommerce.framework.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By cartTitle = By.xpath("//span[@class='title' and text()='Your Cart']");
    private final By cartItem = By.className("cart_item");
    private final By checkoutButton = By.id("checkout");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isCartPageDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(cartTitle)).isDisplayed();
    }

    public int getCartItemCount() {
        return driver.findElements(cartItem).size();
    }

    public boolean isProductPresent(String productName) {
        By productLocator = By.xpath(
                ".//div[contains(@class,'cart_item') and .//div[contains(@class,'inventory_item_name') and normalize-space(.)='" + productName + "']]");
        List<WebElement> items = driver.findElements(productLocator);
        return !items.isEmpty();
    }

    public void removeProduct(String productName) {
        By productLocator = By.xpath(
                ".//div[contains(@class,'cart_item') and .//div[contains(@class,'inventory_item_name') and normalize-space(.)='" + productName + "']]//button");
        WebElement removeButton = wait.until(ExpectedConditions.elementToBeClickable(productLocator));
        removeButton.click();
    }

    public void clickCheckout() {
        wait.until(ExpectedConditions.elementToBeClickable(checkoutButton)).click();
    }
}
