// Package declaration for shared logging support
package com.ust.sdet.support;

// Imports for SLF4J logging support
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Utility class that provides consistent logger instances for tests and page objects
public final class LoggerUtil {

    // Prevent instantiation of the utility class
    private LoggerUtil() {
    }

    // Returns a logger for the provided class
    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }
}