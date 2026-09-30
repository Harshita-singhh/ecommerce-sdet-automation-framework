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
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void sauceDemoLoginPageShouldLoad() {
        driver.get("https://www.saucedemo.com/");

        Assert.assertEquals(driver.getTitle(), "Swag Labs", "SauceDemo login page title should be Swag Labs");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement usernameField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));

        Assert.assertTrue(usernameField.isDisplayed(), "Username field should be visible on the login page");
    }
}
