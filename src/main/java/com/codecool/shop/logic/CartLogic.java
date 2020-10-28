package com.codecool.shop.logic;

import com.codecool.shop.dao.ModifyDao;
import com.codecool.shop.dao.ShopDatabaseManager;
import com.codecool.shop.model.Cart;

import java.util.List;

public class CartLogic implements NotSortable<Cart> {
    ModifyDao<Cart> cartDao = ShopDatabaseManager.Instance.getCartDao();

    private static CartLogic instance = null;

    public static CartLogic getInstance() {
        if (instance == null) {
            instance = new CartLogic();
        }
        return instance;
    }

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
        throw new RuntimeException("Not implemented yet! - getElement " + id);
    }

    @Override
    public List<Cart> getAllElements() {
        throw new RuntimeException("Not implemented yet!");
    }
}
