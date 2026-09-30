package com.ecommerce.framework.listeners;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.ecommerce.framework.tests.BaseTest;

public class ScreenshotListener implements ITestListener {
    private static final String SCREENSHOT_DIR = "target/screenshots";

    @Override
    public void onTestFailure(ITestResult result) {
        Object instance = result.getInstance();
        if (!(instance instanceof BaseTest)) {
            return;
        }

        WebDriver driver = ((BaseTest) instance).getDriver();
        if (driver == null) {
            return;
        }

        File screenshotDir = new File(SCREENSHOT_DIR);
        if (!screenshotDir.exists() && !screenshotDir.mkdirs()) {
            return;
        }

        String fileName = result.getInstanceName() + "-"
                + result.getName() + "-"
                + System.currentTimeMillis() + ".png";

        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        Path destination = screenshotDir.toPath().resolve(fileName);

        try {
            Files.copy(screenshot.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to save screenshot for " + result.getName(), e);
        }
    }
}
