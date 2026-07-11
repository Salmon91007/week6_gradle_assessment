// Package declaration for the order model used in tests and repositories
package com.shopkart.data.model;

import java.time.LocalDate;

// Immutable data record representing a customer order
public record Order(
        // Unique stock keeping unit for the ordered item
        String sku,

        // Quantity of the item ordered
        int qty,

        // Price of the order item
        double price,

        // Date when the order was placed
        LocalDate orderDate,

        // Indicates whether the order has been shipped
        boolean shipped

) {}
