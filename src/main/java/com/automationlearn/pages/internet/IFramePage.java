package com.automationlearn.pages.internet;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.automationlearn.base.DriverManager;

public class IFramePage {

    // iFrame element on the main page
    @FindBy(id = "mce_0_ifr")
    private WebElement iFrame;

    public IFramePage() {
        PageFactory.initElements(DriverManager.getDriver(), this);
    }

    // Switch INTO the iFrame
    public void switchToIFrame() {
        DriverManager.getDriver().switchTo().frame(iFrame);
    }

    // Switch BACK to main page
    public void switchToMainPage() {
        DriverManager.getDriver().switchTo().defaultContent();
    }

    public void typeTextInEditor(String text) {

        // Step 1 — switch into iFrame
        switchToIFrame();

        // Step 2 — find the editor body inside iFrame
        WebElement editorBody = DriverManager.getDriver()
                .findElement(By.id("tinymce"));

        // Step 3 — use JavaScript to set innerHTML directly
        // This completely replaces whatever text is inside
        // Why JavaScript? Because TinyMCE is a rich text editor
        // Normal sendKeys and clear() do not work reliably on it
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].innerHTML = arguments[1]", 
                         editorBody, text);

        // Step 4 — switch back to main page
        switchToMainPage();
    }

    public String getEditorText() {

        // Switch into iFrame to read content
        switchToIFrame();

        String text = DriverManager.getDriver()
                .findElement(By.id("tinymce"))
                .getText();

        // Always switch back after reading
        switchToMainPage();

        return text;
    }
}