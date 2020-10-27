package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.SimpleInOutDao;
import com.codecool.shop.model.Supplier;

import javax.sql.DataSource;
import java.util.List;

public class SupplierDaoJdbc implements SimpleInOutDao<Supplier> {

    private final DataSource dataSource;

    public SupplierDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Supplier supplier) {
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public Supplier get(int id) {
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public List<Supplier> getAll() {
        throw new RuntimeException("Not implemented yet!");
    }
}
