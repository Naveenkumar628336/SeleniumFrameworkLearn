package com.automationlearn.pages.internet;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import com.automationlearn.base.DriverManager;

public class DropdownPage {

    // The dropdown element
    // In HTML: <select id="dropdown">
    @FindBy(id = "dropdown")
    private WebElement dropdown;

    public DropdownPage() {
        PageFactory.initElements(DriverManager.getDriver(), this);
    }

    // Way 1 — Select by the text you see on screen
    // Example: selectByText("Option 1")
    public void selectByText(String text) {
        // First wrap the WebElement in Select class
        // Select class only works on <select> HTML elements
        Select select = new Select(dropdown);
        select.selectByVisibleText(text);
    }

    // Way 2 — Select by value attribute in HTML
    // Example: selectByValue("1")
    // In HTML: <option value="1">Option 1</option>
    public void selectByValue(String value) {
        Select select = new Select(dropdown);
        select.selectByValue(value);
    }

    // Way 3 — Select by index position
    // Example: selectByIndex(1) selects second option
    // Index starts from 0
    // 0 = "Please select an option"
    // 1 = "Option 1"
    // 2 = "Option 2"
    public void selectByIndex(int index) {
        Select select = new Select(dropdown);
        select.selectByIndex(index);
    }

    // Get the text of currently selected option
    // Use this to verify which option is selected
    public String getSelectedOptionText() {
        Select select = new Select(dropdown);
        // getFirstSelectedOption() returns the selected WebElement
        // then we call getText() to get its text
        return select.getFirstSelectedOption().getText();
    }

    // Get total number of options in dropdown
    public int getTotalOptions() {
        Select select = new Select(dropdown);
        return select.getOptions().size();
    }
}