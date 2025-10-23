package com.example;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Objects;

public final class Category {

    private static final Map<String, Category> CACHE = new ConcurrentHashMap<>();
    private final String name;

    private Category(String name) {
        this.name = name;
    }

    public static Category of(String name) {
        if (name == null)
            throw new IllegalArgumentException("Category name can't be null");
        String trimmed = name.trim();
        if (trimmed.isEmpty())
            throw new IllegalArgumentException("Category name can't be blank");

        // Sort so the first letter are big
        String normalized = trimmed.substring(0, 1).toUpperCase() + trimmed.substring(1).toLowerCase();

        // Flyweight-cache
        return CACHE.computeIfAbsent(normalized, Category::new);
    }

    // Method to find "getName()"
    public String getName() {
        return name;
    }
    public String name() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;
        Category category = (Category) o;
        return name.equals(category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }
}

