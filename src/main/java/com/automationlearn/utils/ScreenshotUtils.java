package com.automationlearn.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import com.automationlearn.base.DriverManager;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;

public class ScreenshotUtils {

    // Take screenshot and save to file
    // Returns the file path of saved screenshot
    public static String takeScreenshot(String testName) {

        // Create timestamp for unique file name
        // Example: LoginTest_2024-03-15_10-30-45.png
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss")
            .format(new Date());

        String fileName = testName + "_" + timestamp + ".png";
        String filePath = "reports/screenshots/" + fileName;

        try {
            // TakesScreenshot is built into Selenium
            // Cast driver to TakesScreenshot
            TakesScreenshot ts =
                (TakesScreenshot) DriverManager.getDriver();

            // Get screenshot as file
            File screenshot = ts.getScreenshotAs(OutputType.FILE);

            // Create screenshots folder if not exists
            File destFolder = new File("reports/screenshots/");
            if (!destFolder.exists()) {
                destFolder.mkdirs();
            }

            // Save screenshot to our folder
            File destFile = new File(filePath);
            FileUtils.copyFile(screenshot, destFile);

            System.out.println("📸 Screenshot saved: " + filePath);

        } catch (IOException e) {
            System.out.println("❌ Screenshot failed: "
                + e.getMessage());
        }

        return filePath;
    }
}