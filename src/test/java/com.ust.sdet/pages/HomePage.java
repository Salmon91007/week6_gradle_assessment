// Package declaration for the home page object
package com.ust.sdet.pages;

// Imports required for logging and Selenium locator support
import com.ust.sdet.support.LoggerUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;

// Page object for the home page
public class HomePage extends BasePage {

    // Logger used for home page interactions
    private static final Logger log = LoggerUtil.getLogger(HomePage.class);

    // Locator for the main page heading
    private static final By HEADING = By.id("page-title");

    // Locator for the catalog link on the home page
    private static final By CATALOG = By.cssSelector("a[href='/catalog']");

    // Constructor that initializes the home page with the shared driver
    public HomePage(WebDriver driver) {
        super(driver);
    }

    // Verifies that the home page heading is visible
    public boolean isHeadingVisible() {

        log.info("Verifying Home page heading is visible");

        return visible(HEADING).isDisplayed();
    }

    // Reads and returns the visible home page heading text
    public String headingText() {

        log.info("Reading Home page heading");

        return text(HEADING);
    }

}