package com.automationlearn.pages.internet;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.automationlearn.base.DriverManager;

public class AlertsPage {

    // Locator for "Click for JS Alert" button
    // Using xpath because button has no id/class
    // @onclick='jsAlert()' means — find button whose onclick = jsAlert()
    @FindBy(xpath = "//button[@onclick='jsAlert()']")
    private WebElement jsAlertButton;

    // Locator for "Click for JS Confirm" button
    @FindBy(xpath = "//button[@onclick='jsConfirm()']")
    private WebElement jsConfirmButton;

    // Locator for "Click for JS Prompt" button
    @FindBy(xpath = "//button[@onclick='jsPrompt()']")
    private WebElement jsPromptButton;

    // Result text shown below after alert is handled
    // Example: "You successfully clicked an alert"
    @FindBy(id = "result")
    private WebElement resultText;

    // Constructor — always needed to activate @FindBy
    public AlertsPage() {
        PageFactory.initElements(DriverManager.getDriver(), this);
    }

    // ── Simple Alert Methods ──────────────────────────────

    // Click the button to trigger simple alert
    public void clickJsAlert() {
        jsAlertButton.click();
    }

    // Read what message the alert is showing
    // Must call this BEFORE accept/dismiss
    public String getAlertText() {
        Alert alert = DriverManager.getDriver().switchTo().alert();
        return alert.getText();
    }

    // Click OK on alert
    public void acceptAlert() {
        Alert alert = DriverManager.getDriver().switchTo().alert();
        alert.accept();
    }

    // ── Confirm Alert Methods ─────────────────────────────

    public void clickJsConfirm() {
        jsConfirmButton.click();
    }

    // Click OK on confirm
    public void acceptConfirm() {
        DriverManager.getDriver().switchTo().alert().accept();
    }

    // Click Cancel on confirm
    // dismiss() = Cancel button
    public void dismissConfirm() {
        DriverManager.getDriver().switchTo().alert().dismiss();
    }

    // ── Prompt Alert Methods ──────────────────────────────

    public void clickJsPrompt() {
        jsPromptButton.click();
    }

    // Type text in prompt box then click OK
    public void typeInPromptAndAccept(String text) {
        Alert alert = DriverManager.getDriver().switchTo().alert();
        alert.sendKeys(text);  // type in the prompt input box
        alert.accept();        // click OK
    }

    // ── Result Text ───────────────────────────────────────

    // Get the result text shown on page after alert handled
    public String getResultText() {
        return resultText.getText();
    }
}