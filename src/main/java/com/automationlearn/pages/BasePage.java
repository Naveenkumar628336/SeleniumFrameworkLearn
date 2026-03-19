package com.automationlearn.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.v120.page.Page.SetWebLifecycleStateState;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

import com.automationlearn.base.DriverManager;
import com.automationlearn.utils.WaitUtils;

public class BasePage {
	
	protected WebDriver driver;
	protected Actions action;
	protected JavascriptExecutor js;
	
	public BasePage() {
		this.driver=DriverManager.getDriver();
		this.action=new Actions(driver);
		this.js=(JavascriptExecutor)driver;
		// Initializes all @FindBy elements in child Page classes
        PageFactory.initElements(driver, this);
	}
	 // Wait for clickable, then click
	protected void click(WebElement el) {
		 WaitUtils.waitForClickability(el).click();
	}
	 // Clear field, then type
	protected void type(WebElement el,String text) {
		WaitUtils.waitForVisibility(el).clear();
		el.sendKeys(text);
	}
	//get visibility text
	protected String getText(WebElement el) {
		return WaitUtils.waitForVisibility(el).getText();
	}
	
	 // Safe isDisplayed — won't throw exception
	
	protected boolean isDisplayed(WebElement el) {
		try {
			return el.isDisplayed();}
		catch(Exception e) { return false;	}
	}	
	// JS click — use when normal click is intercepted by overlay
    protected void jsClick(WebElement el) {
        js.executeScript("arguments[0].click();", el);
    }
    // Scroll element into view
    protected void scrollIntoView(WebElement el) {
        js.executeScript("arguments[0].scrollIntoView(true);", el);
    }
    //hover over element 
    protected void hover(WebElement el) {
    	action.moveToElement(el).perform();
    }
    protected String getTitle() {
    	return driver.getTitle();
    	    }
    protected String getCurrentURL() {
    	return driver.getCurrentUrl();
    }
    


}

