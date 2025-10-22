package com.example;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

/**
 * FoodProduct is a Product that is perishable and shippable.
 */
public class FoodProduct extends Product implements Perishable, Shippable {

    private final LocalDate expirationDate;
    private final BigDecimal weight; // i kg

    public FoodProduct(UUID id, String name, Category category, BigDecimal price,
                       LocalDate expirationDate, BigDecimal weight) {
        super(id, name, category, price);

        if (price.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("Price cannot be negative.");
        if (weight.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("Weight cannot be negative.");

        this.expirationDate = Objects.requireNonNull(expirationDate, "Expiration date cannot be null");
        this.weight = Objects.requireNonNull(weight, "Weight cannot be null");
    }

    @Override
    public String productDetails() {
        return String.format("Food: %s, Expires: %s", name(), expirationDate);
    }

    // --- Perishable ---
    @Override
    public LocalDate expirationDate() {
        return expirationDate;
    }

    @Override
    public boolean isExpired() {
        return expirationDate.isBefore(LocalDate.now());
    }

    // --- Shippable ---
    @Override
    public BigDecimal calculateShippingCost() {
        return weight.multiply(BigDecimal.valueOf(50)); // 50 kr/kg
    }

    @Override
    public Double weight() {
        return weight.doubleValue();
    }
}
