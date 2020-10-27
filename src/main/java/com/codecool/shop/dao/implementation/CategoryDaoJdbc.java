package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.SimpleInOutDao;
import com.codecool.shop.model.Category;

import javax.sql.DataSource;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class CategoryDaoJdbc implements SimpleInOutDao<Category> {

    private final DataSource dataSource;

    public CategoryDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Category category) {
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "INSERT INTO product_category (name) VALUES (?);";
            PreparedStatement statement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, category.getName());
            statement.executeUpdate();
            ResultSet result = statement.getGeneratedKeys();
            result.next();
            category.setId(result.getInt(1));
        } catch (SQLException error) {
            throw new RuntimeException("Error while adding a new Category.", error);
        }
    }

    @Override
    public Category get(int id) {
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "SELECT id, name FROM product_category WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, id);
            statement.executeUpdate();
            ResultSet result = statement.getGeneratedKeys();
            if (result.next()) {
                return getCategory(result);
            }
            return null;
        } catch (SQLException error) {
            throw new RuntimeException("Error while getting a Supplier.", error);
        }
    }

    @Override
    public List<Category> getAll() {
        try (Connection connection = dataSource.getConnection()) {
            List<Category> categories = new LinkedList<>();
            String sqlQuery = "SELECT id, name, description, country FROM supplier";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.executeUpdate();
            ResultSet result = statement.getGeneratedKeys();
            while (result.next()) {
                categories.add(getCategory(result));
            }
            return categories;
        } catch (SQLException error) {
            throw new RuntimeException("Error while getting all Suppliers.", error);
        }
    }

    private Category getCategory(ResultSet result) throws SQLException {
        int id = result.getInt("id");
        String name = result.getString("name");
        Category category = new Category(name);
        category.setId(id);
        return category;
    }
}
