// Package declaration for the cart page object
package com.shopkart.ui.pages;

// Imports required for logging and Selenium selectors
import com.shopkart.support.LoggerUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;

// Page object for the shopping cart page
public class CartPage extends BasePage {

    // Logger used for cart page actions
    private static final Logger log = LoggerUtil.getLogger(CartPage.class);

    // Locator for each cart row item
    private static final By CART_LINE =
            By.cssSelector(".cart-row");

    // Locator for the Proceed button
    private static final By PROCEED =
            By.cssSelector("button.button.primary");

    // Constructor that initializes the cart page with the shared driver
    public CartPage(WebDriver driver) {
        super(driver);
    }

    // Returns the number of cart line items currently displayed
    public int lineCount() {

        int count = elements(CART_LINE).size();

        log.info("Cart contains {} item(s)", count);

        return count;
    }

    // Proceeds to the checkout page by clicking the proceed button
    public CheckoutPage proceed() {

        log.info("Proceeding to Checkout");

        click(PROCEED);

        log.info("Navigated to Checkout page");

        return new CheckoutPage(driver);
    }
}