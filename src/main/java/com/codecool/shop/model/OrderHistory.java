package com.codecool.shop.model;

import java.util.List;

public class OrderHistory {
    private User user;
    private List<Order> orderList;

    public OrderHistory(User user, List<Order> orderList) {
        this.user = user;
        this.orderList = orderList;
    }

    public User getUser() {
        return user;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    @Override
    public String toString() {
        return String.format("user: %1$s, " +
                        "amount of orders: %2$d, ",
                this.user.getName(),
                this.orderList.size()
        );
    }
}
