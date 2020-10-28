package com.codecool.shop.logic;

import com.codecool.shop.dao.ModifyDao;
import com.codecool.shop.dao.ShopDatabaseManager;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Product;

import java.util.Map;

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
        double totalPrice = 0;
        int quantity = 0;
        Cart cart = cartDao.get(id);
        Map<Product, Integer> products = cart.getProductList();

        for (Map.Entry<Product, Integer> product : products.entrySet()) {
            quantity += product.getValue();
            totalPrice += product.getKey().getDefaultPrice();
        }

        cart.setQuantity(quantity);
        cart.setTotalPrice(totalPrice);
        return cart;
    }
}
