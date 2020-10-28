package com.codecool.shop.logic;

import com.codecool.shop.dao.ModifyDao;
import com.codecool.shop.dao.ShopDatabaseManager;
import com.codecool.shop.model.Cart;

public class CartLogic implements BusinessLogic<Cart> {
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
        cartDao.add(cart);
    }

    @Override
    public void updateElement(Cart cart) {
        cartDao.update(cart);
    }

    @Override
    public void removeElement(Cart cart) {
        cartDao.remove(cart.getId());
    }

    @Override
    public Cart getElement(int id) {
        return cartDao.get(id);
    }
}
