package com.automationlearn.pages.orangehrm;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.automationlearn.base.DriverManager;

public class LoginPage {

    // Username input field
    // Using name attribute as locator
    @FindBy(name = "username")
    private WebElement usernameField;

    // Password input field
    @FindBy(name = "password")
    private WebElement passwordField;

    // Login button
    @FindBy(css = "button[type='submit']")
    private WebElement loginButton;

    public LoginPage() {
        PageFactory.initElements(DriverManager.getDriver(), this);
    }

    // Type username
    public void enterUsername(String username) {
        usernameField.clear();
        usernameField.sendKeys(username);
    }

    // Type password
    public void enterPassword(String password) {
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    // Click login button
    public void clickLogin() {
        loginButton.click();
    }

    // Login in one step
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    // Check if login page is showing
    public boolean isLoginPageDisplayed() {
        try {
            return loginButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}