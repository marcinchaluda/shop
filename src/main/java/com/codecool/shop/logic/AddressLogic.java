package com.codecool.shop.logic;

import com.codecool.shop.model.Address;

import java.util.List;

public class AddressLogic implements BusinessLogic<Address>{
    @Override
    public void addToDatabase(Address address) {
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public void updateInDatabase(Address address) {
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public Address getFromDatabase(int id) {
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public List<Address> getAllFromDatabase() {
        throw new RuntimeException("Not implemented yet!");
    }
}
