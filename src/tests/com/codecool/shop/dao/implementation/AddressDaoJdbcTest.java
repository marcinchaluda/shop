package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.DataSourceFactory;
import com.codecool.shop.model.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddressDaoJdbcTest {

    private DataSource dataSource;
    private AddressDaoJdbc addressDao;

    @BeforeEach
    public void prepare() {
        dataSource = DataSourceFactory.getPostgreSQLTestDataSource();
        addressDao = new AddressDaoJdbc(dataSource);
        clearAddressTable();
    }

    private void clearAddressTable() {
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "TRUNCATE TABLE address RESTART IDENTITY CASCADE;";
            connection.prepareStatement(sqlQuery).execute();
        } catch (SQLException error) {
            throw new RuntimeException("Error while clearing test table.", error);
        }
    }

    @Order(1)
    @Test
    public void should_addressInsertedIntoDatabaseEqualsThatFromDatabase_when_addAddressToDatabase() {
        final int ADDRESS_ID = 1;

        Address address = new Address("Poland", "Warsaw", "31-476", "Hollywood", 115);
        address.setId(ADDRESS_ID);
        addressDao.add(address);

        Address addressFromDB = addressDao.get(ADDRESS_ID);

        assertEquals(address, addressFromDB);
    }

    @Order(2)
    @Test
    public void should_updatedAddressEqualsThatFromDatabase_when_createdAddAndModifiedAddress() {
        final int ADDRESS_ID = 1;

        Address address = new Address("Poland", "Warsaw", "31-476", "Hollywood", 115);
        address.setId(ADDRESS_ID);
        addressDao.add(address);

        address.setCountry("Tomaszowy Kraj");
        address.setLocalNumber(50);
        addressDao.update(address);

        Address addressFromDB = addressDao.get(ADDRESS_ID);

        assertEquals(address, addressFromDB);
    }
}