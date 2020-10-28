package com.codecool.shop.config;

import com.codecool.shop.dao.ShopDatabaseManager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;

@WebListener
public class Initializer implements ServletContextListener {

    ShopDatabaseManager shopDatabaseManager;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            shopDatabaseManager = new ShopDatabaseManager();
            System.out.println(shopDatabaseManager);
        } catch (SQLException e) {
            throw new RuntimeException("Exception", e);
        }
    }
}