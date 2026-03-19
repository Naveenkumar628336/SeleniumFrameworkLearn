package com.automationlearn.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.automationlearn.base.BaseTest;

public class SmokeTest extends BaseTest {
	
	
	
	@Test(description = "Verify Saucedemo loads")
	public void verifySauceDemoLoads() {
		navigateTo(sauceDemoUrl());
		String title = getDriver().getTitle();
		Assert.assertTrue(title.contains("Swag Labs"),
	            "SauceDemo did not load! Title: " + title);
	        System.out.println("SauceDemo loaded: " + title);
		
	}

    @Test(description = "Verify The Internet loads")
    public void verifyTheInternetLoads() {
        navigateTo(theInternetUrl());
        String title = getDriver().getTitle();
        Assert.assertTrue(title.contains("The Internet"),
            "The Internet did not load! Title: " + title);
        System.out.println("✅ The Internet loaded: " + title);
    }

    @Test(description = "Verify OrangeHRM loads")
    public void verifyOrangeHRMLoads() {
        navigateTo(orangeHRMUrl());
        String url = getDriver().getCurrentUrl();
        Assert.assertTrue(url.contains("orangehrmlive"),
            "OrangeHRM did not load! URL: " + url);
        System.out.println("✅ OrangeHRM loaded: " + url);
    }
}