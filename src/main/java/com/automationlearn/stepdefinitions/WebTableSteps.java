package com.automationlearn.stepdefinitions;

import com.automationlearn.base.DriverManager;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.time.Duration;
import java.util.List;
import java.util.Map;

public class WebTableSteps {

    String baseUrl = "https://demoqa.com";

    // Matches: "Given I am on the DemoQA web tables page"
    @Given("I am on the DemoQA web tables page")
    public void iAmOnWebTablesPage() {
        DriverManager.getDriver().get(baseUrl + "/webtables");
        try { Thread.sleep(1500); } catch (Exception e) {}
        System.out.println("✅ On DemoQA Web Tables page");
    }

    // Matches: "When I search for "Cierra""
    @When("I search for {string}")
    public void iSearchFor(String searchText) {
        WebElement searchBox = DriverManager.getDriver()
            .findElement(By.id("searchBox"));
        searchBox.clear();
        searchBox.sendKeys(searchText);
        try { Thread.sleep(1000); } catch (Exception e) {}
        System.out.println("✅ Searched for: " + searchText);
    }

    // Matches: "Then the table should contain "Cierra""
    @Then("the table should contain {string}")
    public void tableShouldContain(String expectedText) {
        String tableText = DriverManager.getDriver()
            .findElement(By.cssSelector("tbody"))
            .getText();
        Assert.assertTrue(tableText.contains(expectedText),
            expectedText + " not found in table!");
        System.out.println("✅ Table contains: " + expectedText);
    }

    // Matches: "When I click the Add button"
    @When("I click the Add button")
    public void iClickAddButton() {
        DriverManager.getDriver()
            .findElement(By.id("addNewRecordButton")).click();
        System.out.println("✅ Add button clicked");
    }

    // Matches the DataTable step in feature file
    // DataTable → reads the | table | in feature file
    @And("I fill the form with following details")
    public void iFillFormWithDetails(DataTable dataTable) {

        // Convert DataTable to list of maps
        // Each map = one row, key = column header
        List<Map<String, String>> data =
            dataTable.asMaps(String.class, String.class);

        // Get first row of data
        Map<String, String> row = data.get(0);

        WebDriverWait wait = new WebDriverWait(
            DriverManager.getDriver(), Duration.ofSeconds(10));

        // Wait for form
        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.id("firstName")));

        // Fill form using data from feature file
        DriverManager.getDriver()
            .findElement(By.id("firstName"))
            .sendKeys(row.get("firstName"));
        DriverManager.getDriver()
            .findElement(By.id("lastName"))
            .sendKeys(row.get("lastName"));
        DriverManager.getDriver()
            .findElement(By.id("userEmail"))
            .sendKeys(row.get("email"));
        DriverManager.getDriver()
            .findElement(By.id("age"))
            .sendKeys(row.get("age"));
        DriverManager.getDriver()
            .findElement(By.id("salary"))
            .sendKeys(row.get("salary"));
        DriverManager.getDriver()
            .findElement(By.id("department"))
            .sendKeys(row.get("department"));

        System.out.println("✅ Form filled with: " + row);
    }

    // Matches: "And I submit the form"
    @And("I submit the form")
    public void iSubmitForm() {
        DriverManager.getDriver()
            .findElement(By.id("submit")).click();
        try { Thread.sleep(1500); } catch (Exception e) {}
        System.out.println("✅ Form submitted");
    }
}