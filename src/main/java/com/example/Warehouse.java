package com.example;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Singleton Warehouse, lagrar produkter per namn.
 */
public class Warehouse {

    private static final Map<String, Warehouse> INSTANCES = new HashMap<>();
    private final String name;
    private final List<Product> products = new ArrayList<>();
    private final Set<UUID> changedProducts = new HashSet<>();

    // Private constructor – singleton per name
    private Warehouse(String name) {
        this.name = name;
    }

    public static Warehouse getInstance(String name) {
        return INSTANCES.computeIfAbsent(name, Warehouse::new);
    }

    public String getName() {
        return name;
    }

    public void clearProducts() {
        products.clear();
        changedProducts.clear();
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }

    public void addProduct(Product product) {
        if (product == null) throw new IllegalArgumentException("Product cannot be null.");
        products.add(product);
    }

    public List<Product> getProducts() {
        return List.copyOf(products); // immutable list
    }

    public Optional<Product> getProductById(UUID id) {
        return products.stream()
                .filter(p -> p.uuid().equals(id))
                .findFirst();
    }

    public void remove(UUID id) {
        products.removeIf(p -> p.uuid().equals(id));
    }

    public void updateProductPrice(UUID id, BigDecimal newPrice) {
        Product product = getProductById(id)
                .orElseThrow(() -> new NoSuchElementException("Product not found with id: " + id));
        product.price(newPrice);
        changedProducts.add(id);
    }

    public List<Product> getChangedProducts() {
        return products.stream()
                .filter(p -> changedProducts.contains(p.uuid()))
                .collect(Collectors.toList());
    }

    public List<Perishable> expiredProducts() {
        return products.stream()
                .filter(p -> p instanceof Perishable per && per.isExpired())
                .map(p -> (Perishable) p)
                .collect(Collectors.toList());
    }

    public List<Shippable> shippableProducts() {
        return products.stream()
                .filter(p -> p instanceof Shippable)
                .map(p -> (Shippable) p)
                .collect(Collectors.toList());
    }

    public Map<Category, List<Product>> getProductsGroupedByCategories() {
        return products.stream()
                .collect(Collectors.groupingBy(Product::category));
    }
}
