package com.codecool.shop.logic;

import com.codecool.shop.dao.ModifyDao;
import com.codecool.shop.dao.ShopDatabaseManager;
import com.codecool.shop.model.User;

public class UserLogic implements BusinessLogic<User> {
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
        userDao.add(user);
    }

    @Override
    public void updateElement(User user) {
        userDao.update(user);
    }

    @Override
    public void removeElement(User user) {
        userDao.remove(user.getId());
    }

    @Override
    public User getElement(int id) {
        return userDao.get(id);
    }
}
