// Package declaration for end-to-end tests
package com.ust.sdet.tests.e2e;

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
import static org.junit.jupiter.api.Assertions.assertTrue;

// End-to-end test covering login, product selection, cart, and order confirmation
@Epic("E-Commerce Application")
@Feature("Order Placement")
public class PlaceOrderE2ETest {

    // Logger for test lifecycle and step tracking
    private static final Logger log =
            LoggerUtil.getLogger(PlaceOrderE2ETest.class);

    // Shared WebDriver instance for the test flow
    private WebDriver driver;

    // Sets up the browser and opens the login page before each test
    @BeforeEach
    void setUp() {

        log.info("=================================================");
        log.info("[TEST] Starting End-to-End Test: PlaceOrderE2ETest");

        driver = DriverFactory.createDriver();

        driver.get(Config.loginUrl());

        log.info("[TEST] Login page opened");
    }

    // Closes the browser and cleans up after each test
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

        log.info("[TEST] End-to-End Test completed");
    }

    @Test
    @DisplayName("Place an order successfully")
    @Story("Customer purchases a product")
    @Description("Verifies that a customer can log in, search for a product, add it to the cart, complete checkout, and receive an order confirmation.")
    @Severity(SeverityLevel.CRITICAL)
    void catalogToConfirmedOrder() {

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
                        .sortBy("Price: Low to High")
                        .openFirstProduct()
                        .addToCart();

        TraceUtil.trace(driver, "Cart Page");

        log.info("[TEST] Verifying cart badge count");

        cart.header().cartBadge().expectCount(1);

        cart.header().openCart();

        log.info("[TEST] Verifying cart contents");

        assertEquals(
                1,
                cart.lineCount()
        );

        log.info("[TEST] Completing checkout");

        String message =
                cart.proceed()
                        .placeOrder()
                        .confirmationText();

        TraceUtil.trace(driver, "Order Confirmation");

        log.info("[TEST] Verifying order confirmation");

        assertTrue(
                message.toLowerCase().contains("confirmed")
        );

        log.info("[TEST] End-to-End order flow completed successfully");
    }
}