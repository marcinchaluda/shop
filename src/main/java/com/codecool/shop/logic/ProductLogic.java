package com.codecool.shop.logic;

import com.codecool.shop.logic.enumerators.SortType;
import com.codecool.shop.model.Product;

import java.util.List;

public class ProductLogic implements BusinessLogic<Product> {
    @Override
    public void addElement(Product product) {
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public void updateElement(Product product) {
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public Product getElement(int id) {
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public List<Product> getAllElements() {
        throw new RuntimeException("Not implemented yet!");
    }

    public List<Product> getAllFromDatabase(SortType sortType, String sortBy) {
        throw new RuntimeException("Not implemented yet!");
    }

}
