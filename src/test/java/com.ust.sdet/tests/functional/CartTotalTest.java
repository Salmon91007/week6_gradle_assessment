// Package declaration for functional UI tests
package com.ust.sdet.tests.functional;

// Imports for Allure reporting
import io.qameta.allure.*;

// Imports for page objects, configuration, driver setup, logging, and JUnit assertions
import com.ust.sdet.pages.*;
import com.ust.sdet.support.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Functional test that validates the cart total display for a selected product
@Epic("E-Commerce Application")
@Feature("Shopping Cart")
public class CartTotalTest {

    // Logger for functional test lifecycle events
    private static final Logger log =
            LoggerUtil.getLogger(CartTotalTest.class);

    // Shared WebDriver instance for the test flow
    private WebDriver driver;

    // Sets up the browser and opens the login page before each test
    @BeforeEach
    void setUp() {

        log.info("=================================================");
        log.info("[TEST] Starting Functional Test: CartTotalTest");

        driver = DriverFactory.createDriver();

        driver.get(Config.loginUrl());

        log.info("[TEST] Login page opened");
    }

    // Closes the browser after each test
    @AfterEach
    void tearDown() {

        if (driver != null) {

            ScreenshotUtil.capture(
                    driver,
                    getClass().getSimpleName()
            );

            log.info("[TEST] Closing browser");

            driver.quit();
        }

        log.info("[TEST] Functional Test completed");
        log.info("=================================================");
    }

    @Test
    @DisplayName("Verify cart total after adding a product")
    @Story("Customer views cart total")
    @Description("Verifies that the cart displays the correct total amount after a customer adds a product to the shopping cart.")
    @Severity(SeverityLevel.NORMAL)
    void verifyCartTotal() {

        log.info("[TEST] Logging in as customer");

        LoginPage login = new LoginPage(driver);

        HomePage home = login
                .email("customer@example.com")
                .password("Password@123")
                .signIn();

        TraceUtil.trace(driver, "Customer Logged In");

        log.info("[TEST] Verifying signed-in user");

        assertEquals(
                "Customer User",
                home.header().userName()
        );

        log.info("[TEST] Verifying Home page heading");

        home.isHeadingVisible();

        String heading = home.headingText();

        assertEquals(
                "Welcome, Customer User",
                heading
        );

        log.info("[TEST] Navigating to Products");

        home.header().openProducts();

        TraceUtil.trace(driver, "Products Page");

        CatalogPage catalog = new CatalogPage(driver);

        log.info("[TEST] Searching and selecting product");

        CartPage cart =
                catalog.search("headphones", "Showing 1 product")
                        .openFirstProduct()
                        .addToCart();

        TraceUtil.trace(driver, "Cart Page");

        log.info("[TEST] Verifying cart badge");

        cart.header().cartBadge().expectCount(1);

        cart.header().openCart();

        log.info("[TEST] Verifying cart item count");

        assertEquals(
                1,
                cart.lineCount()
        );

        log.info("[TEST] Reading cart total");

        String total =
                cart.proceed()
                        .getTotal();

        log.info("[TEST] Verifying cart total");

        assertEquals(
                "Rs. 8,198",
                total
        );

        log.info("[TEST] Cart total validated successfully");
    }
}