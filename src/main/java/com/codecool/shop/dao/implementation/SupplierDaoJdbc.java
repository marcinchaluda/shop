package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.GetAllDao;
import com.codecool.shop.model.Supplier;

import javax.sql.DataSource;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class SupplierDaoJdbc implements GetAllDao<Supplier> {

    private final DataSource dataSource;

    public SupplierDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * {@inheritDoc}
     *
     * @param supplier - supplier instance with defined all fields without ID
     */
    @Override
    public void add(Supplier supplier) {
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "INSERT INTO supplier (name, description, country) VALUES (?, ?, ?);";
            PreparedStatement statement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, supplier.getName());
            statement.setString(2, supplier.getDescription());
            statement.setString(3, supplier.getCountry());
            statement.executeUpdate();
            ResultSet result = statement.getGeneratedKeys();
            result.next();
            supplier.setId(result.getInt(1));
        } catch (SQLException error) {
            throw new RuntimeException("Error while adding a new Supplier.", error);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(Supplier thing) {
        throw new RuntimeException("Method not implemented yet!");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(int id) {
        throw new RuntimeException("Method not implemented yet!");
    }

    /**
     * {@inheritDoc}
     *
     * @param id - id of the supplier to get from database
     */
    @Override
    public Supplier get(int id) {
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "SELECT id, name, description, country FROM supplier WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, id);
            statement.executeUpdate();
            ResultSet result = statement.getGeneratedKeys();
            if (result.next()) {
                return getSupplier(result);
            }
            return null;
        } catch (SQLException error) {
            throw new RuntimeException("Error while getting a Supplier.", error);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Supplier> getAll() {
        try (Connection connection = dataSource.getConnection()) {
            List<Supplier> suppliers = new LinkedList<>();
            String sqlQuery = "SELECT id, name, description, country FROM supplier";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.executeUpdate();
            ResultSet result = statement.getGeneratedKeys();
            while (result.next()) {
                suppliers.add(getSupplier(result));
            }
            return suppliers;
        } catch (SQLException error) {
            throw new RuntimeException("Error while getting all Suppliers.", error);
        }
    }

    private Supplier getSupplier(ResultSet result) throws SQLException {
        int id = result.getInt("id");
        String name = result.getString("name");
        String description = result.getString("description");
        String country = result.getString("country");
        Supplier supplier = new Supplier(name, description, country);
        supplier.setId(id);
        return supplier;
    }
}
