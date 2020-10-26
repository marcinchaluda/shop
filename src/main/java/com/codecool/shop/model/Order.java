package com.codecool.shop.model;

public class Order extends BaseModel {
    private boolean paid;
    private Cart cart;

    public Order(Cart cart) {
        this.cart = cart;
    }

    public boolean isPaid() {
        return paid;
    }

    public void markPaid() {
        this.paid = true;
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
