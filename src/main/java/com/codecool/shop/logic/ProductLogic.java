package com.codecool.shop.logic;

import com.codecool.shop.dao.ShopDatabaseManager;
import com.codecool.shop.dao.SortDao;
import com.codecool.shop.model.Product;

import java.util.List;

public class ProductLogic implements Sortable<Product> {
    SortDao productDao = ShopDatabaseManager.Instance.getProductDao();

    private static ProductLogic instance = null;

    public static ProductLogic getInstance() {
        if (instance == null) {
            instance = new ProductLogic();
        }
        return instance;
    }

    @Override
    public void addElement(Product product) {
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public void updateElement(Product product) {
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public void removeElement(Product element) {
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public Product getElement(int id) {
        throw new RuntimeException("Not implemented yet! - getElement " + id);
    }

    @Override
    public List<Product> getAllElements(String sortType, String sortBy) {
        throw new RuntimeException("Not implemented yet! - getAllElements");
    }
}
