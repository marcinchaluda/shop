package com.codecool.shop.dao.implementation;


import com.codecool.shop.dao.GetAllDao;
import com.codecool.shop.dao.SortDao;
import com.codecool.shop.model.Category;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.Supplier;

import javax.sql.DataSource;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;


public class ProductDaoJdbc implements GetAllDao<Product>, SortDao {

    private final DataSource dataSource;
    private final GetAllDao<Supplier> supplierDao;
    private final GetAllDao<Category> categoryDao;

    public ProductDaoJdbc(DataSource dataSource, GetAllDao<Supplier> supplierDao, GetAllDao<Category> categoryDao) {
        this.dataSource = dataSource;
        this.supplierDao = supplierDao;
        this.categoryDao = categoryDao;
    }

    /**
     * {@inheritDoc}
     *
     * @param product - product instance with defined all fields without id
     */
    @Override
    public int add(Product product) {
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "INSERT INTO product VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
            setFieldsInStatement(statement, product);
            statement.executeUpdate();
            ResultSet result = statement.getGeneratedKeys();
            result.next();
            product.setId(result.getInt(1));
            return result.getInt(1);
        } catch (SQLException throwable) {
            throw new RuntimeException("Error while adding new Product.", throwable);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param product - product instance with defined all fields
     */
    @Override
    public void update(Product product) {
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "UPDATE product SET product_name = ?, description = ?, unit_price = ?, currency = ?, category = ?, supplier = ?, image_source = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            setFieldsInStatement(statement, product);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error while updating a Product.", e);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param id - id of the product to remove from database
     */
    @Override
    public void remove(int id) {
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "DELETE FROM product WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error while removing a Product.", e);
        }
    }

    private void setFieldsInStatement(PreparedStatement statement, Product product) throws SQLException {
        statement.setString(1, product.getName());
        statement.setString(2, product.getDescription());
        statement.setDouble(3, product.getDefaultPrice());
        statement.setString(4, product.getDefaultCurrency());
        statement.setInt(5, product.getProductCategory().getId());
        statement.setInt(6, product.getSupplier().getId());
        statement.setString(7, product.getImageSource());
        statement.setInt(8, product.getId());
    }

    /**
     * {@inheritDoc}
     *
     * @param id - id of the product to get from database
     */
    @Override
    public Product get(int id) {
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "SELECT product_name, description, unit_price, currency, category, supplier, image_source FROM product WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            if (!result.next()) {
                return null;
            }

            Category category = categoryDao.get(result.getInt(5));
            Supplier supplier = supplierDao.get(result.getInt(6));

            Product product = new Product(
                    result.getString(1),
                    result.getString(2),
                    result.getDouble(3),
                    result.getString(4),
                    category,
                    supplier,
                    result.getString(7)
            );

            product.setId(id);
            return product;

        } catch (SQLException e) {
            throw new RuntimeException("Error while reading product with id: " + id, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Product> getAll() {
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "SELECT * FROM product";
            ResultSet result = connection.createStatement().executeQuery(sqlQuery);
            List<Product> data = new LinkedList<>();
            while (result.next()) {
                Category category = categoryDao.get(result.getInt(6));
                Supplier supplier = supplierDao.get(result.getInt(7));
                Product product = getProduct(result, category, supplier);
                product.setId(result.getInt(1));
                data.add(product);
            }
            return data;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading all products", e);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param supplier - valid supplier name
     */
    @Override
    public List<Product> getBy(Supplier supplier) {
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "SELECT id, product_name, description, unit_price, currency, category, supplier, image_source FROM product WHERE supplier = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, supplier.getId());
            ResultSet result = statement.executeQuery();

            List<Product> data = new LinkedList<>();
            while (result.next()) {
                Category category = categoryDao.get(result.getInt(6));
                Product product = getProduct(result, category, supplier);
                product.setId(result.getInt(1));
                data.add(product);
            }
            return data;

        } catch (SQLException e) {
            throw new RuntimeException("Error while reading product with supplier: " + supplier.getName(), e);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param category - valid category name
     */
    @Override
    public List<Product> getBy(Category category) {
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "SELECT id, product_name, description, unit_price, currency, category, supplier, image_source FROM product WHERE category = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, category.getId());
            ResultSet result = statement.executeQuery();

            List<Product> data = new LinkedList<>();
            while (result.next()) {
                Supplier supplier = supplierDao.get(result.getInt(6));
                Product product = getProduct(result, category, supplier);
                product.setId(result.getInt(1));
                data.add(product);
            }
            return data;

        } catch (SQLException e) {
            throw new RuntimeException("Error while reading product with category: " + category.getName(), e);
        }
    }

    private Product getProduct(ResultSet result, Category category, Supplier supplier) throws SQLException {
        return new Product(
                result.getString(2),
                result.getString(3),
                result.getDouble(4),
                result.getString(5),
                category,
                supplier,
                result.getString(8)
        );
    }
}
