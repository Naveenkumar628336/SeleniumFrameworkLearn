package com.automationlearn.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import com.automationlearn.base.BaseTest;
import com.automationlearn.pages.orangehrm.LoginPage;
import com.automationlearn.pages.orangehrm.DashboardPage;
import com.automationlearn.pages.orangehrm.AddEmployeePage;
import com.automationlearn.pages.orangehrm.EmployeeListPage;

public class OrangeHRMTest extends BaseTest {

    // ── Test 1: Login ─────────────────────────────────────
    // Scenario: Open URL → Login → Verify Dashboard
    @Test(description = "Login to OrangeHRM")
    public void testLogin() {

        // Step 1 — Open OrangeHRM
        navigateTo(orangeHRMUrl());

        // Step 2 — Create login page object
        LoginPage loginPage = new LoginPage();

        // Step 3 — Login with admin credentials
        loginPage.login("Admin", "admin123");

        // Step 4 — Wait for dashboard to load
        WebDriverWait wait = new WebDriverWait(
            getDriver(), Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//h6[text()='Dashboard']")));

        // Step 5 — Verify dashboard loaded
        DashboardPage dashboardPage = new DashboardPage();
        Assert.assertTrue(dashboardPage.isDashboardDisplayed(),
            "Dashboard not loaded after login!");

        System.out.println("✅ Login passed!");
        System.out.println("Current URL: "
            + getDriver().getCurrentUrl());
    }

    // ── Test 2: Add Employee ──────────────────────────────
    // Scenario: Login → PIM → Add Employee → Save → Verify
    @Test(description = "Add new employee")
    public void testAddEmployee() {

        // Login first
        navigateTo(orangeHRMUrl());
        LoginPage loginPage = new LoginPage();
        loginPage.login("Admin", "admin123");

        // Wait for dashboard
        WebDriverWait wait = new WebDriverWait(
            getDriver(), Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//h6[text()='Dashboard']")));

        System.out.println("✅ Logged in");

        // Click PIM menu
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.clickPIM();

        // Wait for PIM page to load
        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//a[text()='Add Employee']")));

        System.out.println("✅ PIM page loaded");

        // Click Add Employee
        AddEmployeePage addPage = new AddEmployeePage();
        addPage.clickAddEmployee();

        // Wait for Add Employee form
        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.name("firstName")));

        System.out.println("✅ Add Employee form opened");

        // Add employee
        addPage.addEmployee("Ravi", "Automation");

        // Wait for page to save
        // After save → redirects to employee profile page
        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//h6[text()='Personal Details']")));

        System.out.println("✅ Employee saved!");
        System.out.println("Current URL: "
            + getDriver().getCurrentUrl());

        Assert.assertTrue(
            getDriver().getCurrentUrl().contains("viewPersonalDetails"),
            "Not redirected to employee profile after save!");

        System.out.println("✅ Add Employee passed!");
    }

    // ── Test 3: Search Employee ───────────────────────────
    // Scenario: Login → PIM → Employee List → Search → Verify
    @Test(description = "Search existing employee")
    public void testSearchEmployee() {

        navigateTo(orangeHRMUrl());
        LoginPage loginPage = new LoginPage();
        loginPage.login("Admin", "admin123");

        WebDriverWait wait = new WebDriverWait(
            getDriver(), Duration.ofSeconds(15));

        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//h6[text()='Dashboard']")));

        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.clickPIM();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//a[text()='Employee List']")));

        getDriver().findElement(
            By.xpath("//a[text()='Employee List']")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("button[type='submit']")));
        System.out.println("✅ Employee List loaded");

        // Type in search box
        WebElement searchInput = getDriver().findElement(
            By.xpath(
                "//label[text()='Employee Name']" +
                "/following::input[1]"));
        searchInput.click();
        searchInput.sendKeys("Admin");

        // Wait for autocomplete suggestion
        try { Thread.sleep(2000); } catch (Exception e) {}

        try {
            WebElement suggestion = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector(".oxd-autocomplete-option")));
            suggestion.click();
            System.out.println("✅ Suggestion selected");
        } catch (Exception e) {
            System.out.println("No autocomplete suggestion");
        }

        // Click Search
        getDriver().findElement(
            By.cssSelector("button[type='submit']")).click();

        // Wait for results to fully load
        try { Thread.sleep(3000); } catch (Exception e) {}

        // Fix for StaleElementException:
        // Do NOT store rows in variable before checking
        // Find elements freshly each time
        // Wait until rows appear OR No Records Found
        wait.until(ExpectedConditions.or(
            ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".oxd-table-body .oxd-table-row")),
            ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[text()='No Records Found']"))
        ));

        // Now find rows freshly — no stale reference
        java.util.List<org.openqa.selenium.WebElement> rows =
            getDriver().findElements(
                By.cssSelector(".oxd-table-body .oxd-table-row"));

        System.out.println("Rows found: " + rows.size());

        // Print each row
        for (org.openqa.selenium.WebElement row : rows) {
            System.out.println("Row: " + row.getText());
        }

        Assert.assertTrue(rows.size() > 0,
            "No employee found in search results!");

        System.out.println("✅ Search passed!");
    }
    // ── Test 4: Logout ────────────────────────────────────
    // Scenario: Login → Logout → Verify login page shows
    @Test(description = "Logout from OrangeHRM")
    public void testLogout() {

        // Login
        navigateTo(orangeHRMUrl());
        LoginPage loginPage = new LoginPage();
        loginPage.login("Admin", "admin123");

        WebDriverWait wait = new WebDriverWait(
            getDriver(), Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//h6[text()='Dashboard']")));

        System.out.println("✅ Logged in");

        // Logout
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.logout();

        // Wait for login page to appear
        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.name("username")));

        // Verify login page is showing
        Assert.assertTrue(loginPage.isLoginPageDisplayed(),
            "Login page not shown after logout!");

        System.out.println("✅ Logout passed!");
        System.out.println("URL after logout: "
            + getDriver().getCurrentUrl());
    }
}