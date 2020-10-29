package com.codecool.shop.model;

import com.google.gson.annotations.SerializedName;

public class ProductInCart extends BaseDescribedModel {

    @SerializedName(value="product")
    private Product product;

    @SerializedName(value="quantity")
    private int quantity;

    public ProductInCart(String name, Product product, int quantity) {
        super(name);
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
