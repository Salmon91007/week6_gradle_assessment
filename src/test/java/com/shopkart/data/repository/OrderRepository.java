// Package declaration for the order repository layer
package com.shopkart.data.repository;

// Imports for database access, the order model, and logging
import com.shopkart.data.db.DatabaseConfig;
import com.shopkart.data.model.Order;
import com.shopkart.support.LoggerUtil;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// Repository class responsible for CRUD-style order operations against the database
public class OrderRepository {

    // Logger for repository actions and errors
    private static final Logger log = LoggerUtil.getLogger(OrderRepository.class);

    // Finds an order by its SKU value
    public Order findBySku(String sku) {

        log.info("[REPOSITORY] Finding order with SKU: {}", sku);

        String sql = """
            SELECT sku, qty, price, order_date, shipped
            FROM orders
            WHERE sku = ?
            """;

        try (
                Connection connection = DatabaseConfig.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, sku);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {

                log.info("[REPOSITORY] Order found for SKU: {}", sku);

                return new Order(
                        rs.getString("sku"),
                        rs.getInt("qty"),
                        rs.getDouble("price"),
                        rs.getDate("order_date").toLocalDate(),
                        rs.getBoolean("shipped")
                );
            }

            log.warn("[REPOSITORY] No order found for SKU: {}", sku);

            return null;

        } catch (SQLException e) {

            log.error("[REPOSITORY] Failed to find order with SKU: {}", sku, e);

            throw new RuntimeException(e);
        }
    }

    // Counts the number of shipped orders in the database
    public long countShipped() {

        log.info("[REPOSITORY] Counting shipped orders");

        String sql = """
            SELECT COUNT(*)
            FROM orders
            WHERE shipped = true
            """;

        try (
                Connection connection = DatabaseConfig.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet rs = statement.executeQuery()
        ) {

            rs.next();

            long count = rs.getLong(1);

            log.info("[REPOSITORY] Shipped order count: {}", count);

            return count;

        } catch (SQLException e) {

            log.error("[REPOSITORY] Failed to count shipped orders", e);

            throw new RuntimeException(e);
        }
    }

    // Persists a new order into the database
    public void save(Order order) {

        log.info("[REPOSITORY] Saving order with SKU: {}", order.sku());

        String sql = """
                INSERT INTO orders
                (sku, qty, price, order_date, shipped)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (
                Connection connection = DatabaseConfig.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, order.sku());
            statement.setInt(2, order.qty());
            statement.setDouble(3, order.price());
            statement.setDate(4, Date.valueOf(order.orderDate()));
            statement.setBoolean(5, order.shipped());

            statement.executeUpdate();

            log.info("[REPOSITORY] Order saved successfully");

        } catch (SQLException e) {

            log.error("[REPOSITORY] Failed to save order with SKU: {}", order.sku(), e);

            throw new RuntimeException(e);
        }
    }

    // Counts all orders in the database
    public long count() {

        log.info("[REPOSITORY] Counting all orders");

        String sql = "SELECT COUNT(*) FROM orders";

        try (
                Connection connection = DatabaseConfig.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet rs = statement.executeQuery()
        ) {

            rs.next();

            long count = rs.getLong(1);

            log.info("[REPOSITORY] Total order count: {}", count);

            return count;

        } catch (SQLException e) {

            log.error("[REPOSITORY] Failed to count orders", e);

            throw new RuntimeException(e);
        }
    }

    // Resets the orders table for clean test execution
    public void reset() {

        log.info("[REPOSITORY] Resetting orders table");

        String sql = "TRUNCATE TABLE orders RESTART IDENTITY";

        try (
                Connection connection = DatabaseConfig.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.executeUpdate();

            log.info("[REPOSITORY] Orders table reset successfully");

        } catch (SQLException e) {

            log.error("[REPOSITORY] Failed to reset orders table", e);

            throw new RuntimeException(e);
        }
    }
}