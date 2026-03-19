package com.automationlearn.pages;

import java.util.List;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.automationlearn.base.DriverManager;

public class ProductsPage {

    @FindBy(css = ".title")
    private WebElement pageTitle;

    @FindBy(css = ".inventory_item")
    private List<WebElement> productList;

    @FindBy(css = ".shopping_cart_link")
    private WebElement cartIcon;

    @FindBy(css = ".shopping_cart_badge")
    private WebElement cartBadge;

    // Add to cart button for first product
    @FindBy(css = ".inventory_item button")
    private List<WebElement> addToCartButtons;

    public ProductsPage() {
        PageFactory.initElements(DriverManager.getDriver(), this);
    }

    // Check if we landed on products page
    public boolean isProductsPageDisplayed() {
        try {
            return pageTitle.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getPageTitle() {
        return pageTitle.getText();
    }

    // How many products are showing
    public int getProductCount() {
        return productList.size();
    }

    // Add first product to cart
    public void addFirstProductToCart() {
        addToCartButtons.get(0).click();
    }

    // Add product by index (0 = first, 1 = second...)
    public void addProductToCart(int index) {
        addToCartButtons.get(index).click();
    }

    public void clickCart() {
        cartIcon.click();
    }

    // Get cart count number shown on cart icon
    public String getCartCount() {
        try {
            return cartBadge.getText();
        } catch (Exception e) {
            return "0";
        }
    }
}