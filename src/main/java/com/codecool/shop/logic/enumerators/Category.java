package com.codecool.shop.logic.enumerators;

public enum Category {
    PHONE("Phone"),
    TABLET("Tablet"),
    LAPTOP("Laptop"),
    PERSONAL_COMPUTER("PC");

    private final String name;

    Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Category getFromValue(String value){
        return Category.valueOf(value.toUpperCase());
    }
}
