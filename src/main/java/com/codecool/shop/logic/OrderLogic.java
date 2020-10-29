package com.codecool.shop.logic;

import com.codecool.shop.dao.ModifyDao;
import com.codecool.shop.dao.ShopDatabaseManager;
import com.codecool.shop.model.Order;

public class OrderLogic implements BusinessLogic<Order> {
    ModifyDao<Order> orderDao = ShopDatabaseManager.Instance.getOrderDao();

    private static OrderLogic instance = null;

    public static OrderLogic getInstance() {
        if (instance == null) {
            instance = new OrderLogic();
        }
        return instance;
    }

    @Override
    public int addElement(Order order) {
        return orderDao.add(order);
    }

    @Override
    public void updateElement(Order order) {
        orderDao.update(order);
    }

    @Override
    public void removeElement(Order order) {
        orderDao.remove(order.getId());
    }

    @Override
    public Order getElement(int id) {
        return orderDao.get(id);
    }
}
