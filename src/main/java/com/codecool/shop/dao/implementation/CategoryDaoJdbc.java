package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.GetAllDao;
import com.codecool.shop.model.Category;

import javax.sql.DataSource;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class CategoryDaoJdbc implements GetAllDao<Category> {

    private final DataSource dataSource;

    public CategoryDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * {@inheritDoc}
     *
     * @param category - category instance with defined all fields without id
     */
    @Override
    public int add(Category category) {
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "INSERT INTO product_category (name) VALUES (?);";
            PreparedStatement statement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, category.getName());
            statement.executeUpdate();
            ResultSet result = statement.getGeneratedKeys();
            result.next();
            category.setId(result.getInt(1));
            return result.getInt(1);
        } catch (SQLException error) {
            throw new RuntimeException("Error while adding a new Category.", error);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(Category thing) {
        throw new RuntimeException("Method not implemented yet!");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(int id) {
        throw new RuntimeException("Method not implemented yet!");
    }

    /**
     * {@inheritDoc}
     *
     * @param id - id of the category to get from database
     */
    @Override
    public Category get(int id) {
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "SELECT id, name FROM product_category WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return getCategory(result);
            }
            return null;

        } catch (SQLException error) {
            throw new RuntimeException("Error while getting a Category.", error);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Category> getAll() {
        try (Connection connection = dataSource.getConnection()) {
            List<Category> categories = new LinkedList<>();
            String sqlQuery = "SELECT id, name FROM product_category";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                categories.add(getCategory(result));
            }
            return categories;

        } catch (SQLException error) {
            throw new RuntimeException("Error while getting all Categories.", error);
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
