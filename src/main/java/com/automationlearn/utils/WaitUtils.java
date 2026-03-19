package com.automationlearn.utils;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.automationlearn.base.DriverManager;

public class WaitUtils {
	private static WebDriverWait getWait() {
		return new WebDriverWait(DriverManager.getDriver(),Duration.ofSeconds(ConfigReader.getExplicitWait()));
	}
	 // Wait until element is visible
	public static WebElement waitForVisibility(WebElement el) {
		return getWait().until(ExpectedConditions.visibilityOf(el));
	}
	public static WebElement waitForVisibility(By locator) {
		return getWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	// Wait until element is visible AND enabled (safe to click)
	public static WebElement waitForClickability(WebElement el) {
		return getWait().until(ExpectedConditions.elementToBeClickable(el));
	}	
	public static WebElement waitForClickability(By locator) {
		return getWait().until(ExpectedConditions.elementToBeClickable(locator));
	}

    // Wait until element disappears (loaders, spinners)
	public static Boolean waitForInvisibility(WebElement el) {
		return getWait().until(ExpectedConditions.invisibilityOf(el));
	}
	 // Wait until URL contains fragment (e.g. after navigation)
    public static boolean waitForUrlContains(String fragment) {
        return getWait().until(ExpectedConditions.urlContains(fragment));
    }
    // Switch into a frame and wait for it
    public static WebDriver waitAndSwitchToFrame(By frameLocator) {
        return getWait().until(
            ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
    }
    // Wait for browser alert
    public static void waitForAlert() {
    	getWait().until(ExpectedConditions.alertIsPresent());
    	
    }
    }
	

