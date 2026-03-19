package com.automationlearn.pages.orangehrm;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.automationlearn.base.DriverManager;

public class AddEmployeePage {

    // Add Employee button on PIM page
    @FindBy(xpath = "//a[text()='Add Employee']")
    private WebElement addEmployeeButton;

    // First Name field
    @FindBy(name = "firstName")
    private WebElement firstNameField;

    // Middle Name field
    @FindBy(name = "middleName")
    private WebElement middleNameField;

    // Last Name field
    @FindBy(name = "lastName")
    private WebElement lastNameField;

    // Employee ID field — auto generated but can edit
    @FindBy(css = ".oxd-input.oxd-input--active")
    private WebElement employeeIdField;

    // Save button
    @FindBy(css = "button[type='submit']")
    private WebElement saveButton;

    // Success message after saving
    @FindBy(xpath = "//div[@class='oxd-toast-content']")
    private WebElement successToast;

    public AddEmployeePage() {
        PageFactory.initElements(DriverManager.getDriver(), this);
    }

    // Click Add Employee button
    public void clickAddEmployee() {
        addEmployeeButton.click();
    }

    // Fill employee name
    public void enterFirstName(String firstName) {
        firstNameField.clear();
        firstNameField.sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        lastNameField.clear();
        lastNameField.sendKeys(lastName);
    }

    // Click Save button
    public void clickSave() {
        saveButton.click();
    }

    // Fill and save in one method
    public void addEmployee(String firstName, String lastName) {
        enterFirstName(firstName);
        enterLastName(lastName);
        clickSave();
    }

    // Check if we are on Add Employee page
    public boolean isAddEmployeePageDisplayed() {
        try {
            return firstNameField.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}