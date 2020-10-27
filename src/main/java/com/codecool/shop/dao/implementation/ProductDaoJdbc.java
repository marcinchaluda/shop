package com.codecool.shop.dao.implementation;


import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.logic.enumerators.Category;
import com.codecool.shop.logic.enumerators.Supplier;
import com.codecool.shop.model.Product;

import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import java.sql.*;


public class ProductDaoJdbc implements ProductDao {

    private List<Product> data = new ArrayList<>();
    private static ProductDaoJdbc instance = null;
    private final DataSource dataSource;

    private ProductDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static ProductDaoJdbc getInstance(DataSource dataSource) {
        if (instance == null) {
            instance = new ProductDaoJdbc(dataSource);
        }
        return instance;
    }

    @Override
    public void add(Product product) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO product VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, product.getName());
            st.setString(2, product.getDescription());
            st.setDouble(3, product.getDefaultPrice());
            st.setString(4, product.getDefaultCurrency());
            st.setString(5, product.getProductCategory().getName());
            st.setString(6, product.getSupplier().getName());
            st.setString(7, product.getImageSource());
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            rs.next();
            product.setId(rs.getInt(1));

        } catch (SQLException throwable) {
            throw new RuntimeException("Error while adding new Product.", throwable);
        }
    }

    @Override
    public void update(Product product) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "UPDATE product SET product_name = ?, description = ?, unit_price = ?, currency = ?, category = ?, supplier = ?, image_source = ? WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, product.getName());
            st.setString(2, product.getDescription());
            st.setDouble(3, product.getDefaultPrice());
            st.setString(4, product.getDefaultCurrency());
            st.setString(5, product.getProductCategory().getName());
            st.setString(6, product.getSupplier().getName());
            st.setString(7, product.getImageSource());
            st.setInt(8, product.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "DELETE FROM product WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Product get(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT product_name, description, unit_price, currency, category, supplier, image_source FROM product WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                return null;
            }

            Product product = new Product(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getDouble(3),
                    rs.getString(4),
                    Category.getFromValue(rs.getString(5)),
                    Supplier.getFromValue(rs.getString(6)),
                    rs.getString(7)
            );

            product.setId(id);
            return product;

        } catch (SQLException e) {
            throw new RuntimeException("Error while reading product with id: " + id, e);
        }
    }

    @Override
    public List<Product> getAll() {
        // TODO
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        // TODO
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public List<Product> getBy(Category productCategory) {
        // TODO
        throw new RuntimeException("Not implemented yet!");
    }
}
