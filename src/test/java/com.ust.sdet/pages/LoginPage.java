// Package declaration for the login page object
package com.ust.sdet.pages;

// Imports for configuration, logging, and Selenium support
import com.ust.sdet.support.Config;
import com.ust.sdet.support.LoggerUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;

// Page object for the login page
public class LoginPage extends BasePage {

    // Logger used for login page actions
    private static final Logger log = LoggerUtil.getLogger(LoginPage.class);

    // Locator for the email field
    private static final By EMAIL =
            By.id("email");

    // Locator for the password field
    private static final By PASSWORD =
            By.id("password");

    // Locator for the sign-in button
    private static final By SIGNIN =
            By.cssSelector(".form-submit");

    // Locator for the login error message
    private static final By LOGIN_ERROR =
            By.cssSelector("[data-testid='login-error']");

    // Constructor that initializes the login page with the shared driver
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // Opens the login page using the configured login URL
    public LoginPage open() {

        log.info("Opening Login Page");

        driver.get(Config.loginUrl());

        return this;
    }

    // Reads and returns the current browser page title
    public String title() {

        log.info("Reading Login Page title");

        return driver.getTitle();
    }

    // Enters the provided email address into the email field
    public LoginPage email(String email) {

        log.info("Entering email: {}", email);

        type(EMAIL, email);

        return this;
    }

    // Enters the provided password into the password field
    public LoginPage password(String password) {

        log.info("Entering password");

        type(PASSWORD, password);

        return this;
    }

    // Signs in by clicking the submit button and returning the home page
    public HomePage signIn() {

        log.info("Signing in");

        visible(SIGNIN);

        click(SIGNIN);

        log.info("Login submitted successfully");

        return new HomePage(driver);
    }

    // Reads the login error message shown after a failed sign-in attempt
    public String errorMessage() {

        log.info("Reading login error message");

        return text(LOGIN_ERROR);
    }
}