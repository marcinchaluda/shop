package com.codecool.shop.logic;

import com.codecool.shop.api.HelpServlet;
import com.codecool.shop.dao.ShopDatabaseManager;
import com.codecool.shop.dao.implementation.UserDaoJdbc;
import com.codecool.shop.model.User;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

public class UserLogic implements BusinessLogic<User> {
    UserDaoJdbc userDao = ShopDatabaseManager.Instance.getUserDao();
    private MailSender sender = MailSender.getInstance();

    private static UserLogic instance = null;

    public static UserLogic getInstance() {
        if (instance == null) {
            instance = new UserLogic();
        }
        return instance;
    }

    @Override
    public int addElement(User user) {
        return userDao.add(user);
    }

    public int addElementWithOutAddress(User user) {
        if (getUserByName(user.getName()) == null) {
            sendWelcomeEmail(user);
            return userDao.addUserWithOutAddress(user);
        }
        return HelpServlet.USER_ALREADY_PRESENT;
    }

    private void sendWelcomeEmail(User user) {
        String userEmail = user.getEmail();
        String emailSubject = "Welcome to Codecool shop.";
        String emailBody = "You received this email due to successful registration to our shop. " +
                "You are now able to login to you account. Happy shopping!";
        try {
            sender.sendEmail(userEmail, emailSubject, emailBody);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateElement(User user, int id) {
        user.setId(id);
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

    private User getUserByName(String name) {
        User currentUser = null;
        List<User> users= userDao.getAll();
        for (User user : users) {
            if (user.getName().equals(name)) {
                currentUser = user;
            }
        }
        return currentUser;
    }
}
