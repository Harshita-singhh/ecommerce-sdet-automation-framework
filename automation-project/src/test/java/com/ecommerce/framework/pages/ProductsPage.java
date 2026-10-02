package com.ecommerce.framework.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ecommerce.framework.utils.ConfigLoader;

public class ProductsPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By productsTitle = By.cssSelector(".title");
    private final By productItems = By.className("inventory_item");
    private final By cartLink = By.cssSelector(".shopping_cart_link");
    private final By cartBadge = By.cssSelector(".shopping_cart_badge");
    private final By cartTitle = By.cssSelector(".title");

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public boolean isProductsPageDisplayed() {
        WebElement title = wait.until(ExpectedConditions.visibilityOfElementLocated(productsTitle));
        return "Products".equals(title.getText());
    }

    public int getProductCount() {
        return driver.findElements(productItems).size();
    }

    public void addProductToCart(String productName) {
        By productLocator = By.xpath(
                ".//div[contains(@class,'inventory_item') and .//div[contains(@class,'inventory_item_name') and normalize-space(.)='" + productName + "']]//button");
        WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(productLocator));
        addButton.click();
        wait.until(ExpectedConditions.textToBe(productLocator, "Remove"));
    }

    public void removeProductFromCart(String productName) {
        By productLocator = By.xpath(
                ".//div[contains(@class,'inventory_item') and .//div[contains(@class,'inventory_item_name') and normalize-space(.)='" + productName + "']]//button");
        WebElement removeButton = wait.until(ExpectedConditions.elementToBeClickable(productLocator));
        removeButton.click();
    }

    public int getCartItemCount() {
        List<WebElement> cartItems = driver.findElements(cartBadge);
        if (cartItems.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(cartItems.get(0).getText());
    }

    public void openCart() {
        String cartUrl = ConfigLoader.getBaseUrl() + "cart.html";
        try {
            WebElement cart = wait.until(ExpectedConditions.elementToBeClickable(cartLink));
            cart.click();
            wait.until(ExpectedConditions.urlContains("cart.html"));
            wait.until(ExpectedConditions.visibilityOfElementLocated(cartTitle));
        } catch (Exception e) {
            driver.get(cartUrl);
            wait.until(ExpectedConditions.urlContains("cart.html"));
            wait.until(ExpectedConditions.visibilityOfElementLocated(cartTitle));
        }
    }
}
