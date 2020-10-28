package com.codecool.shop.logic;

import com.codecool.shop.dao.GetAllDao;
import com.codecool.shop.dao.ShopDatabaseManager;
import com.codecool.shop.dao.SortDao;
import com.codecool.shop.model.Category;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.Supplier;

import java.util.List;
import java.util.Optional;

public class ProductLogic implements Sortable<Product> {
    SortDao productDao = ShopDatabaseManager.Instance.getProductDao();

    private static ProductLogic instance = null;

    public static ProductLogic getInstance() {
        if (instance == null) {
            instance = new ProductLogic();
        }
        return instance;
    }

    @Override
    public void addElement(Product product) {
        productDao.add(product);
    }

    @Override
    public void updateElement(Product product) {
        productDao.update(product);
    }

    @Override
    public void removeElement(Product product) {
        productDao.remove(product.getId());
    }

    @Override
    public Product getElement(int id) {
        return productDao.get(id);
    }

    @Override
    public List<Product> getAllElements(String sortType, String sortBy) {
        if (sortType.equals("default") || sortBy.equals("default")) {
            return productDao.getAll();
        } else {
            if (sortType.equals("category")) {
                GetAllDao<Category> categoryDao = ShopDatabaseManager.Instance.getCategoryDao();
                List<Category> categories = categoryDao.getAll();
                Category category = categories.stream()
                        .filter(cat -> cat.getName().equals(sortBy))
                        .findFirst()
                        .orElse(null);
                if (category == null) {
                    throw new RuntimeException("Bad type of category!");
                }
                return productDao.getBy(category);
            } else {
                GetAllDao<Supplier> supplierDao = ShopDatabaseManager.Instance.getSupplierDao();
                List<Supplier> suppliers = supplierDao.getAll();
                Supplier supplier = suppliers.stream()
                        .filter(cat -> cat.getName().equals(sortBy))
                        .findFirst()
                        .orElse(null);
                if (supplier == null) {
                    throw new RuntimeException("Bad type of category!");
                }
                return productDao.getBy(supplier);
            }
        }
    }
}
