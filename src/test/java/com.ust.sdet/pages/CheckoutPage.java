// Package declaration for the checkout page object
package com.ust.sdet.pages;

// Imports required for logging and Selenium selectors
import com.ust.sdet.support.LoggerUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;

// Page object for the checkout page
public class CheckoutPage extends BasePage {

    // Logger used for checkout-related actions
    private static final Logger log = LoggerUtil.getLogger(CheckoutPage.class);

    // Locator for the Place Order button
    private static final By PLACE_ORDER =
            By.cssSelector("button.button.primary");

    // Locator for the confirmation panel shown after checkout
    private static final By CONFIRMATION_TEXT =
            By.cssSelector(".confirmation-panel");

    // Locator for the order total value
    private static final By TOTAL =
            By.cssSelector("[data-testid='checkout-total']");

    // Constructor that initializes the checkout page with the shared driver
    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    // Places the order by clicking the primary checkout button
    public CheckoutPage placeOrder() {

        log.info("[CHECKOUT] Placing order");

        click(PLACE_ORDER);

        log.info("[CHECKOUT] Order placed successfully");

        return this;
    }

    // Reads the confirmation text displayed after the order is placed
    public String confirmationText() {

        log.info("[CHECKOUT] Reading order confirmation");

        String confirmation = text(CONFIRMATION_TEXT);

        log.info("[CHECKOUT] Confirmation message: {}", confirmation);

        return confirmation;
    }

    // Reads the total amount shown on the checkout page
    public String getTotal() {

        log.info("[CHECKOUT] Reading order total");

        String total = text(TOTAL);

        log.info("[CHECKOUT] Order total: {}", total);

        return total;
    }
}