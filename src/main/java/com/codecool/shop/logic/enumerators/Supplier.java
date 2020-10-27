package com.codecool.shop.logic.enumerators;

import com.google.gson.annotations.SerializedName;

public enum Supplier {
    @SerializedName("Amazon")
    AMAZON("Amazon", "Supplier 1", "America"),

    @SerializedName("Lenovo")
    LENOVO("Lenovo", "Supplier 2", "China"),

    @SerializedName("Apple")
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
