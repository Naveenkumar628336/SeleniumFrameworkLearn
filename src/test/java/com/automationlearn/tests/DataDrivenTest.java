package com.automationlearn.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.automationlearn.base.BaseTest;
import com.automationlearn.pages.LoginPage;
import com.automationlearn.pages.ProductsPage;
import com.automationlearn.utils.ExcelReader;

public class DataDrivenTest extends BaseTest {

    // ═══════════════════════════════════════════════════
    // @DataProvider — provides data to test method
    // name = "loginData" → test method refers to this name
    // returns Object[][] → 2D array
    //   first []  → each row = one test run
    //   second [] → each column = one parameter
    // ═══════════════════════════════════════════════════
    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {

        // Each row = { username, password, expectedResult }
        // Test runs once for EACH row
        return new Object[][] {

            // Row 1 — Valid login → should succeed
            { "standard_user", "secret_sauce", "success" },

            // Row 2 — Locked user → should show error
            { "locked_out_user", "secret_sauce", "error" },

            // Row 3 — Wrong password → should show error
            { "standard_user", "wrong_password", "error" },

            // Row 4 — Empty username → should show error
            { "", "secret_sauce", "error" },

            // Row 5 — Problem user → should succeed
            { "problem_user", "secret_sauce", "success" }
        };
    }

    // ═══════════════════════════════════════════════════
    // Test method — uses dataProvider = "loginData"
    // TestNG automatically passes each row as parameters
    // ═══════════════════════════════════════════════════
    @Test(
        dataProvider = "loginData",
        description = "Login with multiple data sets"
    )
    public void testLoginWithMultipleData(
            String username,
            String password,
            String expectedResult) {

        System.out.println("\n--- Test Run ---");
        System.out.println("Username : " + username);
        System.out.println("Password : " + password);
        System.out.println("Expected : " + expectedResult);

        // Go to SauceDemo
        navigateTo(sauceDemoUrl());

        // Create page objects
        LoginPage loginPage = new LoginPage();

        // Perform login
        loginPage.login(username, password);

        // Verify based on expected result
        if (expectedResult.equals("success")) {

            // Should reach products page
            ProductsPage productsPage = new ProductsPage();
            Assert.assertTrue(
                productsPage.isProductsPageDisplayed(),
                "Login should succeed for: " + username);

            System.out.println("✅ Login succeeded as expected!");

        } else {

            // Should show error message
            Assert.assertTrue(
                loginPage.isErrorDisplayed(),
                "Error should show for: " + username);

            System.out.println("✅ Error shown as expected: "
                + loginPage.getErrorMessage());
        }
    }

    // ═══════════════════════════════════════════════════
    // @DataProvider for different scenarios
    // ═══════════════════════════════════════════════════
    @DataProvider(name = "searchData")
    public Object[][] getSearchData() {
        return new Object[][] {
            { "Cierra",    true  },   // exists → true
            { "Vega",      true  },   // exists → true
            { "Insurance", true  },   // exists → true
            { "XYZ123",    false }    // not exists → false
        };
    }

    @Test(
        dataProvider = "searchData",
        description = "Search table with multiple data sets"
    )
    public void testTableSearchWithMultipleData(
            String searchValue,
            boolean shouldExist) {

        System.out.println("\n--- Search Test ---");
        System.out.println("Search  : " + searchValue);
        System.out.println("Expected: " + shouldExist);

        navigateTo("https://demoqa.com/webtables");

        // Wait for table
        try { Thread.sleep(1500); } catch (Exception e) {}

        // Type in search box
        org.openqa.selenium.WebElement searchBox =
            getDriver().findElement(
                org.openqa.selenium.By.id("searchBox"));
        searchBox.clear();
        searchBox.sendKeys(searchValue);

        try { Thread.sleep(1000); } catch (Exception e) {}

        // Get table text
        String tableText = getDriver()
            .findElement(
                org.openqa.selenium.By.cssSelector("tbody"))
            .getText();

        boolean actualResult = tableText.contains(searchValue);

        System.out.println("Table text: " + tableText);
        System.out.println("Found: " + actualResult);

        Assert.assertEquals(actualResult, shouldExist,
            "Search result mismatch for: " + searchValue);

        System.out.println("✅ Search test passed!");
    }
 // ═══════════════════════════════════════════════════
 // @DataProvider reading from Excel file
 // ═══════════════════════════════════════════════════
 @DataProvider(name = "excelLoginData")
 public Object[][] getExcelLoginData() {

     // Create ExcelReader with file path and sheet name
     ExcelReader excel = new ExcelReader(
         "testdata/LoginData.xlsx",
         "LoginData"
     );

     // Get all data from Excel
     Object[][] data = excel.getAllData();

     // Close Excel file
     excel.close();

     System.out.println("Rows from Excel: " + data.length);
     return data;
 }

 @Test(
     dataProvider = "excelLoginData",
     description = "Login test with Excel data"
 )
 public void testLoginWithExcelData(
         String username,
         String password,
         String expected) {

     System.out.println("\n--- Excel Data Test ---");
     System.out.println("Username : " + username);
     System.out.println("Password : " + password);
     System.out.println("Expected : " + expected);

     navigateTo(sauceDemoUrl());

     LoginPage loginPage = new LoginPage();
     loginPage.login(username, password);

     if (expected.equals("success")) {
         ProductsPage productsPage = new ProductsPage();
         Assert.assertTrue(
             productsPage.isProductsPageDisplayed(),
             "Should succeed for: " + username);
         System.out.println("✅ Success as expected!");
     } else {
         Assert.assertTrue(
             loginPage.isErrorDisplayed(),
             "Should show error for: " + username);
         System.out.println("✅ Error as expected: "
             + loginPage.getErrorMessage());
     }
 }
}