package com.automationlearn.pages.demoqa;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.automationlearn.base.DriverManager;

public class TextBoxPage {

    // Input field — Full Name
    @FindBy(id = "userName")
    private WebElement fullNameField;

    // Input field — Email
    @FindBy(id = "userEmail")
    private WebElement emailField;

    // Textarea — Current Address
    @FindBy(id = "currentAddress")
    private WebElement currentAddressField;

    // Textarea — Permanent Address
    @FindBy(id = "permanentAddress")
    private WebElement permanentAddressField;

    // Submit button
    @FindBy(id = "submit")
    private WebElement submitButton;

    // Output fields — shown after submit
    // These verify the form was submitted correctly
    @FindBy(id = "name")
    private WebElement outputName;

    @FindBy(id = "email")
    private WebElement outputEmail;

    public TextBoxPage() {
        PageFactory.initElements(DriverManager.getDriver(), this);
    }

    // Type full name in field
    public void enterFullName(String name) {
        fullNameField.clear();
        fullNameField.sendKeys(name);
    }

    // Type email in field
    public void enterEmail(String email) {
        emailField.clear();
        emailField.sendKeys(email);
    }

    // Type current address
    public void enterCurrentAddress(String address) {
        currentAddressField.clear();
        currentAddressField.sendKeys(address);
    }

    // Type permanent address
    public void enterPermanentAddress(String address) {
        permanentAddressField.clear();
        permanentAddressField.sendKeys(address);
    }

    // Click submit button
    // DemoQA has ads — sometimes button is hidden behind ad
    // We scroll to button first then click
    public void clickSubmit() {
        // Scroll button into view before clicking
        // This avoids ElementClickInterceptedException from ads
        ((org.openqa.selenium.JavascriptExecutor)
                DriverManager.getDriver())
                .executeScript("arguments[0].scrollIntoView(true);",
                        submitButton);
        submitButton.click();
    }

    // Fill entire form in one method
    public void fillForm(String name, String email,
                         String currentAddr, String permanentAddr) {
        enterFullName(name);
        enterEmail(email);
        enterCurrentAddress(currentAddr);
        enterPermanentAddress(permanentAddr);
    }

    // Get output name text after submit
    // Returns something like "Name:John"
    public String getOutputName() {
        return outputName.getText();
    }

    // Get output email text after submit
    public String getOutputEmail() {
        return outputEmail.getText();
    }

    // Check if output section is displayed
    public boolean isOutputDisplayed() {
        try {
            return outputName.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}