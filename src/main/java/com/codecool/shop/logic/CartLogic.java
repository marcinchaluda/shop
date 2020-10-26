package com.codecool.shop.logic;

import com.codecool.shop.model.Cart;

import java.util.List;

public class CartLogic implements BusinessLogic<Cart> {

    @Override
    public void addToDatabase(Cart cart) {
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public void updateInDatabase(Cart cart) {
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public Cart getFromDatabase(int id) {
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public List<Cart> getAllFromDatabase() {
        throw new RuntimeException("Not implemented yet!");
    }
}
