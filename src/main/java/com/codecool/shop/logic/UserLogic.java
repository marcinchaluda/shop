package com.codecool.shop.logic;

import com.codecool.shop.model.User;

import java.util.List;

public class UserLogic implements BusinessLogic<User> {
    @Override
    public void addToDatabase(User user) {
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public void updateInDatabase(User user) {
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public User getFromDatabase(int id) {
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public List<User> getAllFromDatabase() {
        throw new RuntimeException("Not implemented yet!");
    }
}
