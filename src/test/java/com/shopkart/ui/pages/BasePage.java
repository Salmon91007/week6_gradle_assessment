// Package declaration for the shared page object base classes
package com.shopkart.ui.pages;

// Imports used by the common Selenium page object helpers
import com.shopkart.ui.components.Header;
import com.shopkart.support.LoggerUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

// Base class that provides common web page actions for all page objects
public abstract class BasePage {

    // Shared driver and wait instance used by child page objects
    protected final WebDriver driver;
    protected final WebDriverWait wait;

    // Logger for tracking page-level actions
    private static final Logger log = LoggerUtil.getLogger(BasePage.class);

    // Initializes the driver and explicit wait for the page object
    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Returns the shared header component for the current page
    public Header header() {
        return new Header(driver);
    }

    // Waits until the target element is visible and returns it
    protected WebElement visible(By by) {

        log.info("Waiting for element to become visible: {}", by);

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(by)
        );
    }

    // Waits until a list of elements becomes visible and returns them
    protected List<WebElement> visibleElements(By by) {

        log.info("Waiting for elements to become visible: {}", by);

        return wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(by)
        );
    }

    // Finds elements immediately without waiting for visibility
    protected List<WebElement> elements(By by) {

        log.info("Locating elements: {}", by);

        return driver.findElements(by);
    }

    // Clicks an element after waiting for it to become clickable
    protected void click(By by) {

        log.info("Clicking element: {}", by);

        wait.until(
                ExpectedConditions.elementToBeClickable(by)
        ).click();
    }

    // Types text into a field after waiting for it to be visible
    protected void type(By by, CharSequence... text) {

        log.info("Typing '{}' into element: {}",
                Arrays.toString(text), by);

        WebElement element = visible(by);

        // Clears the existing field value before entering new text
        element.clear();
        element.sendKeys(text);
    }

    // Reads visible text from an element on the page
    protected String text(By by) {

        log.info("Reading text from element: {}", by);

        return visible(by).getText();
    }
}