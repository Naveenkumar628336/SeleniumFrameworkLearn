package com.automationlearn.base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.automationlearn.utils.ConfigReader;

public class BaseTest {

	 /**
     * Runs BEFORE each @Test method.
     * @Parameters lets testng.xml pass a browser per test group.
     * @Optional = not mandatory, falls back to config.properties.
     */
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({"browser"})
	public void setup(@Optional String browser) {
		    if(browser != null && !browser.isEmpty()) {
		    	System.setProperty("browser", browser);
		    	
		    }
		    DriverManager.initDriver();
	}
	 /**
     * Runs AFTER each @Test — even on failure.
     * Always closes browser to avoid memory leaks.
     */
	@AfterMethod(alwaysRun = true)
	public void teardown() {
		DriverManager.quitDriver();
	}
	// Shortcut: test classes use getDriver() instead of DriverManager.getDriver()
    protected WebDriver getDriver() {
        return DriverManager.getDriver();
    }

    protected void navigateTo(String url) {
        getDriver().get(url);
    }

    // URL shortcuts used in test classes
    protected String sauceDemoUrl()   { return ConfigReader.getSauceDemoUrl(); }
    protected String demoQAUrl()      { return ConfigReader.getDemoQAUrl(); }
    protected String theInternetUrl() { return ConfigReader.getTheInternetUrl(); }
    protected String orangeHRMUrl()   { return ConfigReader.getOrangeHRMUrl(); }
}
