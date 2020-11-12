package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.DataSourceFactory;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OrderDaoJdbcTest {
    private static DataSource dataSource;
    private static OrderDaoJdbc orderDao;
    private static CartDaoJdbc cartDao;
    final int ORDER_ID = 1;

    @BeforeAll
    public static void prepare() {
        dataSource = DataSourceFactory.getPostgreSQLTestDataSource();
        cartDao = mock(CartDaoJdbc.class);
        orderDao = new OrderDaoJdbc(dataSource, cartDao);
        when(cartDao.get(1))
                .thenReturn(new Cart(new User("", "", "", null, null, null)));
    }

    @BeforeEach
    public void clearTable() {
        dropTable();
        createTable();
    }

    @org.junit.jupiter.api.Order(1)
    @Test
    public void should_orderInsertedIntoDatabaseEqualsThatFromDatabase_when_addOrderToDatabase() {
        Cart cart = cartDao.get(1);
        cart.setId(1);
        Order order = new Order(cart);
        orderDao.add(order);

        Order orderFromDB = orderDao.get(ORDER_ID);

        assertEquals(order, orderFromDB);
    }

    @org.junit.jupiter.api.Order(2)
    @Test
    public void should_updatedOrderEqualsThatFromDatabase_when_createdAddAndModifyOrder() {
        Cart cart = cartDao.get(1);
        cart.setId(1);
        Order order = new Order(cart);
        orderDao.add(order);

        order.setPaid(true);
        orderDao.update(order);
        Order orderFromDB = orderDao.get(ORDER_ID);

        assertEquals(order, orderFromDB);
    }

    @org.junit.jupiter.api.Order(3)
    @Test
    public void should_returnNull_when_gettingNotExistingOrder() {
        Order orderFromDB = orderDao.get(ORDER_ID);

        assertNull(orderFromDB);
    }

    @org.junit.jupiter.api.Order(4)
    @Test
    public void should_returnNull_when_gettingRemovedOrder() {
        Cart cart = cartDao.get(1);
        cart.setId(1);
        Order order = new Order(cart);
        orderDao.add(order);
        orderDao.remove(ORDER_ID);

        Order orderFromDB = orderDao.get(ORDER_ID);

        assertNull(orderFromDB);
    }

    @org.junit.jupiter.api.Order(5)
    @Test
    public void should_returnNull_when_updatingNotExistingOrderAndGettingItFromDatabase() {
        Cart cart = cartDao.get(1);
        cart.setId(1);
        Order order = new Order(cart);

        orderDao.update(order);
        Order orderFromDB = orderDao.get(ORDER_ID);

        assertNull(orderFromDB);
    }

    @org.junit.jupiter.api.Order(6)
    @Test
    public void should_returnListOfOrdersOfSize3_when_getAllOrdersOfUserAfterAdding3() {
        Cart cart = cartDao.get(1);
        createCartTable();
        addCartsToTestDatabase(cart);

        orderDao.add(new Order(cart));
        orderDao.add(new Order(cart));
        orderDao.add(new Order(cart));

        List<Order> orders = orderDao.getOrdersByUserId(1);

        assertEquals(3, orders.size());
    }

    private static void addCartsToTestDatabase(Cart cart) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO cart VALUES (DEFAULT, ?, ?)";
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, 1);
            st.setBoolean(2, false);
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            rs.next();
            cart.setId(rs.getInt(1));
        } catch (SQLException throwable) {
            throw new RuntimeException("Error while adding new cart.", throwable);
        }
    }

    @org.junit.jupiter.api.Order(7)
    @Test
    public void should_throwRuntimeException_when_addingOrderNotExistingTable() {
        Cart cart = cartDao.get(1);
        cart.setId(1);
        Order order = new Order(cart);
        dropTable();

        Executable executable = () -> orderDao.add(order);

        assertThrows(RuntimeException.class, executable);
    }

    @org.junit.jupiter.api.Order(8)
    @Test
    public void should_throwRuntimeException_when_updatingOrderNotExistingTable() {
        Cart cart = cartDao.get(1);
        cart.setId(1);
        Order order = new Order(cart);
        order.setId(ORDER_ID);
        dropTable();

        Executable executable = () -> orderDao.update(order);

        assertThrows(RuntimeException.class, executable);
    }

    @org.junit.jupiter.api.Order(9)
    @Test
    public void should_throwRuntimeException_when_removingOrderNotExistingTable() {
        dropTable();

        Executable executable = () -> orderDao.remove(ORDER_ID);

        assertThrows(RuntimeException.class, executable);
    }

    @org.junit.jupiter.api.Order(10)
    @Test
    public void should_throwRuntimeException_when_gettingOrderNotExistingTable() {
        dropTable();

        Executable executable = () -> orderDao.get(ORDER_ID);

        assertThrows(RuntimeException.class, executable);
    }

    @org.junit.jupiter.api.Order(11)
    @Test
    public void should_returnEmptyList_when_getAllOrdersOfUserAfterAdding3() {
        Cart cart = cartDao.get(1);
        createCartTable();
        orderDao.add(new Order(cart));
        orderDao.add(new Order(cart));
        orderDao.add(new Order(cart));

        List<Order> orders = orderDao.getOrdersByUserId(1);

        assertEquals(0, orders.size());
    }

    @org.junit.jupiter.api.Order(12)
    @Test
    public void should_throwRuntimeException_when_getAllOrdersOfUserAfterAdding3WhenTableNotExist() {
        Cart cart = cartDao.get(1);
        createCartTable();
        orderDao.add(new Order(cart));
        orderDao.add(new Order(cart));
        orderDao.add(new Order(cart));
        dropTable();

        Executable executable = () -> orderDao.getOrdersByUserId(1);

        assertThrows(RuntimeException.class, executable);
    }

    @AfterAll
    public static void complete() {
        dropTable();
        dropCartTable();
    }

    private static void createTable() {
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "CREATE TABLE IF NOT EXISTS user_order (" +
                    "\"id\"      serial PRIMARY KEY," +
                    "\"cart_id\" integer NOT NULL," +
                    "\"paid\"    boolean NOT NULL," +
                    "\"date\"    TIMESTAMPTZ DEFAULT now()" +
                    ");";
            connection.prepareStatement(sqlQuery).execute();
        } catch (SQLException error) {
            throw new RuntimeException("Error while creating test table.", error);
        }
    }

    private static void dropTable() {
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "DROP TABLE IF EXISTS user_order CASCADE;";
            connection.prepareStatement(sqlQuery).execute();
        } catch (SQLException error) {
            throw new RuntimeException("Error while deleting test table.", error);
        }
    }

    private static void createCartTable() {
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "CREATE TABLE IF NOT EXISTS cart (" +
                    "\"id\"      serial PRIMARY KEY,"+
                    "\"user_id\" integer NOT NULL,"+
                    "\"disabled\"boolean NOT NULL"+
                    ");";
            connection.prepareStatement(sqlQuery).execute();
        } catch (SQLException error) {
            throw new RuntimeException("Error while creating test table.", error);
        }
    }

    private static void dropCartTable() {
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "DROP TABLE IF EXISTS cart CASCADE;";
            connection.prepareStatement(sqlQuery).execute();
        } catch (SQLException error) {
            throw new RuntimeException("Error while deleting test table.", error);
        }
    }
}