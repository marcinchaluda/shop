package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.DataSourceFactory;
import com.codecool.shop.model.Address;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

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
        dropTable();
        createTable();
    }

    @Order(1)
    @Test
    public void should_addressInsertedIntoDatabaseEqualsThatFromDatabase_when_addAddressToDatabase() {
        Address address = new Address("Poland", "Warsaw", "31-476", "Hollywood", 115);
        addressDao.add(address);

        Address addressFromDB = addressDao.get(ADDRESS_ID);

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

        addressDao.update(address);
        Address addressFromDB = addressDao.get(ADDRESS_ID);

        assertNull(addressFromDB);
    }

    @Order(6)
    @Test
    public void should_throwRuntimeException_when_addingAddressNotExistingTable() {
        Address address = new Address("Poland", "Warsaw", "31-476", "Hollywood", 115);
        dropTable();

        Executable executable = () -> addressDao.add(address);

        assertThrows(RuntimeException.class, executable);
    }

    @Order(7)
    @Test
    public void should_throwRuntimeException_when_updatingAddressNotExistingTable() {
        Address address = new Address("Poland", "Warsaw", "31-476", "Hollywood", 115);
        address.setId(ADDRESS_ID);
        dropTable();

        Executable executable = () -> addressDao.update(address);

        assertThrows(RuntimeException.class, executable);
    }

    @Order(8)
    @Test
    public void should_throwRuntimeException_when_removingAddressNotExistingTable() {
        dropTable();

        Executable executable = () -> addressDao.remove(ADDRESS_ID);

        assertThrows(RuntimeException.class, executable);
    }

    @Order(9)
    @Test
    public void should_throwRuntimeException_when_gettingAddressNotExistingTable() {
        dropTable();

        Executable executable = () -> addressDao.get(ADDRESS_ID);

        assertThrows(RuntimeException.class, executable);
    }

    @AfterAll
    public static void complete() {
        dropTable();
    }

    private static void createTable() {
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "CREATE TABLE IF NOT EXISTS address (" +
                                    "\"id\"           serial PRIMARY KEY,\n" +
                                    " \"country\"      text NOT NULL,\n" +
                                    " \"city\"         text NOT NULL,\n" +
                                    " \"zip_code\"     text NOT NULL,\n" +
                                    " \"street\"       text NOT NULL,\n" +
                                    " \"local_number\" integer NOT NULL" +
                                ");";
            connection.prepareStatement(sqlQuery).execute();
        } catch (SQLException error) {
            throw new RuntimeException("Error while creating test table.", error);
        }
    }

    private static void dropTable() {
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "DROP TABLE IF EXISTS address CASCADE;";
            connection.prepareStatement(sqlQuery).execute();
        } catch (SQLException error) {
            throw new RuntimeException("Error while deleting test table.", error);
        }
    }
}