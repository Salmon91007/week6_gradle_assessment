// Package declaration for configuration helpers used by tests
package com.ust.sdet.support;

// Utility class that reads runtime configuration values for browser-based tests
public final class Config {

    // Prevent instantiation of the configuration helper
    private Config() {
    }

    // Returns the base application URL, trimming any trailing slash
    public static String baseUrl() {
        return TestEnvironment
                .optional("BASE_URL", "http://localhost:5173")
                .replaceAll("/$", "");
    }

    // Returns the login page URL
    public static String loginUrl() {
        return baseUrl() + "/login";
    }

    // Returns the catalog page URL
    public static String catalogUrl() {
        return baseUrl() + "/catalog";
    }

    // Returns whether browser tests should run in headless mode
    public static boolean headless() {
        return Boolean.parseBoolean(
                TestEnvironment.optional("HEADLESS", "false")
        );
    }

    // Returns the configured browser name for the test run
    public static String browser(){
        return TestEnvironment.optional("BROWSER", "chrome");
    }
}