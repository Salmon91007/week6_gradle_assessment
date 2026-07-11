// Package declaration for Selenium driver creation helpers
package com.ust.sdet.support;

// Imports for Selenium browser drivers and logging
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;

import java.util.Map;

// Factory class for creating the appropriate WebDriver instance based on configuration
public final class DriverFactory {

    // Logger for browser creation events
    private static final Logger log = LoggerUtil.getLogger(DriverFactory.class);

    // Prevent instantiation of the factory class
    private DriverFactory() {
    }

    // Creates and returns a Selenium WebDriver instance for the configured browser
    public static WebDriver createDriver() {

        log.info("Creating WebDriver instance");
        log.info("Browser: {}", Config.browser());
        log.info("Headless Mode: {}", Config.headless());

        switch (Config.browser().toLowerCase()) {

            case "chrome":

                log.info("Launching Chrome browser");

                ChromeOptions options = new ChromeOptions();

                if (Config.headless()) {
                    options.addArguments("--headless=new");
                }

                options.addArguments("--window-size=1440,900");
                options.setExperimentalOption(
                        "prefs",
                        Map.of(
                                "credentials_enable_service", false,
                                "profile.password_manager_enabled", false
                        )
                );

                options.addArguments("--disable-save-password-bubble");
                options.addArguments("--incognito");

                WebDriver chromeDriver = new ChromeDriver(options);

                log.info("Chrome browser launched successfully");

                return chromeDriver;

            case "firefox":

                log.info("Launching Firefox browser");

                FirefoxOptions firefoxOptions = new FirefoxOptions();

                if (Config.headless()) {
                    firefoxOptions.addArguments("-headless");
                }

                WebDriver firefoxDriver = new FirefoxDriver(firefoxOptions);

                log.info("Firefox browser launched successfully");

                return firefoxDriver;

            default:

                log.error("Unsupported browser configured: {}", Config.browser());

                throw new IllegalArgumentException(
                        "Unsupported browser: " + Config.browser()
                );
        }
    }
}