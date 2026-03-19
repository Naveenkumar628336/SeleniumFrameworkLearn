package com.automationlearn.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.automationlearn.base.BaseTest;
import com.automationlearn.pages.LoginPage;
import com.automationlearn.pages.ProductsPage;

public class LoginTest extends BaseTest {

    // ── Test 1: Valid Login ──────────────────────────────
    @Test(description = "Login with valid credentials")
    public void testValidLogin() {

        // Step 1: Go to SauceDemo
        navigateTo(sauceDemoUrl());

        // Step 2: Create page object
        LoginPage loginPage = new LoginPage();

        // Step 3: Perform login
        loginPage.login("standard_user", "secret_sauce");

        // Step 4: Verify we reached products page
        ProductsPage productsPage = new ProductsPage();
        Assert.assertTrue(
            productsPage.isProductsPageDisplayed(),
            "Products page did not load after login!"
        );
        System.out.println("✅ Login successful! Title: " + productsPage.getPageTitle());
    }

    // ── Test 2: Invalid Login ────────────────────────────
    @Test(description = "Login with wrong password shows error")
    public void testInvalidLogin() {

        navigateTo(sauceDemoUrl());
        LoginPage loginPage = new LoginPage();

        loginPage.login("standard_user", "wrong_password");

        Assert.assertTrue(
            loginPage.isErrorDisplayed(),
            "Error message not shown for invalid login!"
        );
        System.out.println("✅ Error shown: " + loginPage.getErrorMessage());
    }

    // ── Test 3: Empty Username ───────────────────────────
    @Test(description = "Login with empty username shows error")
    public void testEmptyUsername() {

        navigateTo(sauceDemoUrl());
        LoginPage loginPage = new LoginPage();

        loginPage.login("", "secret_sauce");

        Assert.assertTrue(
            loginPage.isErrorDisplayed(),
            "Error message not shown for empty username!"
        );
        System.out.println("✅ Error shown: " + loginPage.getErrorMessage());
    }

    // ── Test 4: Add to Cart after Login ─────────────────
    @Test(description = "Login and add product to cart")
    public void testAddToCart() {

        navigateTo(sauceDemoUrl());
        LoginPage loginPage = new LoginPage();
        loginPage.login("standard_user", "secret_sauce");

        ProductsPage productsPage = new ProductsPage();

        // Verify products loaded
        System.out.println("Total products: " + productsPage.getProductCount());
        Assert.assertTrue(productsPage.getProductCount() > 0, "No products found!");

        // Add first product to cart
        productsPage.addFirstProductToCart();

        // Verify cart count changed to 1
        Assert.assertEquals(productsPage.getCartCount(), "1",
            "Cart count should be 1 after adding product!");
        System.out.println("✅ Product added! Cart count: " + productsPage.getCartCount());
    }
}