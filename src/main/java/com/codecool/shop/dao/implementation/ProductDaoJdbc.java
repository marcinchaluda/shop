package com.codecool.shop.dao.implementation;


import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SimpleInOutDao;
import com.codecool.shop.model.Category;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.Supplier;

import java.util.LinkedList;
import java.util.List;
import javax.sql.DataSource;
import java.sql.*;


public class ProductDaoJdbc implements ProductDao {

    private final DataSource dataSource;
    private final SimpleInOutDao<Supplier> supplierDao;
    private final SimpleInOutDao<Category> categoryDao;

    public ProductDaoJdbc(DataSource dataSource, SimpleInOutDao<Supplier> supplierDao, SimpleInOutDao<Category> categoryDao) {
        this.dataSource = dataSource;
        this.supplierDao = supplierDao;
        this.categoryDao = categoryDao;
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
            st.setInt(5, product.getProductCategory().getId());
            st.setInt(6, product.getSupplier().getId());
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
            st.setInt(5, product.getProductCategory().getId());
            st.setInt(6, product.getSupplier().getId());
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

            Category category = categoryDao.get(rs.getInt(5));
            Supplier supplier = supplierDao.get(rs.getInt(6));

            Product product = new Product(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getDouble(3),
                    rs.getString(4),
                    category,
                    supplier,
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
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT * FROM product";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            List<Product> data = new LinkedList<>();
            while (rs.next()) {
                Category category = categoryDao.get(rs.getInt(6));
                Supplier supplier = supplierDao.get(rs.getInt(7));
                Product product = new Product(
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDouble(4),
                        rs.getString(5),
                        category,
                        supplier,
                        rs.getString(8)
                );
                product.setId(rs.getInt(1));
                data.add(product);
            }
            return data;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading all products", e);
        }
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, product_name, description, unit_price, currency, category, image_source FROM product WHERE supplier = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, supplier.getId());
            ResultSet rs = st.executeQuery();

            List<Product> data = new LinkedList<>();
            while (rs.next()) {
                Category category = categoryDao.get(rs.getInt(6));
                Product product = new Product(
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDouble(4),
                        rs.getString(5),
                        category,
                        supplier,
                        rs.getString(7)
                );
                product.setId(rs.getInt(1));

                data.add(product);
            }
            return data;

        } catch (SQLException e) {
            throw new RuntimeException("Error while reading product with supplier: " + supplier.getName(), e);
        }
    }

    @Override
    public List<Product> getBy(Category category) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, product_name, description, unit_price, currency, supplier, image_source FROM product WHERE category = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, category.getId());
            ResultSet rs = st.executeQuery();

            List<Product> data = new LinkedList<>();
            while (rs.next()) {
                Supplier supplier = supplierDao.get(rs.getInt(7));
                Product product = new Product(
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDouble(4),
                        rs.getString(5),
                        category,
                        supplier,
                        rs.getString(7)
                );
                product.setId(rs.getInt(1));

                data.add(product);
            }
            return data;

        } catch (SQLException e) {
            throw new RuntimeException("Error while reading product with category: " + category.getName(), e);
        }
    }
}
