package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.Dao;
import com.codecool.shop.model.Address;

import javax.sql.DataSource;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class AddressDaoJdbc implements Dao<Address> {

    private final DataSource dataSource;

    public AddressDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Address address) {
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "INSERT INTO address (country, city, zip_code, street, local_number) VALUES (?, ?, ?, ?, ?);";
            PreparedStatement statement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, address.getCountry());
            statement.setString(2, address.getCity());
            statement.setString(3, address.getZipCode());
            statement.setString(4, address.getStreet());
            statement.setInt(5, address.getLocalNumber());
            statement.executeUpdate();
            ResultSet result = statement.getGeneratedKeys();
            result.next();
            address.setId(result.getInt(1));
        } catch (SQLException error) {
            throw new RuntimeException("Error while adding a new Address.", error);
        }
    }

    @Override
    public void update(Address address) {
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "UPDATE address SET country = ?, city = ?, zip_code = ?, street = ?, local_number = ? WHERE id = ?;";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, address.getCountry());
            statement.setString(2, address.getCity());
            statement.setString(3, address.getZipCode());
            statement.setString(4, address.getStreet());
            statement.setInt(5, address.getLocalNumber());
            statement.setInt(6, address.getId());
        } catch (SQLException error) {
            throw new RuntimeException("Error while updating an Address.");
        }
    }

    @Override
    public void remove(int id) {
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "DELETE FROM address WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, id);
        } catch (SQLException error) {
            throw new RuntimeException("Error while removing an Address.");
        }
    }

    @Override
    public Address get(int id) {
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "SELECT id, country, city, zip_code, street, local_number FROM address WHERE id = ?;";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            return !result.next() ? null : getAddress(result);
        } catch (SQLException error) {
            throw new RuntimeException("Error while retrieving an Address with " + id + ".");
        }
    }

    @Override
    public List<Address> getAll() {
        try (Connection connection = dataSource.getConnection()) {
            List<Address> addresses = new LinkedList<>();
            String sqlQuery = "SELECT id, country, city, zip_code, street, local_number FROM address;";
            ResultSet result = connection.createStatement().executeQuery(sqlQuery);
            while (result.next()) {
                Address address = getAddress(result);
                addresses.add(address);
            }
            return addresses;
        } catch (SQLException error) {
            throw new RuntimeException("Error while retrieving all Addresses.");
        }
    }

    private Address getAddress(ResultSet result) throws SQLException {
        int id = result.getInt("id");
        String country = result.getString("country");
        String city = result.getString("city");
        String zipCode = result.getString("zip_code");
        String street = result.getString("street");
        int local = result.getInt("local_number");

        Address address = new Address(country, city, zipCode, street, local);
        address.setId(id);
        return address;
    }
}
