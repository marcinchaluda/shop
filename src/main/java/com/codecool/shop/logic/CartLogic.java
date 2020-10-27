package com.codecool.shop.logic;

import com.codecool.shop.model.Cart;

import java.util.List;

public class CartLogic implements NotSortable<Cart> {

    @Override
    public void addElement(Cart cart) {
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public void updateElement(Cart cart) {
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public void removeElement(Cart element) {
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public Cart getElement(int id) {
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public List<Cart> getAllElements() {
        throw new RuntimeException("Not implemented yet!");
    }
}
