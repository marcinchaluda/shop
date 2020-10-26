package com.codecool.shop.logic;

import com.codecool.shop.model.Order;

import java.util.List;

public class OrderLogic implements BusinessLogic<Order> {
    @Override
    public void addToDatabase(Order order) {
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public void updateInDatabase(Order order) {
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public Order getFromDatabase(int id) {
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public List<Order> getAllFromDatabase() {
        throw new RuntimeException("Not implemented yet!");
    }
}
