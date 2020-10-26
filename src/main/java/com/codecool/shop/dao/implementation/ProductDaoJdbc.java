package com.codecool.shop.dao.implementation;


import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.logic.enumerators.Category;
import com.codecool.shop.logic.enumerators.Supplier;
import com.codecool.shop.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductDaoJdbc implements ProductDao {

    private List<Product> data = new ArrayList<>();
    private static ProductDaoJdbc instance = null;

    private ProductDaoJdbc() {
    }

    public static ProductDaoJdbc getInstance() {
        if (instance == null) {
            instance = new ProductDaoJdbc();
        }
        return instance;
    }

    @Override
    public void add(Product product) {
        // TODO
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public void update(Product thing) {
        // TODO
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public void remove(int id) {
        // TODO
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public Product get(int id) {
        // TODO
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public List<Product> getAll() {
        // TODO
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        // TODO
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public List<Product> getBy(Category productCategory) {
        // TODO
        throw new RuntimeException("Not implemented yet!");
    }
}
