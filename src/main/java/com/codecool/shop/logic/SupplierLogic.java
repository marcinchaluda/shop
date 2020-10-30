package com.codecool.shop.logic;

import com.codecool.shop.dao.GetAllDao;
import com.codecool.shop.dao.ShopDatabaseManager;
import com.codecool.shop.model.Supplier;

import java.util.List;

public class SupplierLogic implements GetAllLogic<Supplier> {
    GetAllDao<Supplier> supplierDao = ShopDatabaseManager.Instance.getSupplierDao();

    private static SupplierLogic instance = null;

    public static SupplierLogic getInstance() {
        if (instance == null) {
            instance = new SupplierLogic();
        }
        return instance;
    }

    @Override
    public int addElement(Supplier supplier) {
        return supplierDao.add(supplier);
    }

    @Override
    public void updateElement(Supplier supplier, int id) {
        supplier.setId(id);
        supplierDao.update(supplier);
    }

    @Override
    public void removeElement(Supplier supplier) {
        supplierDao.remove(supplier.getId());
    }

    @Override
    public Supplier getElement(int id) {
        return supplierDao.get(id);
    }

    @Override
    public List<Supplier> getAllElements() {
        return supplierDao.getAll();
    }
}
