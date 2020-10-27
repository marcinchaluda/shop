package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.SimpleInOutDao;
import com.codecool.shop.model.Category;

import javax.sql.DataSource;
import java.util.List;

public class CategoryDaoJdbc implements SimpleInOutDao<Category> {

    private final DataSource dataSource;

    public CategoryDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Category category) {

    }

    @Override
    public Category get(int id) {
        return null;
    }

    @Override
    public List<Category> getAll() {
        return null;
    }
}
