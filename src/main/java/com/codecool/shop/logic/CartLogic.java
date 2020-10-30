package com.codecool.shop.logic;

import com.codecool.shop.dao.ModifyDao;
import com.codecool.shop.dao.ShopDatabaseManager;
import com.codecool.shop.dao.implementation.CartDaoJdbc;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.ProductInCart;

public class CartLogic implements BusinessLogic<Cart> {
    CartDaoJdbc cartDao = ShopDatabaseManager.Instance.getCartDao();

    private static CartLogic instance = null;

    public static CartLogic getInstance() {
        if (instance == null) {
            instance = new CartLogic();
        }
        return instance;
    }

    @Override
    public int addElement(Cart cart) {
        return cartDao.add(cart);
    }

    @Override
    public void updateElement(Cart cart, int id) {
        cart.setId(id);
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

    public void updateProductInCart(ProductInCart productInCart, int cartId, String action) {
        cartDao.updateProductInCart(productInCart, cartId, action);
    }
}
