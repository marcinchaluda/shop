package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.DataSourceFactory;
import com.codecool.shop.model.Address;
import org.junit.jupiter.api.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class AddressDaoJdbcTest {

    private static DataSource dataSource;
    private static AddressDaoJdbc addressDao;
    final int ADDRESS_ID = 1;

    @BeforeAll
    public static void prepare() {
        dataSource = DataSourceFactory.getPostgreSQLTestDataSource();
        addressDao = new AddressDaoJdbc(dataSource);
    }

    @BeforeEach
    public void clearTable() {
        clearAddressTableToDefaultState();
    }

    @Order(1)
    @Test
    public void should_addressInsertedIntoDatabaseEqualsThatFromDatabase_when_addAddressToDatabase() {
        Address address = new Address("Poland", "Warsaw", "31-476", "Hollywood", 115);
        addressDao.add(address);

        Address addressFromDB = addressDao.get(ADDRESS_ID);
        addressFromDB.setId(ADDRESS_ID);

        assertEquals(address, addressFromDB);
    }

    @Order(2)
    @Test
    public void should_updatedAddressEqualsThatFromDatabase_when_createdAddAndModifyAddress() {
        Address address = new Address("Poland", "Warsaw", "31-476", "Hollywood", 115);
        addressDao.add(address);

        address.setCountry("Tomaszowy Kraj");
        address.setLocalNumber(50);
        addressDao.update(address);

        Address addressFromDB = addressDao.get(ADDRESS_ID);
        addressFromDB.setId(ADDRESS_ID);

        assertEquals(address, addressFromDB);
    }

    @Order(3)
    @Test
    public void should_returnNull_when_gettingNotExistingAddress() {
        Address address = addressDao.get(ADDRESS_ID);

        assertNull(address);
    }

    @Order(4)
    @Test
    public void should_returnNull_when_gettingRemovedAddress() {
        Address address = new Address("Poland", "Warsaw", "31-476", "Hollywood", 115);
        addressDao.add(address);
        addressDao.remove(ADDRESS_ID);

        Address addressFromDB = addressDao.get(ADDRESS_ID);

        assertNull(addressFromDB);
    }

    @Order(5)
    @Test
    public void should_returnNull_when_updatingNotExistingAddressAndGettingItFromDatabase() {
        Address address = new Address("Poland", "Warsaw", "31-476", "Hollywood", 115);
        address.setId(ADDRESS_ID);

        addressDao.update(address);
        Address addressFromDB = addressDao.get(ADDRESS_ID);

        assertNull(addressFromDB);
    }

    @AfterAll
    public static void complete() {
        clearAddressTableToDefaultState();
    }

    private static void clearAddressTableToDefaultState() {
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "TRUNCATE TABLE address RESTART IDENTITY CASCADE;";
            connection.prepareStatement(sqlQuery).execute();
        } catch (SQLException error) {
            throw new RuntimeException("Error while clearing test table.", error);
        }
    }
}