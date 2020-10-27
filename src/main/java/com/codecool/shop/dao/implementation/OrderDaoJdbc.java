package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.Dao;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Order;

import javax.sql.DataSource;
import java.sql.*;
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
    public void update(Order thing) {

    }

    @Override
    public void remove(int id) {

    }

    @Override
    public Order get(int id) {
        return null;
    }

    @Override
    public List<Order> getAll() {
        return null;
    }
}
