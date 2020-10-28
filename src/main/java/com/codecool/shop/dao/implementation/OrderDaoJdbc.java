package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.Dao;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Order;

import javax.sql.DataSource;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class OrderDaoJdbc implements Dao<Order> {
    private final DataSource dataSource;
    private final Dao<Cart> cartDao;

    public OrderDaoJdbc(DataSource dataSource, Dao<Cart> cartDao) {
        this.dataSource = dataSource;
        this.cartDao = cartDao;
    }

    @Override
    public void add(Order order) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO user_order VALUES (DEFAULT, ?, ?)";
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, order.getCart().getId());
            st.setBoolean(2, order.isPaid());
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            rs.next();
            order.setId(rs.getInt(1));

        } catch (SQLException throwable) {
            throw new RuntimeException("Error while adding new cart.", throwable);
        }
    }

    @Override
    public void update(Order order) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "UPDATE user_order SET cart_id = ?, paid = ? WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, order.getCart().getId());
            st.setBoolean(2, order.isPaid());
            st.setInt(3, order.getId());
            st.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "DELETE FROM user_order WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            st.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Order get(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT cart_id, paid FROM user_order WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                return null;
            }

            Cart cart = cartDao.get(rs.getInt(1));
            Order order = new Order(cart);
            order.setId(id);
            order.setPaid(rs.getBoolean(2));

            return order;

        } catch (SQLException e) {
            throw new RuntimeException("Error while reading order with id: " + id, e);
        }
    }

    @Override
    public List<Order> getAll() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id FROM user_order";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            List<Order> orderList = new LinkedList<>();

            while (rs.next()) {
                orderList.add(get(rs.getInt(1)));
            }

            return orderList;

        } catch (SQLException e) {
            throw new RuntimeException("Error while reading all orders", e);
        }
    }
}
