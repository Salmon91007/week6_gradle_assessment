// Package declaration for repository integration tests
package com.ust.sdet.tests.integration;

// Imports for Allure reporting
import io.qameta.allure.*;

// Imports for the builder, factory, model, repository, logging, Flyway, and JUnit
import com.ust.sdet.builder.OrderBuilder;
import com.ust.sdet.factory.OrderFactory;
import com.ust.sdet.model.Order;
import com.ust.sdet.repository.OrderRepository;
import com.ust.sdet.support.LoggerUtil;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Integration tests for the order repository using a real database schema
@Epic("Order Management")
@Feature("Order Repository")
public class OrderRepositoryIntegrationTest {

    // Logger for integration test lifecycle and database events
    private static final Logger log =
            LoggerUtil.getLogger(OrderRepositoryIntegrationTest.class);

    // Shared repository and factory objects used by all tests
    private static final OrderRepository repo = new OrderRepository();
    private static final OrderFactory factory = new OrderFactory(repo);

    // Applies database migrations before the test class runs
    @BeforeAll
    static void migrateDatabase() {

        log.info("=================================================");
        log.info("[TEST] Starting Database Integration Tests");
        log.info("[DATABASE] Running Flyway database migrations");

        Flyway flyway = Flyway.configure()
                .dataSource(
                        System.getProperty("db.url", "jdbc:postgresql://localhost:5432/testdb"),
                        System.getProperty("db.user", "postgres"),
                        System.getProperty("db.password", "postgres")
                )
                .load();

        flyway.migrate();

        log.info("[DATABASE] Database migrations completed successfully");
    }

    // Clears the database before each test so they run independently
    @BeforeEach
    void setup() {

        log.info("[TEST] Resetting database before test");

        repo.reset();
    }

    // Verifies that a new order can be created and counted
    @Test
    @DisplayName("Create a new order")
    @Story("Persist order data")
    @Description("Verifies that a new order is successfully persisted into the PostgreSQL database.")
    @Severity(SeverityLevel.CRITICAL)
    void createsOrder() {

        log.info("[TEST] Running: createsOrder");

        factory.persisted(
                OrderBuilder.anOrder()
                        .withQty(3)
                        .build()
        );

        log.info("[TEST] Verifying total order count");

        assertEquals(1, repo.count());

        log.info("[TEST] createsOrder completed successfully");
    }

    // Verifies multiple orders can be inserted and counted
    @Test
    @DisplayName("Count persisted orders")
    @Story("Count stored orders")
    @Description("Verifies that multiple persisted orders are counted correctly.")
    @Severity(SeverityLevel.NORMAL)
    void countsOrders() {

        log.info("[TEST] Running: countsOrders");

        factory.persisted(
                OrderBuilder.anOrder()
                        .build()
        );

        factory.persisted(
                OrderBuilder.anOrder()
                        .build()
        );

        log.info("[TEST] Verifying total order count");

        assertEquals(2, repo.count());

        log.info("[TEST] countsOrders completed successfully");
    }

    // Verifies that an order can be retrieved by SKU
    @Test
    @DisplayName("Find order by SKU")
    @Story("Retrieve stored order")
    @Description("Verifies that an order can be retrieved correctly using its SKU.")
    @Severity(SeverityLevel.NORMAL)
    void findsOrderBySku() {

        log.info("[TEST] Running: findsOrderBySku");

        factory.persisted(
                OrderBuilder.anOrder()
                        .withSku("SKU-100")
                        .build()
        );

        Order order = repo.findBySku("SKU-100");

        log.info("[TEST] Verifying retrieved order");

        assertEquals("SKU-100", order.sku());
        assertEquals(1, order.qty());

        log.info("[TEST] findsOrderBySku completed successfully");
    }

    // Verifies shipped orders are counted separately from all orders
    @Test
    @DisplayName("Count shipped orders")
    @Story("Track shipped orders")
    @Description("Verifies that only shipped orders are included in the shipped order count.")
    @Severity(SeverityLevel.MINOR)
    void countsOnlyShippedOrders() {

        log.info("[TEST] Running: countsOnlyShippedOrders");

        factory.persisted(
                OrderBuilder.anOrder()
                        .withShipped(true)
                        .build()
        );

        factory.persisted(
                OrderBuilder.anOrder()
                        .withSku("SKU-2")
                        .build()
        );

        log.info("[TEST] Verifying order counts");

        assertEquals(2, repo.count());
        assertEquals(1, repo.countShipped());

        log.info("[TEST] countsOnlyShippedOrders completed successfully");
    }
}