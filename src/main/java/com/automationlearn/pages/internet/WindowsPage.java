package com.automationlearn.pages.internet;

import java.util.ArrayList;
import java.util.Set;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.automationlearn.base.DriverManager;

public class WindowsPage {

    // The "Click Here" link that opens new window
    @FindBy(linkText = "Click Here")
    private WebElement clickHereLink;

    // Store original window handle
    // We need this to switch back later
    private String originalWindowHandle;

    public WindowsPage() {
        PageFactory.initElements(DriverManager.getDriver(), this);

        // Save the current window handle when page object is created
        // At this point only 1 window is open — so this is the original
        originalWindowHandle = DriverManager.getDriver().getWindowHandle();
        System.out.println("Original window handle: " + originalWindowHandle);
    }

    // Click the link that opens new window
    public void clickNewWindowLink() {
        clickHereLink.click();
    }

    // Switch to the newly opened window
    public void switchToNewWindow() {
        WebDriver driver = DriverManager.getDriver();

        // Get all open window handles
        // Set is like a list but no duplicates
        Set<String> allWindowHandles = driver.getWindowHandles();

        System.out.println("Total windows open: " + allWindowHandles.size());

        // Loop through all handles
        // Switch to the one that is NOT the original window
        for (String handle : allWindowHandles) {
            if (!handle.equals(originalWindowHandle)) {
                driver.switchTo().window(handle);
                System.out.println("Switched to new window: " + handle);
                break; // stop loop once we found and switched
            }
        }
    }

    // Switch back to original window
    public void switchToOriginalWindow() {
        DriverManager.getDriver().switchTo().window(originalWindowHandle);
        System.out.println("Switched back to original window");
    }

    // Close current window
    // Call this when you are done with new window
    public void closeCurrentWindow() {
        DriverManager.getDriver().close();
    }

    // Get title of current page
    // Use after switching to verify which window you are on
    public String getCurrentWindowTitle() {
        return DriverManager.getDriver().getTitle();
    }

    // Get heading text on current page
    public String getHeadingText() {
        return DriverManager.getDriver()
                .findElement(org.openqa.selenium.By.tagName("h3"))
                .getText();
    }
}