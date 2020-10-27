package com.codecool.shop.logic.enumerators;

import com.google.gson.annotations.SerializedName;

public enum Category {
    @SerializedName("Phone")
    PHONE("Phone"),

    @SerializedName("Tablet")
    TABLET("Tablet"),

    @SerializedName("Laptop")
    LAPTOP("Laptop"),

    @SerializedName("PC")
    PERSONAL_COMPUTER("PC");

    private final String name;

    Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
