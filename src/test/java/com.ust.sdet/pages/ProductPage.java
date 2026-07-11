// Package declaration for the page object classes
package com.ust.sdet.pages;

// Imports required for logging and Selenium WebDriver support
import com.ust.sdet.support.LoggerUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;

// Page object representing the product details page
public class ProductPage extends BasePage {

    // Logger used to track product page actions
    private static final Logger log = LoggerUtil.getLogger(ProductPage.class);

    // Locator for the product title displayed on the page
    private static final By PRODUCT_TITLE =
            By.cssSelector("[data-test='detail-name']");

    // Locator for the Add to Cart button
    private static final By ADD_TO_CART =
            By.cssSelector("[data-test='add-to-cart']");

    // Constructor that initializes the page with the shared WebDriver instance
    public ProductPage(WebDriver driver) {
        super(driver);
    }

    // Returns the visible product title text from the page
    public String title() {

        // Logs the action before reading the title
        log.info("Reading product title");

        // Retrieves the product title using the page object helper
        return text(PRODUCT_TITLE);
    }

    // Clicks the Add to Cart button and returns the cart page
    public CartPage addToCart() {

        // Logs the action before adding the product to the cart
        log.info("Adding product to cart");

        // Clicks the Add to Cart button
        click(ADD_TO_CART);

        // Logs the success message after the product is added
        log.info("Product added to cart successfully");

        // Returns a new CartPage instance after the action completes
        return new CartPage(driver);
    }
}