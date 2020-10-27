package com.codecool.shop.dao;

import com.codecool.shop.model.*;

import java.util.List;

public interface ProductDao extends Dao<Product> {
    List<Product> getBy(Supplier supplier);
    List<Product> getBy(Category productCategory);
}
