package com.codecool.shop.logic;

import com.codecool.shop.model.Product;

import java.util.List;

public class ProductLogic implements Sortable<Product> {
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

    public List<Product> getAllElements(String sortType, String sortBy) {
        throw new RuntimeException("Not implemented yet! - getAllElements");
    }

}
