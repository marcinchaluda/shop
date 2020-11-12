package com.codecool.shop.model;

import com.google.gson.annotations.SerializedName;

public class Order extends BaseModel {

    @SerializedName(value="paid")
    private boolean paid;

    @SerializedName(value="cart")
    private Cart cart;

    private int cart_id;

    public Order(Cart cart) {
        this.cart = cart;
        this.cart_id = cart.getId();
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

    public int getCart_id() {
        if (cart_id == 0) {
            return cart.getId();
        }
        return cart_id;
    }

    public void setCart_id(int cart_id) {
        this.cart_id = cart_id;
    }

    @Override
    public String toString() {
        return String.format("id: %1$d, " +
                        "paid status: %2$b, " +
                        "cart id: %3$d",
                this.id,
                this.paid,
                this.cart_id);
    }
}
