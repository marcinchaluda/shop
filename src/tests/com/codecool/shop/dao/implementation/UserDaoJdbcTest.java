package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.DataSourceFactory;
import com.codecool.shop.model.Address;
import com.codecool.shop.model.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserDaoJdbcTest {
    private static DataSource dataSource;
    private static UserDaoJdbc userDao;
    private static AddressDaoJdbc addressDao;
    final int USER_ID = 1;

    @BeforeAll
    public static void prepare() {
        dataSource = DataSourceFactory.getPostgreSQLTestDataSource();
        addressDao = mock(AddressDaoJdbc.class);
        userDao = new UserDaoJdbc(dataSource, addressDao);
    }

    @BeforeEach
    public void clearTable() {
        dropTable();
        createTable();
    }

    @Order(1)
    @Test
    public void should_userInsertedIntoDatabaseEqualsThatFromDatabase_when_addUserToDatabase() {
        User user = new User("Name", "email@email.com", "password", null, null, null);

        userDao.add(user);
        User userFromDB = userDao.get(USER_ID);

        assertEquals(user, userFromDB);
    }

    @Order(2)
    @Test
    public void should_updatedUserEqualsThatFromDatabase_when_createdAddAndModifyUser() {
        when(addressDao.get(1))
                .thenReturn(new Address("Poland", "Warsaw", "31-476", "Hollywood", 115));
        when(addressDao.get(2))
                .thenReturn(new Address("Poland", "Cracow", "22-476", "Bangladesh", 77));
        Address address1 = addressDao.get(1);
        Address address2 = addressDao.get(2);
        User user = new User("Name", "email@email.com", "password", "", address1, address2);
        userDao.add(user);

        user.setEmail("newEmail");
        user.setPassword("newPassword");
        userDao.update(user);

        User userFromDB = userDao.get(USER_ID);

        assertEquals(user, userFromDB);
    }

    @Order(3)
    @Test
    public void should_returnNull_when_gettingNotExistingUser() {
        User user = userDao.get(USER_ID);

        assertNull(user);
    }

    @Order(4)
    @Test
    public void should_returnNull_when_gettingRemovedUser() {
        when(addressDao.get(1))
                .thenReturn(new Address("Poland", "Warsaw", "31-476", "Hollywood", 115));
        when(addressDao.get(2))
                .thenReturn(new Address("Poland", "Cracow", "22-476", "Bangladesh", 77));
        Address address1 = addressDao.get(1);
        Address address2 = addressDao.get(2);
        User user = new User("Name", "email@email.com", "password", "", address1, address2);
        userDao.add(user);
        userDao.remove(USER_ID);

        User userFromDB = userDao.get(USER_ID);

        assertNull(userFromDB);
    }

    @Order(5)
    @Test
    public void should_returnNull_when_updatingNotExistingUserAndGettingItFromDatabase() {
        when(addressDao.get(1))
                .thenReturn(new Address("Poland", "Warsaw", "31-476", "Hollywood", 115));
        when(addressDao.get(2))
                .thenReturn(new Address("Poland", "Cracow", "22-476", "Bangladesh", 77));
        Address address1 = addressDao.get(1);
        Address address2 = addressDao.get(2);
        User user = new User("Name", "email@email.com", "password", "", address1, address2);

        userDao.update(user);
        User userFromDB = userDao.get(USER_ID);

        assertNull(userFromDB);
    }

    @Order(6)
    @Test
    public void should_returnListOfUsersOfSize3_when_getAllUsersAfterAdding3() {
        userDao.add(new User("Name", "email@email.com", "password", null, null, null));
        userDao.add(new User("Name2", "email@email.com", "password", null, null, null));
        userDao.add(new User("Name3", "email@email.com", "password", null, null, null));

        List<User> users = userDao.getAll();

        assertEquals(3, users.size());
    }

    @Order(7)
    @Test
    public void should_throwRuntimeException_when_addingUserNotExistingTable() {
        when(addressDao.get(1))
                .thenReturn(new Address("Poland", "Warsaw", "31-476", "Hollywood", 115));
        when(addressDao.get(2))
                .thenReturn(new Address("Poland", "Cracow", "22-476", "Bangladesh", 77));
        Address address1 = addressDao.get(1);
        Address address2 = addressDao.get(2);
        User user = new User("Name", "email@email.com", "password", "", address1, address2);
        dropTable();

        Executable executable = () -> userDao.add(user);

        assertThrows(RuntimeException.class, executable);
    }

    @Order(8)
    @Test
    public void should_throwRuntimeException_when_updatingUserNotExistingTable() {
        when(addressDao.get(1))
                .thenReturn(new Address("Poland", "Warsaw", "31-476", "Hollywood", 115));
        when(addressDao.get(2))
                .thenReturn(new Address("Poland", "Cracow", "22-476", "Bangladesh", 77));
        Address address1 = addressDao.get(1);
        Address address2 = addressDao.get(2);
        User user = new User("Name", "email@email.com", "password", "", address1, address2);
        user.setId(USER_ID);
        dropTable();

        Executable executable = () -> userDao.update(user);

        assertThrows(RuntimeException.class, executable);
    }

    @Order(9)
    @Test
    public void should_throwRuntimeException_when_removingUserNotExistingTable() {
        dropTable();

        Executable executable = () -> userDao.remove(USER_ID);

        assertThrows(RuntimeException.class, executable);
    }

    @Order(10)
    @Test
    public void should_throwRuntimeException_when_gettingUserNotExistingTable() {
        dropTable();

        Executable executable = () -> userDao.get(USER_ID);

        assertThrows(RuntimeException.class, executable);
    }

    @Order(11)
    @Test
    public void should_returnFalse_when_userDontExistInDatabase() {
        User user = new User("Name", "email@email.com", "password", null, null, null);

        boolean userExistResult = userDao.isExist(user);

        assertFalse(userExistResult);
    }

    @Order(12)
    @Test
    public void should_throwRuntimeException_when_checkingForUserInNotExistingTable() {
        dropTable();
        User user = new User("Name", "email@email.com", "password", null, null, null);

        Executable executable = () -> userDao.isExist(user);

        assertThrows(RuntimeException.class, executable);
    }

    @Order(13)
    @Test
    public void should_throwRuntimeException_when_gettingAllUsersInNotExistingTable() {
        dropTable();

        Executable executable = () -> userDao.getAll();

        assertThrows(RuntimeException.class, executable);
    }

    @AfterAll
    public static void complete() {
        dropTable();
    }

    private static void createTable() {
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "CREATE TABLE IF NOT EXISTS user_account (" +
                    "\"id\"               serial PRIMARY KEY," +
                    "\"full_name\"        text NOT NULL," +
                    "\"email\"            text NOT NULL," +
                    "\"password\"         text NOT NULL," +
                    "\"phone_number\"     text," +
                    "\"billing_address\"  integer," +
                    "\"shipping_address\" integer" +
                    ");";
            connection.prepareStatement(sqlQuery).execute();
        } catch (SQLException error) {
            throw new RuntimeException("Error while creating test table.", error);
        }
    }

    private static void dropTable() {
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "DROP TABLE IF EXISTS user_account CASCADE;";
            connection.prepareStatement(sqlQuery).execute();
        } catch (SQLException error) {
            throw new RuntimeException("Error while deleting test table.", error);
        }
    }
}