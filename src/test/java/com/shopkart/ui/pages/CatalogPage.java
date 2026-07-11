// Package declaration for the catalog page object
package com.shopkart.ui.pages;

// Imports for product card handling, configuration, logging, and Selenium types
import com.shopkart.ui.components.ProductCard;
import com.shopkart.support.Config;
import com.shopkart.support.LoggerUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;

import java.util.List;

// Page object for the product catalog page
public class CatalogPage extends BasePage {

    // Logger used for catalog interactions
    private static final Logger log = LoggerUtil.getLogger(CatalogPage.class);

    // Locator for the search input field
    private static final By SEARCH_INPUT =
            By.cssSelector("[data-test='search-input']");

    // Locator for each product card shown in the catalog
    private static final By PRODUCT_CARD =
            By.cssSelector("[data-test='product-card']");

    // Locator for the results count banner
    private static final By RESULT_COUNT =
            By.cssSelector("[data-test='catalog-result-count']");

    // Locator for the sort drop-down control
    private static final By SORT_SELECT =
            By.cssSelector("[data-test='sort-select']");

    // Locator for the link inside each product card
    private static final By PRODUCT_LINK =
            By.cssSelector("[data-test='product-card'] a");

    // Constructor that initializes the catalog page with the shared driver
    public CatalogPage(WebDriver driver) {
        super(driver);
    }

    // Opens the catalog page using the configured catalog URL
    public CatalogPage open() {

        log.info("Opening Catalog page");

        driver.get(Config.catalogUrl());

        return this;
    }

    // Searches for a product and waits for the expected result count
    public CatalogPage search(String text, String expectedCount) {

        log.info("Searching for product: {}", text);

        WebElement searchInput = visible(SEARCH_INPUT);

        // Clears any previous value and submits the new search text
        searchInput.clear();
        searchInput.sendKeys(text);
        searchInput.sendKeys(Keys.ENTER);

        wait.until(
                ExpectedConditions.textToBe(
                        RESULT_COUNT,
                        expectedCount
                )
        );

        log.info("Search completed. Expected result: {}", expectedCount);

        return this;
    }

    // Searches for a product and waits until product cards are displayed
    public CatalogPage searchFor(String text) {

        log.info("Searching for product: {}", text);

        WebElement searchInput = visible(SEARCH_INPUT);

        // Clears the field and submits the search term
        searchInput.clear();
        searchInput.sendKeys(text);
        searchInput.sendKeys(Keys.ENTER);

        visible(PRODUCT_CARD);

        log.info("Products displayed successfully");

        return this;
    }

    // Opens the first product card from the catalog
    public ProductPage openFirstProduct() {

        log.info("Opening first product from catalog");

        visible(PRODUCT_LINK);

        elements(PRODUCT_LINK)
                .getFirst()
                .click();

        return new ProductPage(driver);
    }

    // Collects all product cards shown in the catalog
    public List<ProductCard> cards() {

        log.info("Loading all product cards");

        return driver.findElements(PRODUCT_CARD)
                .stream()
                .map(ProductCard::new)
                .toList();
    }

    // Reads all product titles from the card list
    public List<String> titles() {

        log.info("Reading product titles");

        return cards()
                .stream()
                .map(ProductCard::title)
                .toList();
    }

    // Reads all product prices from the card list
    public List<Integer> prices() {

        log.info("Reading product prices");

        return cards()
                .stream()
                .map(ProductCard::price)
                .toList();
    }

    // Sorts the catalog results by the provided visible label
    public CatalogPage sortBy(String label) {

        log.info("Sorting products by '{}'", label);

        new Select(visible(SORT_SELECT))
                .selectByVisibleText(label);

        visibleElements(PRODUCT_CARD);

        log.info("Products sorted successfully");

        return this;
    }

    // Verifies that the catalog page is displayed
    public boolean isDisplayed() {

        log.info("Verifying Catalog page is displayed");

        return visible(SEARCH_INPUT).isDisplayed();
    }
}