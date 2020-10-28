package com.codecool.shop.dao;

import com.codecool.shop.model.Category;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.Supplier;

import java.util.List;

public interface SortDao extends GetAllDao<Product> {
    /**
     * DAO's method that get all elements from database when objects is instance of Sortable and sort it by supplier
     */
    List<Product> getBy(Supplier supplier);

    /**
     * DAO's method that get all elements from database when objects is instance of Sortable and sort it by category
     */
    List<Product> getBy(Category productCategory);
}
