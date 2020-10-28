package com.codecool.shop.logic;

import com.codecool.shop.dao.ModifyDao;
import com.codecool.shop.dao.ShopDatabaseManager;
import com.codecool.shop.model.Address;

import java.util.List;

public class AddressLogic implements NotSortable<Address>{
    ModifyDao<Address> addressDao = ShopDatabaseManager.Instance.getAddressDao();

    private static AddressLogic instance = null;

    public static AddressLogic getInstance() {
        if (instance == null) {
            instance = new AddressLogic();
        }
        return instance;
    }

    @Override
    public void addElement(Address address) {
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public void updateElement(Address address) {
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public void removeElement(Address element) {
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public Address getElement(int id) {
        throw new RuntimeException("Not implemented yet! - getElement " + id);
    }

    @Override
    public List<Address> getAllElements() {
        throw new RuntimeException("Not implemented yet!");
    }
}
