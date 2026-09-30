package com.ecommerce.framework;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class OpenSauceDemoTest {

    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void sauceDemoUserCanLoginAndViewProducts() {
        driver.get("https://www.saucedemo.com/");

        Assert.assertEquals(driver.getTitle(), "Swag Labs",
                "SauceDemo login page title should be Swag Labs");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement usernameField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));

        WebElement passwordField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.name("password")));

        WebElement loginButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("input#login-button")));

        Assert.assertTrue(usernameField.isDisplayed(),
                "Username field should be visible on the login page");

        Assert.assertTrue(passwordField.isDisplayed(),
                "Password field should be visible on the login page");

        usernameField.clear();
        usernameField.sendKeys("standard_user");

        passwordField.clear();
        passwordField.sendKeys("secret_sauce");

        loginButton.click();

        WebElement productsHeader = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//span[@class='title' and text()='Products']")));

        Assert.assertEquals(productsHeader.getText(), "Products",
                "Products page title should be visible after login");

        Assert.assertTrue(driver.getCurrentUrl().contains("/inventory.html"),
                "User should be redirected to the inventory page after login");
    }

    @Test
    public void invalidLoginShouldShowErrorMessage() {
        driver.get("https://www.saucedemo.com/");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Find username
        WebElement usernameField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("user-name")));

        // Find password
        WebElement passwordField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.name("password")));

        // Find login button
        WebElement loginButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("input#login-button")));

        // Enter valid username
        usernameField.clear();
        usernameField.sendKeys("standard_user");

        // Enter wrong password
        passwordField.clear();
        passwordField.sendKeys("wrong_password");

        // Click Login
        loginButton.click();

        // Find error
        WebElement errorMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("[data-test='error']")));

        // Assert exact error text
        Assert.assertEquals(
                errorMessage.getText(),
                "Epic sadface: Username and password do not match any user in this service");
    }
}