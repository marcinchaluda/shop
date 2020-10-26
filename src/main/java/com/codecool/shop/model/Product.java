package com.codecool.shop.model;

import com.codecool.shop.logic.enumerators.*;

public class Product extends BaseDescribedModel {

    private double defaultPrice;
    private String defaultCurrency;
    private Category productCategory;
    private Supplier supplier;

    public Product(String name, double defaultPrice, String currencyString, String description, Category productCategory, Supplier supplier) {
        super(name, description);
        this.defaultPrice = defaultPrice;
        this.defaultCurrency = currencyString;
        this.supplier = supplier;
        this.productCategory = productCategory;
    }

    public double getDefaultPrice() {
        return defaultPrice;
    }

    public void setDefaultPrice(double defaultPrice) {
        this.defaultPrice = defaultPrice;
    }

    public String getDefaultCurrency() {
        return defaultCurrency;
    }

    public void setDefaultCurrency(String defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }

    public String getPrice() {
        return String.valueOf(this.defaultPrice) + " " + this.defaultCurrency.toString();
    }

    public void setPrice(double price, String currency) {
        this.defaultPrice = price;
        this.defaultCurrency = currency;
    }

    public Category getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(Category productCategory) {
        this.productCategory = productCategory;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    @Override
    public String toString() {
        return String.format("id: %1$d, " +
                        "name: %2$s, " +
                        "defaultPrice: %3$f, " +
                        "defaultCurrency: %4$s, " +
                        "productCategory: %5$s, " +
                        "supplier: %6$s",
                this.id,
                this.name,
                this.defaultPrice,
                this.defaultCurrency,
                this.productCategory.getName(),
                this.supplier.getName()
        );
    }
}
