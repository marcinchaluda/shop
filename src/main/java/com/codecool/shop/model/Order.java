package com.codecool.shop.model;

import com.google.gson.annotations.SerializedName;

public class Order extends BaseModel {

    @SerializedName(value="status", alternate="paid")
    private boolean paid;

    @SerializedName(value="cart")
    private Cart cart;

    public Order(Cart cart) {
        this.cart = cart;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean status) {
        this.paid = status;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @Override
    public String toString() {
        return String.format("id: %1$d, " +
                        "paid status: %2$b",
                this.id,
                this.paid);
    }
}
