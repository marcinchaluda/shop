package com.codecool.shop.logic;

import com.codecool.shop.model.Order;

import java.util.List;

public class OrderLogic implements NotSortable<Order> {
    @Override
    public void addElement(Order order) {
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public void updateElement(Order order) {
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public void removeElement(Order element) {
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public Order getElement(int id) {
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public List<Order> getAllElements() {
        throw new RuntimeException("Not implemented yet!");
    }
}
