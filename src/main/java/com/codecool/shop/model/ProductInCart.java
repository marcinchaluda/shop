package com.codecool.shop.model;

public class ProductInCart extends BaseModel {

    private int productId;
    private int quantity;

    public ProductInCart(int productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
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
