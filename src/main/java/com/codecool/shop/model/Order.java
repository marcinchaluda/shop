package com.codecool.shop.model;

public class Order extends BaseModel {
    private boolean paid;
    private Cart cart;

    public boolean isPaid() {
        return paid;
    }

    public void markPaid() {
        this.paid = true;
    }

    @Override
    public String toString() {
        return String.format("id: %1$d, " +
                        "paid status: %2$b",
                this.id,
                this.paid);
    }
}
