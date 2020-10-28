package com.codecool.shop.logic;

import com.codecool.shop.dao.ModifyDao;
import com.codecool.shop.dao.ShopDatabaseManager;
import com.codecool.shop.model.Address;

public class AddressLogic implements BusinessLogic<Address> {
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
        addressDao.add(address);
    }

    @Override
    public void updateElement(Address address) {
        addressDao.update(address);
    }

    @Override
    public void removeElement(Address address) {
        addressDao.remove(address.getId());
    }

    @Override
    public Address getElement(int id) {
        return addressDao.get(id);
    }
}
