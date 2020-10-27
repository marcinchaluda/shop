package com.codecool.shop.dao;

import com.codecool.shop.logic.enumerators.Category;
import com.codecool.shop.logic.enumerators.Supplier;
import com.codecool.shop.model.Product;

import java.util.List;

public interface ProductDao extends Dao<Product> {
    List<Product> getBy(Supplier supplier);
    List<Product> getBy(Category productCategory);
}
