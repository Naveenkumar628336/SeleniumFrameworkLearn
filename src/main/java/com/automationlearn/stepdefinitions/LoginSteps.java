package com.automationlearn.stepdefinitions;

import com.automationlearn.base.DriverManager;
import com.automationlearn.pages.LoginPage;
import com.automationlearn.pages.ProductsPage;
import com.automationlearn.utils.ConfigReader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class LoginSteps {

    // Page objects declared here
    // Shared between step methods
    private LoginPage loginPage;
    private ProductsPage productsPage;

    // ── Given Steps ───────────────────────────────────────

    // Matches: "Given I am on the SauceDemo login page"
    @Given("I am on the SauceDemo login page")
    public void iAmOnSauceDemoLoginPage() {
        // Navigate to SauceDemo
        DriverManager.getDriver()
            .get(ConfigReader.getSauceDemoUrl());

        // Create page object
        loginPage = new LoginPage();

        System.out.println("✅ Opened SauceDemo login page");
    }

    // ── When Steps ────────────────────────────────────────

    // Matches: "When I enter username "standard_user""
    // {string} → captures text inside quotes
    @When("I enter username {string}")
    public void iEnterUsername(String username) {
        loginPage.enterUsername(username);
        System.out.println("✅ Entered username: " + username);
    }

    // Matches: "And I enter password "secret_sauce""
    @And("I enter password {string}")
    public void iEnterPassword(String password) {
        loginPage.enterPassword(password);
        System.out.println("✅ Entered password");
    }

    // Matches: "And I click the login button"
    @And("I click the login button")
    public void iClickLoginButton() {
        loginPage.clickLogin();
        System.out.println("✅ Clicked login button");
    }

    // ── Then Steps ────────────────────────────────────────

    // Matches: "Then I should see the products page"
    @Then("I should see the products page")
    public void iShouldSeeProductsPage() {
        productsPage = new ProductsPage();
        Assert.assertTrue(
            productsPage.isProductsPageDisplayed(),
            "Products page not displayed!");
        System.out.println("✅ Products page displayed!");
    }

    // Matches: "Then I should see an error message"
    @Then("I should see an error message")
    public void iShouldSeeErrorMessage() {
        Assert.assertTrue(
            loginPage.isErrorDisplayed(),
            "Error message not displayed!");
        System.out.println("✅ Error message: "
            + loginPage.getErrorMessage());
    }

    // Matches Scenario Outline:
    // "Then I should see {string}"
    @Then("I should see {string}")
    public void iShouldSee(String expected) {

        if (expected.equals("products page")) {
            productsPage = new ProductsPage();
            Assert.assertTrue(
                productsPage.isProductsPageDisplayed(),
                "Products page not shown!");
            System.out.println("✅ Products page shown!");

        } else if (expected.equals("error message")) {
            Assert.assertTrue(
                loginPage.isErrorDisplayed(),
                "Error message not shown!");
            System.out.println("✅ Error shown: "
                + loginPage.getErrorMessage());
        }
    }
}