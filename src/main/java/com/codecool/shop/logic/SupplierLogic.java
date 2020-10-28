package com.codecool.shop.logic;

import com.codecool.shop.dao.GetAllDao;
import com.codecool.shop.dao.ShopDatabaseManager;
import com.codecool.shop.model.Supplier;

import java.util.List;

public class SupplierLogic implements NotSortable<Supplier> {
    GetAllDao<Supplier> supplierDao = ShopDatabaseManager.Instance.getSupplierDao();

    private static SupplierLogic instance = null;

    public static SupplierLogic getInstance() {
        if (instance == null) {
            instance = new SupplierLogic();
        }
        return instance;
    }

    @Override
    public void addElement(Supplier supplier) {
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public void updateElement(Supplier supplier) {
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public void removeElement(Supplier element) {
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public Supplier getElement(int id) {
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public List<Supplier> getAllElements() {
        throw new RuntimeException("Not implemented yet!");
    }
}
