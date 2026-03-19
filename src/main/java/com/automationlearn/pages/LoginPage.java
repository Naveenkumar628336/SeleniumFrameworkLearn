package com.automationlearn.pages;



import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.automationlearn.base.DriverManager;

public class LoginPage {

    // Step 1: Declare locators using @FindBy
    // @FindBy replaces driver.findElement(By.id("user-name"))
	@FindBy(id = "user-name")
	private WebElement usernameField;
	
	  @FindBy(id = "password")
	    private WebElement passwordField;

	    @FindBy(id = "login-button")
	    private WebElement loginButton;

	    @FindBy(css = "[data-test='error']")
	    private WebElement errorMessage;
	    

	    // Step 2: Constructor — initializes all @FindBy elements
	    
	    public LoginPage()
	    {
	    	PageFactory.initElements(DriverManager.getDriver(), this);
	    }

	    // Step 3: Actions — simple methods that test will call
	    
	    public void enterUsername(String username) {
	    	usernameField.clear();
	    	usernameField.sendKeys(username);
	    }
	    public void enterPassword(String password) {
	        passwordField.clear();
	        passwordField.sendKeys(password);
	    }

	    public void clickLogin() {
	        loginButton.click();
	    }

	    // Combination method — login in one step
	    public void login(String username, String password) {
	        enterUsername(username);
	        enterPassword(password);
	        clickLogin();
	    }

	    // Verification methods — used in test assertions
	    public boolean isErrorDisplayed() {
	        try {
	            return errorMessage.isDisplayed();
	        } catch (Exception e) {
	            return false;
	        }
	    }

	    public String getErrorMessage() {
	        return errorMessage.getText();
	    }

	    public boolean isLoginButtonDisplayed() {
	        try {
	            return loginButton.isDisplayed();
	        } catch (Exception e) {
	            return false;
	        }
	    }
	}
