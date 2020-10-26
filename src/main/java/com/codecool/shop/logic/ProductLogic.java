package com.codecool.shop.logic;

import com.codecool.shop.logic.enumerators.SortType;
import com.codecool.shop.model.Product;

import java.util.List;

public class ProductLogic implements BusinessLogic<Product> {
    @Override
    public void addToDatabase(Product product) {
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public void updateInDatabase(Product product) {
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public Product getFromDatabase(int id) {
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public List<Product> getAllFromDatabase() {
        throw new RuntimeException("Not implemented yet!");
    }

    public List<Product> getAllFromDatabase(SortType sortType, String sortBy) {
        throw new RuntimeException("Not implemented yet!");
    }

}
