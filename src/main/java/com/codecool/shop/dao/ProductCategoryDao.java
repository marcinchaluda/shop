package com.codecool.shop.dao;

import java.util.List;

public interface ProductCategoryDao {

    void add(ProductCategory category);
    ProductCategory find(int id);
    void remove(int id);

    List<ProductCategory> getAll();

}
