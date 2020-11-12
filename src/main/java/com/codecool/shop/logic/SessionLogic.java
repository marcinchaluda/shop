package com.codecool.shop.logic;

import com.codecool.shop.dao.ShopDatabaseManager;
import com.codecool.shop.dao.implementation.OrderDaoJdbc;
import com.codecool.shop.model.Order;

public class SessionLogic {
    OrderDaoJdbc orderDao = ShopDatabaseManager.Instance.getOrderDao();

    private static SessionLogic instance = null;

    public static SessionLogic getInstance() {
        if (instance == null) {
            instance = new SessionLogic();
        }
        return instance;
    }

    public boolean orderBelongsToUser(int userId, int orderId) {
        Order order = orderDao.get(orderId);
        if (order.getCart().getUser().getId() == userId) {
            return true;
        }
        return false;
    }
}
