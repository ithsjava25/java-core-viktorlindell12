package com.example;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * An abstract base class for all types of products.
 * Provides shared fields, validation, and basic accessors.
 */
public abstract class Product {

    private final UUID id;
    private final String name;
    private final Category category;
    private BigDecimal price;


     // Constructor used by subclasses.

    protected Product(UUID id, String name, Category category, BigDecimal price) {
        if (id == null)
            throw new IllegalArgumentException("ID cannot be null.");
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Product name cannot be blank.");
        if (category == null)
            throw new IllegalArgumentException("Category cannot be null.");
        if (price == null)
            throw new IllegalArgumentException("Price cannot be null.");

        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    // Getters
    public UUID uuid() {
        return id;
    }

    public String name() {
        return name;
    }

    public Category category() {
        return category;
    }

    public BigDecimal price() {
        return price;
    }

    // Setter for price
    public void price(BigDecimal newPrice) {
        if (newPrice == null)
            throw new IllegalArgumentException("Price cannot be null.");
        this.price = newPrice;
    }

    // Abstract method implemented by subclasses
    public abstract String productDetails();

    // Common toString for debugging
    @Override
    public String toString() {
        return "%s (%s) - %s kr".formatted(name, category.getName(), price);
    }
}

