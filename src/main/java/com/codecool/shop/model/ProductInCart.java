package com.codecool.shop.model;

import com.google.gson.annotations.SerializedName;

public class ProductInCart extends BaseDescribedModel {

    @SerializedName(value="product")
    private Product product;

    @SerializedName(value="quantity")
    private int quantity;

    private int productId;

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

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return String.format("id: %1$d, " +
                        "productId: %2$s, " +
                        "quantity: %3$s, ",
                this.id,
                this.productId,
                this.quantity
        );
    }
}
