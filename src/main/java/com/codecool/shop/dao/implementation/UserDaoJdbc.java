package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.Dao;
import com.codecool.shop.model.Address;
import com.codecool.shop.model.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class UserDaoJdbc implements Dao<User> {

    private final DataSource dataSource;
    private final AddressDaoJdbc addressDao;

    public UserDaoJdbc(DataSource dataSource, AddressDaoJdbc addressDao) {
        this.dataSource = dataSource;
        this.addressDao = addressDao;
    }

    @Override
    public void add(User user) {
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "INSERT INTO user_account (full_name, email, phone_number, billing_address, shipping_address) VALUES (?, ?, ?, ?, ?);";
            PreparedStatement statement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPhoneNumber());
            statement.setInt(4, user.getBillingAddress().getId());
            statement.setInt(5, user.getShippingAddress().getId());
            statement.executeUpdate();
            ResultSet result = statement.getGeneratedKeys();
            result.next();
            user.setId(result.getInt(1));
        } catch (SQLException error) {
            throw new RuntimeException("Error while adding a new User.", error);
        }
    }

    @Override
    public void update(User user) {
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "UPDATE user_account SET full_name = ?, email = ?, phone_number = ?, billing_address = ?, shipping_address = ? WHERE id = ?;";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPhoneNumber());
            statement.setInt(4, user.getBillingAddress().getId());
            statement.setInt(5, user.getShippingAddress().getId());
            statement.setInt(6, user.getId());
        } catch (SQLException error) {
            throw new RuntimeException("Error while updating a User.", error);
        }
    }

    @Override
    public void remove(int id) {
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "DELETE FROM user_account WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, id);
        } catch (SQLException error) {
            throw new RuntimeException("Error while removing a User.", error);
        }
    }

    @Override
    public User get(int id) {
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "SELECT id, full_name, email, phone_number, billing_address, shipping_address FROM user_account WHERE id = ?;";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            return !result.next() ? null : getUser(result);
        } catch (SQLException error) {
            throw new RuntimeException("Error while retrieving a User with " + id + ".", error);
        }
    }

    @Override
    public List<User> getAll() {
        try (Connection connection = dataSource.getConnection()) {
            List<User> users = new LinkedList<>();
            String sqlQuery = "SELECT id, full_name, email, phone_number, billing_address, shipping_address FROM user_account;";
            ResultSet result = connection.createStatement().executeQuery(sqlQuery);
            while (result.next()) {
                User user = getUser(result);
                users.add(user);
            }
            return users;
        } catch (SQLException error) {
            throw new RuntimeException("Error while retrieving all Users.", error);
        }
    }

    private User getUser(ResultSet result) throws SQLException {
        int id = result.getInt("id");
        String name = result.getString("full_name");
        String email = result.getString("email");
        String phoneNumber = result.getString("phone_number");
        Address billingAddress = addressDao.get(result.getInt("billing_address"));
        Address shippingAddress = addressDao.get(result.getInt("shipping_address"));

        User user = new User(name, email, phoneNumber, billingAddress, shippingAddress);
        user.setId(id);
        return user;
    }
}
