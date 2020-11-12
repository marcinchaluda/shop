package com.codecool.shop.model;

public class Category extends BaseDescribedModel {

    public Category(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
