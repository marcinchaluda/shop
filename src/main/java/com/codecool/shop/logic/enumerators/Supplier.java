package com.codecool.shop.logic.enumerators;

public enum Supplier {
    AMAZON("Amazon", "Supplier 1", "America"),
    LENOVO("Lenovo", "Supplier 2", "China"),
    APPLE("Apple", "Supplier 3", "America");

    private final String name;
    private final String description;

    private final String country;

    Supplier(String name, String description, String country) {
        this.name = name;
        this.description = description;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCountry() {
        return country;
    }
}
