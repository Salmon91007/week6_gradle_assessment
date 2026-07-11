// Package declaration for the order factory helper
package com.shopkart.data.factory;

// Imports for the order model and repository dependency
import com.shopkart.data.model.Order;
import com.shopkart.data.repository.OrderRepository;

// Helper class that persists an order through the repository layer
public class OrderFactory {

    // Repository instance used to save orders
    private final OrderRepository repository;

    // Constructor that injects the repository dependency
    public OrderFactory(OrderRepository repo) {
        this.repository = repo;
    }

    // Saves the provided order and returns it for further use
    public Order persisted(Order order) {
        repository.save(order);
        return order;
    }
}
