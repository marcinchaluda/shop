package com.codecool.shop.logic.enumerators;

public enum SortType {
    ALL("all"),
    CATEGORY("category"),
    SUPPLIER("supplier");

    private final String name;

    SortType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
