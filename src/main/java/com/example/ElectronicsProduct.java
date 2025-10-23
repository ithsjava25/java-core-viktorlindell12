package com.example;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

/**
 * ElectronicsProduct är en Product som är shippable.
 */
public class ElectronicsProduct extends Product implements Shippable {

    private final int warrantyMonths;
    private final BigDecimal weight; // i kg

    public ElectronicsProduct(UUID id, String name, Category category, BigDecimal price,
                              int warrantyMonths, BigDecimal weight) {
        super(id, name, category, price);

        if (warrantyMonths < 0)
            throw new IllegalArgumentException("Warranty months cannot be negative.");
        if (weight.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("Weight cannot be negative.");

        this.warrantyMonths = warrantyMonths;
        this.weight = Objects.requireNonNull(weight, "Weight cannot be null");
    }

    @Override
    public String productDetails() {
        return String.format("Electronics: %s, Warranty: %d months", name(), warrantyMonths);
    }

    // --- Shippable ---
    @Override
    public BigDecimal calculateShippingCost() {
        BigDecimal cost = BigDecimal.valueOf(79);
        if (weight.doubleValue() > 5.0) {
            cost = cost.add(BigDecimal.valueOf(49));
        }
        return cost;
    }

    @Override
    public Double weight() {
        return weight.doubleValue();
    }

    public int getWarrantyMonths() {
        return warrantyMonths;
    }
}

