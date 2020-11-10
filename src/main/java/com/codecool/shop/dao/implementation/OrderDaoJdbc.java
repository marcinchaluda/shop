package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.Dao;
import com.codecool.shop.dao.ModifyDao;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Order;

import javax.sql.DataSource;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoJdbc implements ModifyDao<Order> {
    private final DataSource dataSource;
    private final Dao<Cart> cartDao;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public OrderDaoJdbc(DataSource dataSource, Dao<Cart> cartDao) {
        this.dataSource = dataSource;
        this.cartDao = cartDao;
    }

    /**
     * {@inheritDoc}
     * @param order - order instance with defined all fields without id
     */
    @Override
    public int add(Order order) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO user_order VALUES (DEFAULT, ?, ?)";
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, order.getCart().getId());
            st.setBoolean(2, order.isPaid());
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            rs.next();
            order.setId(rs.getInt(1));
            return rs.getInt(1);
        } catch (SQLException throwable) {
            throw new RuntimeException("Error while adding new cart.", throwable);
        }
    }

    /**
     * {@inheritDoc}
     * @param order - order instance with defined all fields
     */
    @Override
    public void update(Order order) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "UPDATE user_order SET cart_id = ?, paid = ? WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, order.getCart_id());
            st.setBoolean(2, order.isPaid());
            st.setInt(3, order.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     * @param id - id of order instance to remove from database
     */
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

    /**
     * {@inheritDoc}
     * @param id - id of order instance to get from database
     */
    @Override
    public Order get(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT cart_id, paid, date FROM user_order WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                return null;
            }

            Cart cart = cartDao.get(rs.getInt("cart_id"));
            Order order = new Order(cart);
            order.setId(id);
            order.setPaid(rs.getBoolean("paid"));
            order.setDate(formatter.format(rs.getTimestamp(3)));
            return order;

        } catch (SQLException e) {
            throw new RuntimeException("Error while reading order with id: " + id, e);
        }
    }

    public List<Order> getOrdersByUserId(int userId) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT user_order.id FROM user_order\n" +
                         "LEFT JOIN cart on cart.id = user_order.cart_id\n" +
                         "LEFT JOIN user_account on user_account.id = cart.user_id\n" +
                         "WHERE user_account.id = ?";

            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                return null;
            }
            List<Order> orderList = new ArrayList<>();

            do  {
                orderList.add(get(rs.getInt(1)));
            } while (rs.next());

            return orderList;

        } catch (SQLException e) {
            throw new RuntimeException("Error while reading orders belongs to user of id: " + userId, e);
        }
    }
}
