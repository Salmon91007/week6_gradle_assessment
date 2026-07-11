// Package declaration for database configuration helpers
package com.shopkart.data.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Utility class for creating database connections for repository tests
public final class DatabaseConfig {

    // Prevent instantiation of this utility class
    private DatabaseConfig() {}

    // Opens a JDBC connection using configurable database properties
    public static Connection getConnection() throws SQLException {

        // Database URL, defaulting to a local PostgreSQL instance
        String url = System.getProperty(
                "db.url",
                "jdbc:postgresql://localhost:5432/testdb"
        );

        // Database username, defaulting to postgres
        String user = System.getProperty(
                "db.user",
                "postgres"
        );

        // Database password, defaulting to postgres
        String password = System.getProperty(
                "db.password",
                "postgres"
        );

        return DriverManager.getConnection(url, user, password);
    }
}