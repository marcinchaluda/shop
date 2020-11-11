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
    private AddressDaoJdbc addressDao;
    final int ADDRESS_ID = 1;

    @BeforeAll
    public static void start() {
        System.out.println("Tested conditions and completed tasks:");
    }

    @BeforeEach
    public void prepare() {
        dataSource = DataSourceFactory.getPostgreSQLTestDataSource();
        addressDao = new AddressDaoJdbc(dataSource);
        clearAddressTable();
        System.out.println("-- Cleared Address table in test database --");
    }

    private static void clearAddressTable() {
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
        Address address = new Address("Poland", "Warsaw", "31-476", "Hollywood", 115);
        address.setId(ADDRESS_ID);
        addressDao.add(address);

        Address addressFromDB = addressDao.get(ADDRESS_ID);

        assertEquals(address, addressFromDB);
        System.out.println("1. Adding address to database.");
    }

    @Order(2)
    @Test
    public void should_updatedAddressEqualsThatFromDatabase_when_createdAddAndModifiedAddress() {
        Address address = new Address("Poland", "Warsaw", "31-476", "Hollywood", 115);
        address.setId(ADDRESS_ID);
        addressDao.add(address);

        address.setCountry("Tomaszowy Kraj");
        address.setLocalNumber(50);
        addressDao.update(address);

        Address addressFromDB = addressDao.get(ADDRESS_ID);

        assertEquals(address, addressFromDB);
        System.out.println("2. Updating address in database after inserting one.");
    }

    @Order(3)
    @Test
    public void should_returnNull_when_gettingNotExistingAddress() {
        Address address = addressDao.get(ADDRESS_ID);

        assertNull(address);
        System.out.println("3. Getting never existing address.");
    }

    @Order(4)
    @Test
    public void should_returnNull_when_gettingRemovedAddress() {
        Address address = new Address("Poland", "Warsaw", "31-476", "Hollywood", 115);
        address.setId(ADDRESS_ID);
        addressDao.add(address);
        addressDao.remove(ADDRESS_ID);

        Address addressFromDB = addressDao.get(ADDRESS_ID);

        assertNull(addressFromDB);
        System.out.println("4. Getting removed address.");
    }

    @Order(5)
    @Test
    public void should_returnNull_when_updatingNotExistingAddressAndGettingItFromDatabase() {
        Address address = new Address("Poland", "Warsaw", "31-476", "Hollywood", 115);
        address.setId(ADDRESS_ID);

        addressDao.update(address);
        Address addressFromDB = addressDao.get(ADDRESS_ID);

        assertNull(addressFromDB);
        System.out.println("5. Updating not existing address and getting it from database.");
    }

    @AfterAll
    public static void complete() {
        System.out.println("Completing testing, restoring default settings in database.");
        clearAddressTable();
        System.out.println("-- Cleared Address table in test database --");
    }
}