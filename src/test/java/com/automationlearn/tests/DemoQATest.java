package com.automationlearn.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.automationlearn.base.BaseTest;
import com.automationlearn.pages.demoqa.TextBoxPage;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class DemoQATest extends BaseTest {

    String baseUrl = "https://demoqa.com";

    // ═══════════════════════════════════════════════════
    // HELPER METHOD — reused by both Test 3 and Test 4
    // ═══════════════════════════════════════════════════

    /**
     * Searches for a value in web table
     * Prints full row details if found
     * Returns true if found, false if not found
     *
     * @param searchValue — any value to search
     *                      can be name, email, department etc
     */
    private boolean getRowDetails(String searchValue) {

        // Step 1 — Get all header names from <thead>
        List<WebElement> headers = getDriver()
            .findElements(By.cssSelector("thead tr th"));

        // Store header names in list
        List<String> headerNames = new ArrayList<>();
        for (WebElement header : headers) {
            String headerText = header.getText().trim();
            // Skip empty and Action column
            if (!headerText.isEmpty()
                    && !headerText.equals("Action")) {
                headerNames.add(headerText);
            }
        }
        System.out.println("Headers: " + headerNames);

        // Step 2 — Get all rows from <tbody>
        List<WebElement> rows = getDriver()
            .findElements(By.cssSelector("tbody tr"));
        System.out.println("Total rows: " + rows.size());

        // Step 3 — Loop through each row
        for (WebElement row : rows) {

            // Check if this row contains search value
            if (row.getText().contains(searchValue)) {

                // Step 4 — Get all cells in matching row
                List<WebElement> cells = row
                    .findElements(By.cssSelector("td"));

                // Step 5 — Print full details
                System.out.println("\n=============================");
                System.out.println("✅ Record found: " + searchValue);
                System.out.println("=============================");

                // Combine header name + cell value
                for (int i = 0; i < headerNames.size(); i++) {
                    if (i < cells.size()) {
                        System.out.println(
                            headerNames.get(i)
                            + " : "
                            + cells.get(i).getText().trim());
                    }
                }
                System.out.println("=============================\n");

                // Found — return true
                return true;
            }
        }

        // Not found in any row — return false
        System.out.println("❌ Not found: " + searchValue);
        return false;
    }

    // ═══════════════════════════════════════════════════
    // TEST 1 — Text Box Form
    // ═══════════════════════════════════════════════════
    @Test(description = "Fill and submit text box form")
    public void testTextBoxForm() {

        navigateTo(baseUrl + "/text-box");
        TextBoxPage textBoxPage = new TextBoxPage();

        textBoxPage.fillForm(
            "John Doe",
            "john@test.com",
            "123 Main Street",
            "456 Park Avenue"
        );

        textBoxPage.clickSubmit();

        Assert.assertTrue(textBoxPage.isOutputDisplayed(),
            "Output not displayed after submit!");

        System.out.println("✅ Text Box passed!");
        System.out.println("Name  : " + textBoxPage.getOutputName());
        System.out.println("Email : " + textBoxPage.getOutputEmail());
    }

    // ═══════════════════════════════════════════════════
    // TEST 2 — CheckBox
    // ═══════════════════════════════════════════════════
    @Test(description = "Check Home checkbox and verify result")
    public void testCheckBox() {

        navigateTo(baseUrl + "/checkbox");

        WebDriverWait wait = new WebDriverWait(
            getDriver(), Duration.ofSeconds(10));

        try { Thread.sleep(1500); } catch (Exception e) {}

        // Click expand span
        WebElement expandSpan = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.cssSelector(
                    ".rc-tree-switcher.rc-tree-switcher_close")));
        expandSpan.click();
        System.out.println("✅ Tree expanded!");

        try { Thread.sleep(800); } catch (Exception e) {}

        // Click Home checkbox
        WebElement homeCheckbox = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.cssSelector("span[aria-label='Select Home']")));
        homeCheckbox.click();
        System.out.println("✅ Home checked!");

        try { Thread.sleep(800); } catch (Exception e) {}

        // Verify result
        WebElement result = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.id("result")));

        String resultText = result.getText();
        Assert.assertTrue(resultText.contains("home"),
            "home not in result! Found: " + resultText);

        System.out.println("✅ CheckBox passed! Result: " + resultText);
    }

    // ═══════════════════════════════════════════════════
    // TEST 3 — Web Table Search
    // Uses helper method getRowDetails()
    // ═══════════════════════════════════════════════════
    @Test(description = "Search record in web table")
    public void testWebTableSearch() {

        navigateTo(baseUrl + "/webtables");

        WebDriverWait wait = new WebDriverWait(
            getDriver(), Duration.ofSeconds(10));

        // Wait for table to load
        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("tbody")));

        // ── Search by first name ──────────────────────
        System.out.println("\n--- Searching: Cierra ---");
        boolean foundByFirstName = getRowDetails("Cierra");
        Assert.assertTrue(foundByFirstName,
            "Cierra not found in table!");

        // ── Search by last name ───────────────────────
        System.out.println("\n--- Searching: Vega ---");
        boolean foundByLastName = getRowDetails("Vega");
        Assert.assertTrue(foundByLastName,
            "Vega not found in table!");

        // ── Search by department ──────────────────────
        System.out.println("\n--- Searching: Insurance ---");
        boolean foundByDept = getRowDetails("Insurance");
        Assert.assertTrue(foundByDept,
            "Insurance not found in table!");

        System.out.println("✅ All searches passed!");
    }

    // ═══════════════════════════════════════════════════
    // TEST 4 — Web Table Add Record
    // Uses helper method getRowDetails()
    // ═══════════════════════════════════════════════════
    @Test(description = "Add new record and verify in table")
    public void testWebTableAddRecord() {

        navigateTo(baseUrl + "/webtables");

        WebDriverWait wait = new WebDriverWait(
            getDriver(), Duration.ofSeconds(10));

        // Wait for table to load
        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("tbody")));

        // Store new record values
        String firstName  = "Ravi";
        String lastName   = "Kumar";
        String email      = "ravi@test.com";
        String age        = "30";
        String salary     = "50000";
        String department = "QA";

        // Click Add button
        WebElement addBtn = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.id("addNewRecordButton")));
        addBtn.click();
        System.out.println("✅ Add button clicked");

        // Wait for form
        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.id("firstName")));

        // Fill form
        getDriver().findElement(By.id("firstName"))
            .sendKeys(firstName);
        getDriver().findElement(By.id("lastName"))
            .sendKeys(lastName);
        getDriver().findElement(By.id("userEmail"))
            .sendKeys(email);
        getDriver().findElement(By.id("age"))
            .sendKeys(age);
        getDriver().findElement(By.id("salary"))
            .sendKeys(salary);
        getDriver().findElement(By.id("department"))
            .sendKeys(department);

        System.out.println("✅ Form filled");

        // Submit
        getDriver().findElement(By.id("submit")).click();
        System.out.println("✅ Form submitted");

        // Wait for form to close
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
            By.id("firstName")));

        // Wait for table to refresh
        try { Thread.sleep(1500); } catch (Exception e) {}

        // Use helper method to find and print details
        // Same method used in Test 3 — no duplicate code!
        System.out.println("\n--- Verifying added record ---");
        boolean found = getRowDetails(firstName);

        Assert.assertTrue(found,
            firstName + " not found after adding!");

        System.out.println("✅ Add record passed!");
    }
}