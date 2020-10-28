package com.codecool.shop.model;

import com.google.gson.annotations.SerializedName;

public class Supplier extends BaseDescribedModel {

    @SerializedName(value="country")
    private final String country;

    public Supplier(String name, String description, String country) {
        super(name, description);
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

}
