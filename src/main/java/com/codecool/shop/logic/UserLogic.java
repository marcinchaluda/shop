package com.codecool.shop.logic;

import com.codecool.shop.dao.ModifyDao;
import com.codecool.shop.dao.ShopDatabaseManager;
import com.codecool.shop.model.User;

import java.util.List;

public class UserLogic implements NotSortable<User> {
    ModifyDao<User> userDao = ShopDatabaseManager.Instance.getUserDao();

    private static UserLogic instance = null;

    public static UserLogic getInstance() {
        if (instance == null) {
            instance = new UserLogic();
        }
        return instance;
    }

    @Override
    public void addElement(User user) {
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public void updateElement(User user) {
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public void removeElement(User element) {
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public User getElement(int id) {
        throw new RuntimeException("Not implemented yet! - getElement " + id);
    }

    @Override
    public List<User> getAllElements() {
        throw new RuntimeException("Not implemented yet!");
    }
}
