package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.ProductDaoJdbc;
import com.codecool.shop.logic.enumerators.Category;
import com.codecool.shop.logic.enumerators.Supplier;
import com.codecool.shop.model.Product;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class ShopDatabaseManager {
    private ProductDao productDao;

    public ShopDatabaseManager() throws SQLException {
        setup();
    }

    private void setup() throws SQLException {
        DataSource dataSource = connect();
        productDao = ProductDaoJdbc.getInstance(dataSource);
    }

    private DataSource connect() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        String dbName = System.getenv("PSQL_DB_NAME");
        String user = System.getenv("PSQL_USER_NAME");
        String password = System.getenv("PSQL_PASSWORD");

        dataSource.setDatabaseName(dbName);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        System.out.println("Trying to connect");
        dataSource.getConnection().close();
        System.out.println("Connection ok.");

        return dataSource;
    }

    public void addProduct(Product product) {
        productDao.add(product);
    }

    public void updateProduct(Product product) {
        productDao.update(product);
    }

    public void removeProduct(int product_id) {
        productDao.remove(product_id);
    }

    public Product getProduct(int product_id) {
        return productDao.get(product_id);
    }

    public List<Product> getAllProducts() {
        return productDao.getAll();
    }

    public List<Product> getBy(Supplier supplier) {
        return productDao.getBy(supplier);
    }

    public List<Product> getBy(Category category) {
        return productDao.getBy(category);
    }
}