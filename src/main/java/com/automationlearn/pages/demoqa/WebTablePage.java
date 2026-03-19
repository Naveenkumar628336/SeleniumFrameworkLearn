package com.automationlearn.pages.demoqa;

import java.util.List;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.automationlearn.base.DriverManager;

public class WebTablePage {

    @FindBy(id = "addNewRecordButton")
    private WebElement addButton;

    @FindBy(id = "searchBox")
    private WebElement searchBox;

    // Each row group in the table
    @FindBy(css = ".rt-tr-group")
    private List<WebElement> tableRows;

    // Registration form fields
    @FindBy(id = "firstName")
    private WebElement firstNameField;

    @FindBy(id = "lastName")
    private WebElement lastNameField;

    @FindBy(id = "userEmail")
    private WebElement emailField;

    @FindBy(id = "age")
    private WebElement ageField;

    @FindBy(id = "salary")
    private WebElement salaryField;

    @FindBy(id = "department")
    private WebElement departmentField;

    @FindBy(id = "submit")
    private WebElement submitButton;

    public WebTablePage() {
        PageFactory.initElements(DriverManager.getDriver(), this);
    }

    public void clickAddButton() {
        addButton.click();
    }

    public void addNewRecord(String firstName, String lastName,
                              String email, String age,
                              String salary, String department) {
        firstNameField.sendKeys(firstName);
        lastNameField.sendKeys(lastName);
        emailField.sendKeys(email);
        ageField.sendKeys(age);
        salaryField.sendKeys(salary);
        departmentField.sendKeys(department);
        submitButton.click();

        // Wait for table to update after submit
        // DemoQA table takes a moment to refresh
        try { Thread.sleep(1000); } catch (Exception e) {}
    }

    public void searchRecord(String text) {
        searchBox.clear();
        searchBox.sendKeys(text);

        // Wait for table to filter after search
        try { Thread.sleep(1000); } catch (Exception e) {}
    }

    // Check if specific text exists in any table row
    public boolean isTextPresentInTable(String text) {
        // Re-find rows after table updates
        // Why? Because after search/add — old WebElement list is stale
        List<WebElement> rows = DriverManager.getDriver()
                .findElements(org.openqa.selenium.By.cssSelector(".rt-tr-group"));

        for (WebElement row : rows) {
            if (row.getText().contains(text)) {
                return true;
            }
        }
        return false;
    }

    public int getRowCount() {
        int count = 0;
        for (WebElement row : tableRows) {
            if (!row.getText().trim().isEmpty()) {
                count++;
            }
        }
        return count;
    }
}