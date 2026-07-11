package com.shopkart.ui.components;

import com.shopkart.pages.*;
import com.shopkart.ui.pages.*;
import com.ust.sdet.pages.*;
import com.shopkart.support.LoggerUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;

public class Header extends BasePage {

    private static final Logger log = LoggerUtil.getLogger(Header.class);

    private static final By HOME = By.linkText("Home");
    private static final By PROFILE = By.linkText("Profile");
    private static final By PRODUCTS = By.linkText("Products");
    private static final By CART = By.cssSelector("[data-test='cart-icon']");
    private static final By CHECKOUT = By.linkText("Checkout");
    private static final By ORDERS = By.linkText("Orders");
    private static final By USER_NAME = By.cssSelector("[aria-label='Signed in user'] span");

    public Header(WebDriver driver) {
        super(driver);
    }

    public CartBadge cartBadge() {

        log.info("Accessing cart badge");

        return new CartBadge(wait);
    }

    public CartPage openCart() {

        log.info("Opening Cart page");

        click(CART);

        return new CartPage(driver);
    }

    public HomePage openHome() {

        log.info("Navigating to Home page");

        click(HOME);

        return new HomePage(driver);
    }

    public void openProfile() {

        log.info("Opening Profile page");

        click(PROFILE);
    }

    public CatalogPage openProducts() {

        log.info("Opening Products page");

        click(PRODUCTS);

        return new CatalogPage(driver);
    }

    public void openOrders() {

        log.info("Opening Orders page");

        click(ORDERS);
    }

    public CheckoutPage checkoutPage() {

        log.info("Navigating to Checkout page");

        click(CHECKOUT);

        return new CheckoutPage(driver);
    }

    public String userName() {

        log.info("Reading signed-in user name");

        return text(USER_NAME);
    }
}