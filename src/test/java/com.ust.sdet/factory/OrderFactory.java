// Package declaration for the order factory helper
package com.ust.sdet.factory;

// Imports for the order model and repository dependency
import com.ust.sdet.model.Order;
import com.ust.sdet.repository.OrderRepository;

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
