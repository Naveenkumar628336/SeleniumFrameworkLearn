package com.automationlearn.pages.orangehrm;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import com.automationlearn.base.DriverManager;

public class EmployeeListPage {

    // Employee List menu item
    @FindBy(xpath = "//a[text()='Employee List']")
    private WebElement employeeListMenu;

    // Search by name field
    @FindBy(xpath = "(//input[@class='oxd-input oxd-input--active'])[2]")
    private WebElement searchNameField;

    // Search button
    @FindBy(css = "button[type='submit']")
    private WebElement searchButton;

    // Table rows in results
    @FindBy(css = ".oxd-table-body .oxd-table-row")
    private List<WebElement> tableRows;

    public EmployeeListPage() {
        PageFactory.initElements(DriverManager.getDriver(), this);
    }

    // Go to Employee List page
    public void clickEmployeeList() {
        employeeListMenu.click();
    }

    // Search employee by name
    public void searchEmployee(String name) {
        searchNameField.clear();
        searchNameField.sendKeys(name);
        searchButton.click();
    }

    // Get total search results count
    public int getResultCount() {
        // Wait for results to load
        try { Thread.sleep(2000); } catch (Exception e) {}

        List<WebElement> rows = DriverManager.getDriver()
            .findElements(
                By.cssSelector(".oxd-table-body .oxd-table-row"));
        return rows.size();
    }

    // Check if employee name exists in results
    public boolean isEmployeeFound(String name) {
        try { Thread.sleep(2000); } catch (Exception e) {}

        List<WebElement> rows = DriverManager.getDriver()
            .findElements(
                By.cssSelector(".oxd-table-body .oxd-table-row"));

        System.out.println("Search results count: " + rows.size());

        for (WebElement row : rows) {
            String rowText = row.getText();
            System.out.println("Row: " + rowText);
            if (rowText.contains(name)) {
                return true;
            }
        }
        return false;
    }
}