// Package declaration for the order builder helper
package com.ust.sdet.builder;

// Imports for the order model and date support
import com.ust.sdet.model.Order;

import java.time.LocalDate;

// Builder class for creating Order test objects with default or custom values
public class OrderBuilder {

    // Default values for a new order
    private String sku = "SKU-1";
    private int qty = 1;
    private double price = 1299.00;
    private LocalDate orderDate = LocalDate.now();
    private boolean shipped = false;

    // Starts a new builder instance
    public static OrderBuilder anOrder() {
        return new OrderBuilder();
    }

    // Sets a custom SKU value
    public OrderBuilder withSku(String sku) {
        this.sku = sku;
        return this;
    }

    // Sets a custom quantity value
    public OrderBuilder withQty(int qty) {
        this.qty = qty;
        return this;
    }

    // Sets a custom price value
    public OrderBuilder withPrice(double price) {
        this.price = price;
        return this;
    }

    // Sets the shipped status
    public OrderBuilder withShipped(boolean shipped) {
        this.shipped = shipped;
        return this;
    }

    // Builds an Order object from the current builder state
    public Order build() {
        return new Order(
                sku,
                qty,
                price,
                orderDate,
                shipped
        );
    }
}