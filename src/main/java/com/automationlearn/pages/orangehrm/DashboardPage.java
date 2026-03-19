package com.automationlearn.pages.orangehrm;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.automationlearn.base.DriverManager;

public class DashboardPage {

    // Dashboard heading — visible after login
    @FindBy(xpath = "//h6[text()='Dashboard']")
    private WebElement dashboardHeading;

    // PIM menu item in left sidebar
    @FindBy(xpath = "//span[text()='PIM']")
    private WebElement pimMenu;

    // User profile icon — top right
    // Used for logout
    @FindBy(css = ".oxd-userdropdown-img")
    private WebElement userProfileIcon;

    // Logout option in dropdown
    @FindBy(xpath = "//a[text()='Logout']")
    private WebElement logoutOption;

    public DashboardPage() {
        PageFactory.initElements(DriverManager.getDriver(), this);
    }

    // Check if dashboard loaded after login
    public boolean isDashboardDisplayed() {
        try {
            return dashboardHeading.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Click PIM in left menu
    public void clickPIM() {
        pimMenu.click();
    }

    // Logout — click profile icon then logout
    public void logout() {
        userProfileIcon.click();

        // Wait for dropdown to appear
        try { Thread.sleep(1000); } catch (Exception e) {}

        logoutOption.click();
    }
}