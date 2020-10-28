package com.codecool.shop.logic;

import com.codecool.shop.dao.GetAllDao;
import com.codecool.shop.dao.ShopDatabaseManager;
import com.codecool.shop.model.Category;

import java.util.List;

public class CategoryLogic implements GetAllLogic<Category> {
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
        categoryDao.add(category);
    }

    @Override
    public void updateElement(Category category) {
        categoryDao.update(category);
    }

    @Override
    public void removeElement(Category category) {
        categoryDao.remove(category.getId());
    }

    @Override
    public Category getElement(int id) {
        return categoryDao.get(id);
    }

    @Override
    public List<Category> getAllElements() {
        return categoryDao.getAll();
    }
}
