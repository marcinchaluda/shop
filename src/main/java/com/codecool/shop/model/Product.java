package com.codecool.shop.model;

import com.codecool.shop.logic.enumerators.*;
import com.google.gson.annotations.SerializedName;

import java.text.DecimalFormat;

public class Product extends BaseDescribedModel {

    @SerializedName(value="unitPrice", alternate="defaultPrice")
    private double defaultPrice;

    @SerializedName(value="currency", alternate="defaultCurrency")
    private String defaultCurrency;

    @SerializedName(value="category", alternate="productCategory")
    private Category productCategory;

    @SerializedName(value="supplier")
    private Supplier supplier;

    @SerializedName(value="image", alternate="imageSource")
    private String imageSource;

    transient private final DecimalFormat df = new DecimalFormat("#.##");

    public Product(String name, String description, double defaultPrice, String currencyString, Category productCategory, Supplier supplier, String imageSource) {
        super(name, description);
        this.defaultPrice = defaultPrice;
        this.defaultCurrency = currencyString;
        this.supplier = supplier;
        this.productCategory = productCategory;
        this.imageSource = imageSource;
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
        return String.valueOf(df.format(this.defaultPrice)) + " " + this.defaultCurrency;
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
                        "getDescription: %3$s, " +
                        "defaultPrice: %4$f, " +
                        "defaultCurrency: %5$s, " +
                        "productCategory: %6$s, " +
                        "supplier: %7$s, " +
                        "imageSource: %8$s",
                this.id,
                this.name,
                this.getDescription(),
                this.defaultPrice,
                this.defaultCurrency,
                this.productCategory.getName(),
                this.supplier.getName(),
                this.imageSource
        );
    }

    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }
}
