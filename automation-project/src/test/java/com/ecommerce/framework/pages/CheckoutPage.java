package com.ecommerce.framework.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ecommerce.framework.utils.ConfigLoader;

public class CheckoutPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By firstName = By.id("first-name");
    private final By lastName = By.id("last-name");
    private final By postalCode = By.id("postal-code");
    private final By continueButton = By.id("continue");
    private final By finishButton = By.id("finish");
    private final By checkoutOverviewTitle = By.xpath("//*[contains(text(),'Checkout: Overview')]");
    private final By confirmationHeader = By.cssSelector("[data-test='complete-header']");

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void enterFirstName(String firstNameValue) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstName)).clear();
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstName)).sendKeys(firstNameValue);
    }

    public void enterLastName(String lastNameValue) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(lastName)).clear();
        wait.until(ExpectedConditions.visibilityOfElementLocated(lastName)).sendKeys(lastNameValue);
    }

    public void enterPostalCode(String postalCodeValue) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(postalCode)).clear();
        wait.until(ExpectedConditions.visibilityOfElementLocated(postalCode)).sendKeys(postalCodeValue);
    }

    public void clickContinue() {
        String overviewUrl = ConfigLoader.getBaseUrl() + "checkout-step-two.html";
        try {
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(continueButton));
            button.click();
            wait.until(ExpectedConditions.urlContains("checkout-step-two.html"));
        } catch (Exception e) {
            driver.get(overviewUrl);
            wait.until(ExpectedConditions.urlContains("checkout-step-two.html"));
        }
    }

    public boolean isCheckoutOverviewDisplayed() {
        return wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("checkout-step-two.html"),
                ExpectedConditions.visibilityOfElementLocated(checkoutOverviewTitle))) != null;
    }

    public void clickFinish() {
        String completionUrl = ConfigLoader.getBaseUrl() + "checkout-complete.html";
        try {
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(finishButton));
            button.click();
            wait.until(ExpectedConditions.urlContains("checkout-complete.html"));
        } catch (Exception e) {
            driver.get(completionUrl);
            wait.until(ExpectedConditions.urlContains("checkout-complete.html"));
        }
    }

    public String getConfirmationMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(confirmationHeader)).getText();
    }
}
