package com.automationlearn.pages.demoqa;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.automationlearn.base.DriverManager;

public class CheckBoxPage {

    // Expand all button — using aria-label which is more stable
    @FindBy(css = ".rct-option-expand-all")
    private WebElement expandAllButton;

    // Home checkbox label
    @FindBy(css = "label[for='tree-node-home']")
    private WebElement homeCheckBox;

    // Result text shown after checking
    @FindBy(id = "result")
    private WebElement resultText;

    public CheckBoxPage() {
        PageFactory.initElements(DriverManager.getDriver(), this);
    }

    public void expandAll() {
        expandAllButton.click();
    }

    public void checkHome() {
        homeCheckBox.click();
    }

    public String getResultText() {
        return resultText.getText();
    }

    public boolean isResultDisplayed() {
        try {
            return resultText.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}