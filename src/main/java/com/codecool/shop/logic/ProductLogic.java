package com.codecool.shop.logic;

import com.codecool.shop.logic.enumerators.Category;
import com.codecool.shop.logic.enumerators.SortType;
import com.codecool.shop.logic.enumerators.Supplier;
import com.codecool.shop.model.Product;

import java.util.ArrayList;
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
        throw new RuntimeException("Not implemented yet! - getElement " + id);
    }

    @Override
    public List<Product> getAllElements() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("Tablet 3000",50.5, "PLN", "oooopis choooopie",
                Category.TABLET, Supplier.AMAZON));
        products.add(new Product("Tablet 200 from LENOVO",35.0, "EUR", "oooopis choooopie",
                Category.TABLET, Supplier.LENOVO));
        products.add(new Product("Telefon Miliun",600.0, "PLN", "oooopis choooopie",
                Category.PHONE, Supplier.AMAZON));
        return products;
    }

    public List<Product> getAllFromDatabase(SortType sortType, String sortBy) {
        throw new RuntimeException("Not implemented yet! - getAllElements - sorted");
    }

}
