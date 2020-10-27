package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.Dao;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Map;

public class CartDaoJdbc implements Dao<Cart> {
    private final DataSource dataSource;
    private final Dao<User> userDao;
    private final Dao<Product> productDao;
    private final CartContentJdbc cartContentJdbc = new CartContentJdbc();

    public CartDaoJdbc(DataSource dataSource, Dao<User> userDao, Dao<Product> productDao) {
        this.dataSource = dataSource;
        this.userDao = userDao;
        this.productDao = productDao;
    }

    class CartContentJdbc {
        public void add(Map<Product, Integer> productInCart, int cartId) {
            try (Connection conn = dataSource.getConnection()) {
                for (Product product : productInCart.keySet()) {
                    String sql = "INSERT INTO cart_content VALUES (DEFAULT, ?, ?, ?)";
                    PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    st.setInt(1, product.getId());
                    st.setInt(2, cartId);
                    st.setInt(3, productInCart.get(product));

                    st.executeUpdate();
                    ResultSet rs = st.getGeneratedKeys();
                    rs.next();
                }

            } catch (SQLException throwable) {
                throw new RuntimeException("Error while adding new Product.", throwable);
            }
        }
    }

    @Override
    public void add(Cart cart) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO cart VALUES (DEFAULT, ?)";
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, cart.getUser().getId());
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            rs.next();
            cart.setId(rs.getInt(1));

            cartContentJdbc.add(cart.getProductList(), cart.getId());

        } catch (SQLException throwable) {
            throw new RuntimeException("Error while adding new Product.", throwable);
        }
    }

    @Override
    public void update(Cart cart) {

    }

    @Override
    public void remove(int id) {

    }

    @Override
    public Cart get(int id) {
        return null;
    }

    @Override
    public List<Cart> getAll() {
        return null;
    }
}
