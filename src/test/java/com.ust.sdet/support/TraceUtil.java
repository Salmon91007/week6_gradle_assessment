package com.ust.sdet.support;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class TraceUtil {

    private static final Logger log =
            LoggerUtil.getLogger(TraceUtil.class);

    private TraceUtil() {
    }

    public static void trace(WebDriver driver, String step) {

        log.info("==============================================");
        log.info("[TRACE] Step       : {}", step);
        log.info("[TRACE] URL        : {}", driver.getCurrentUrl());
        log.info("[TRACE] Page Title : {}", driver.getTitle());
        log.info("[TRACE] Browser    : {}", Config.browser());
        log.info(
                "[TRACE] Time       : {}",
                LocalDateTime.now().format(
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                )
        );
        log.info("==============================================");
    }
}