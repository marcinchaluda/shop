package com.codecool.shop.logic;

import com.codecool.shop.dao.ModifyDao;
import com.codecool.shop.dao.ShopDatabaseManager;
import com.codecool.shop.model.Order;

import javax.mail.MessagingException;

public class OrderLogic implements BusinessLogic<Order> {
    private ModifyDao<Order> orderDao = ShopDatabaseManager.Instance.getOrderDao();
    private MailSender sender = MailSender.getInstance();

    private static OrderLogic instance = null;

    public static OrderLogic getInstance() {
        if (instance == null) {
            instance = new OrderLogic();
        }
        return instance;
    }

    @Override
    public int addElement(Order order) {
        int orderId = orderDao.add(order);
        try {
            String userEmail = order.getCart().getUser().getEmail();
            String emailSubject = "Order successfully created.";
            String emailBody = "Your order " + order.getId() + " waiting for payment!";
            sender.sendEmail(userEmail, emailSubject, emailBody);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return orderId;
    }

    @Override
    public void updateElement(Order order, int id) {
        order.setId(id);
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
