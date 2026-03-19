package com.automationlearn.stepdefinitions;

import com.automationlearn.base.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {

    // Runs BEFORE each Scenario
    // Same as @BeforeMethod in TestNG
    @Before
    public void setUp(Scenario scenario) {
        System.out.println("\n=============================");
        System.out.println("Scenario: " + scenario.getName());
        System.out.println("=============================");

        // Initialize browser
        DriverManager.initDriver();
    }

    // Runs AFTER each Scenario
    // Same as @AfterMethod in TestNG
    @After
    public void tearDown(Scenario scenario) {

        // If scenario failed → take screenshot
        if (scenario.isFailed()) {
            System.out.println("❌ Scenario FAILED: "
                + scenario.getName());

            // Take screenshot and attach to Cucumber report
            byte[] screenshot = ((org.openqa.selenium.TakesScreenshot)
                DriverManager.getDriver())
                .getScreenshotAs(
                    org.openqa.selenium.OutputType.BYTES);

            // Attach screenshot to Cucumber HTML report
            scenario.attach(screenshot, "image/png",
                "Failure Screenshot");

        } else {
            System.out.println("✅ Scenario PASSED: "
                + scenario.getName());
        }

        // Close browser
        DriverManager.quitDriver();
    }
}