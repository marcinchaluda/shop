package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.Dao;
import com.codecool.shop.model.Address;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class AddressDaoJdbc implements Dao<Address> {

    private final DataSource dataSource;

    public AddressDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Address address) {
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "INSERT INTO address (country, city, zip_code, street, local_number) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, address.getCountry());
            statement.setString(2, address.getCity());
            statement.setString(3, address.getZipCode());
            statement.setString(2, address.getStreet());
            statement.setInt(3, address.getLocalNumber());
            statement.executeUpdate();
            ResultSet result = statement.getGeneratedKeys();
            result.next();
            address.setId(result.getInt(1));
        } catch (SQLException error) {
            throw new RuntimeException("Error while adding new Author.", error);
        }
    }

    @Override
    public void update(Address address) {

    }

    @Override
    public void remove(int id) {

    }

    @Override
    public Address get(int id) {
        return null;
    }

    @Override
    public List<Address> getAll() {
        return null;
    }
}
