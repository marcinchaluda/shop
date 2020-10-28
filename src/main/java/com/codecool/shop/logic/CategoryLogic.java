package com.codecool.shop.logic;

import com.codecool.shop.dao.GetAllDao;
import com.codecool.shop.dao.ShopDatabaseManager;
import com.codecool.shop.model.Category;

import java.util.List;

public class CategoryLogic implements NotSortable<Category> {
    GetAllDao<Category> categoryDao = ShopDatabaseManager.Instance.getCategoryDao();

    private static CategoryLogic instance = null;

    public static CategoryLogic getInstance() {
        if (instance == null) {
            instance = new CategoryLogic();
        }
        return instance;
    }

    @Override
    public void addElement(Category category) {
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public void updateElement(Category category) {
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public void removeElement(Category category) {
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public Category getElement(int id) {
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public List<Category> getAllElements() {
        throw new RuntimeException("Not implemented yet!");
    }
}
