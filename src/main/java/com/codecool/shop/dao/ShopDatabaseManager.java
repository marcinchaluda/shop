package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.model.*;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Intermediate class responsible for connections with our database
 */
public class ShopDatabaseManager {
    private GetAllDao<Category> categoryDao;
    private GetAllDao<Supplier> supplierDao;
    private SortDao productDao;
    private ModifyDao<Address> addressDao;
    private UserDaoJdbc userDao;
    private CartDaoJdbc cartDao;
    private ModifyDao<Order> orderDao;

    private static ShopDatabaseManager instance = null;
    public static ShopDatabaseManager Instance;

    static {
        try {
            Instance = ShopDatabaseManager.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ShopDatabaseManager getInstance() throws SQLException {
        if (instance == null) {
            instance = new ShopDatabaseManager();
        }
        return instance;
    }

    /**
     * Constructor that runs setup right after creating instance of ShopDatabaseManager
     */
    private ShopDatabaseManager() throws SQLException {
        setup();
    }

    /**
     * Start method that establish connection with database and set up all DAO's instances
     */
    private void setup() throws SQLException {
        DataSource dataSource = null;
        try {
            dataSource = DataSourceFactory.getPostgreSQLShopDataSource();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (dataSource != null) {
            connect(dataSource);
            setUpAllDAOs(dataSource);
        } else {
            throw new SQLException("Can't connect to database!");
        }
    }

    /**
     * Private method to define all DAO's before work with database
     */
    private void setUpAllDAOs(DataSource dataSource) {
        categoryDao = new CategoryDaoJdbc(dataSource);
        supplierDao = new SupplierDaoJdbc(dataSource);
        productDao = new ProductDaoJdbc(dataSource, supplierDao, categoryDao);
        addressDao = new AddressDaoJdbc(dataSource);
        userDao = new UserDaoJdbc(dataSource, addressDao);
        cartDao = new CartDaoJdbc(dataSource, userDao, productDao);
        orderDao = new OrderDaoJdbc(dataSource, cartDao);
    }

    /**
     * Return DataSource instance based on environment variables
     */
    private void connect(DataSource dataSource) throws SQLException {
        System.out.println("Trying to connect");
        dataSource.getConnection().close();
        System.out.println("Connection ok.");
    }

    public GetAllDao<Category> getCategoryDao() {
        return categoryDao;
    }

    public GetAllDao<Supplier> getSupplierDao() {
        return supplierDao;
    }

    public SortDao getProductDao() {
        return productDao;
    }

    public ModifyDao<Address> getAddressDao() {
        return addressDao;
    }

    public UserDaoJdbc getUserDao() {
        return userDao;
    }

    public CartDaoJdbc getCartDao() {
        return cartDao;
    }

    public ModifyDao<Order> getOrderDao() {
        return orderDao;
    }

    /**
     * Add new product to database
     *
     * @param product - product object with defined all fields
     */
    public void addProduct(Product product) {
        productDao.add(product);
    }

    /**
     * Update product in database
     *
     * @param product - product object with defined all fields
     */
    public void updateProduct(Product product) {
        productDao.update(product);
    }

    /**
     * Remove product from database
     *
     * @param product_id - id of product to find and remove from database
     */
    public void removeProduct(int product_id) {
        productDao.remove(product_id);
    }

    /**
     * Get product from database and return created instance of Product with all fields defined
     *
     * @param product_id - id of product to find
     */
    public Product getProduct(int product_id) {
        return productDao.get(product_id);
    }

    /**
     * Get list of products from database and return list of instances
     */
    public List<Product> getAllProducts() {
        return productDao.getAll();
    }

    /**
     * Get list of products from database and return list of instances only with defined supplier
     *
     * @param supplier - supplier to find
     */
    public List<Product> getBy(Supplier supplier) {
        return productDao.getBy(supplier);
    }


    /**
     * Get list of products from database and return list of instances only with defined category
     *
     * @param category - category to find
     */
    public List<Product> getBy(Category category) {
        return productDao.getBy(category);
    }

    /**
     * Get one supplier from database.
     **/
    public Supplier getSupplier(int id) {
        return supplierDao.get(id);
    }

    /**
     * Get all of suppliers from database.
     **/
    public List<Supplier> getAllSuppliers() {
        return supplierDao.getAll();
    }

    /**
     * Get one category from database.
     **/
    public Category getCategory(int id) {
        return categoryDao.get(id);
    }

    /**
     * Get all of categories from database.
     **/
    public List<Category> getAllCategories() {
        return categoryDao.getAll();
    }
}