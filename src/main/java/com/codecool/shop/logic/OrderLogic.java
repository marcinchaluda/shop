package com.codecool.shop.logic;

import com.codecool.shop.dao.ModifyDao;
import com.codecool.shop.dao.ShopDatabaseManager;
import com.codecool.shop.model.Order;

import java.util.List;

public class OrderLogic implements NotSortable<Order> {
    ModifyDao<Order> orderDao = ShopDatabaseManager.Instance.getOrderDao();

    private static OrderLogic instance = null;

    public static OrderLogic getInstance() {
        if (instance == null) {
            instance = new OrderLogic();
        }
        return instance;
    }

    @Override
    public void addElement(Order order) {
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public void updateElement(Order order) {
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public void removeElement(Order element) {
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public Order getElement(int id) {
        throw new RuntimeException("Not implemented yet! - getElement " + id);
    }

    @Override
    public List<Order> getAllElements() {
        throw new RuntimeException("Not implemented yet!");
    }
}
