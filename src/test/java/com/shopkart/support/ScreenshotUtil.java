package com.shopkart.support;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public final class ScreenshotUtil {

    private static final Logger log =
            LoggerUtil.getLogger(ScreenshotUtil.class);

    private ScreenshotUtil() {
    }

    public static void capture(WebDriver driver, String testName) {

        try {

            Path screenshotDirectory =
                    Path.of("build", "evidence", "screenshots");

            Files.createDirectories(screenshotDirectory);

            File source =
                    ((TakesScreenshot) driver)
                            .getScreenshotAs(OutputType.FILE);

            Path destination =
                    screenshotDirectory.resolve(
                            testName + "_" + System.currentTimeMillis() + ".png"
                    );

            Files.copy(
                    source.toPath(),
                    destination,
                    StandardCopyOption.REPLACE_EXISTING
            );

            log.info("[EVIDENCE] Screenshot saved: {}", destination);

            try (InputStream inputStream =
                         Files.newInputStream(destination)) {

                Allure.addAttachment(
                        testName + " Screenshot",
                        "image/png",
                        inputStream,
                        ".png"
                );

                log.info("[ARTIFACT] Screenshot attached to Allure report");

            }

        } catch (IOException e) {

            log.error(
                    "[EVIDENCE] Unable to capture screenshot",
                    e
            );
        }
    }
}