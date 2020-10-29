package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.Dao;
import com.codecool.shop.dao.ModifyDao;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductInCart;
import com.codecool.shop.model.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class CartDaoJdbc implements ModifyDao<Cart> {
    private final DataSource dataSource;
    private final Dao<User> userDao;
    private final Dao<Product> productDao;
    private final CartContentJdbc cartContentJdbc = new CartContentJdbc();

    public CartDaoJdbc(DataSource dataSource, Dao<User> userDao, Dao<Product> productDao) {
        this.dataSource = dataSource;
        this.userDao = userDao;
        this.productDao = productDao;
    }

    public void increaseQuantityOfProduct(ProductInCart productInCart, int cartId) {
    }

    /**
     * Private class inside CartDaoJdbc to get Cart Content everytime it is necessary
     */
    private class CartContentJdbc {
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
                throw new RuntimeException("Error while adding new products to cart.", throwable);
            }
        }

        public void remove(int cartId) {
            try (Connection conn = dataSource.getConnection()) {
                String sql = "DELETE FROM cart_content WHERE cart_id = ?";
                PreparedStatement st = conn.prepareStatement(sql);
                st.setInt(1, cartId);
                st.executeUpdate();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        public Map<Product, Integer> getAllProducts(int cartId) {
            try (Connection conn = dataSource.getConnection()) {
                String sql = "SELECT product_id, quantity FROM cart_content WHERE cart_id = ?";
                PreparedStatement st = conn.prepareStatement(sql);
                st.setInt(1, cartId);
                ResultSet rs = st.executeQuery();

                Map<Product, Integer> cartContent = new HashMap<>();
                while (rs.next()) {
                    Product product = productDao.get(rs.getInt(1));
                    product.setId(rs.getInt(1));

                    cartContent.put(product, rs.getInt(2));
                }

                return cartContent;

            } catch (SQLException e) {
                throw new RuntimeException("Error while reading products belongs to cart of id " + cartId, e);
            }
        }

    }

    /**
     * {@inheritDoc}
     *
     * @param cart - cart instance with defined all fields without id
     */
    @Override
    public int add(Cart cart) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO cart VALUES (DEFAULT, ?)";
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, cart.getUser().getId());
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            rs.next();
            cart.setId(rs.getInt(1));
            cartContentJdbc.add(cart.getProductList(), cart.getId());
            return rs.getInt(1);

        } catch (SQLException throwable) {
            throw new RuntimeException("Error while adding new cart.", throwable);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param cart - cart instance with defined all fields
     */
    @Override
    public void update(Cart cart) {
        cartContentJdbc.remove(cart.getId());
        cartContentJdbc.add(cart.getProductList(), cart.getId());
    }

    /**
     * {@inheritDoc}
     *
     * @param id - id of cart instance to remove from database
     */
    @Override
    public void remove(int id) {
        cartContentJdbc.remove(id);

        try (Connection conn = dataSource.getConnection()) {
            String sql = "DELETE FROM cart WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            st.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param id - id of cart instance to get from database
     */
    @Override
    public Cart get(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT user_id FROM cart WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                return null;
            }

            Cart cart = new Cart(userDao.get(rs.getInt(1)));
            cart.setId(id);
            cart.setProductList(cartContentJdbc.getAllProducts(id));

            return cart;

        } catch (SQLException e) {
            throw new RuntimeException("Error while reading cart with id: " + id, e);
        }
    }

    public void updateProductInCart(ProductInCart productInCart, int cartId, String action) {
        try (Connection connection = dataSource.getConnection()) {

            String sqlUpdateQuery;
            if (action.toUpperCase().equals("ADD")) {
                sqlUpdateQuery = "UPDATE cart_content SET quantity = quantity + ? WHERE product_id = ? AND cart_id = ?; ";
            } else {
                sqlUpdateQuery = "UPDATE cart_content SET quantity = ? WHERE product_id = ? AND cart_id = ?; ";
            }

            PreparedStatement updateStatement = connection.prepareStatement(sqlUpdateQuery);

            updateStatement.setInt(1, productInCart.getQuantity());
            updateStatement.setInt(2, productInCart.getProductId());
            updateStatement.setInt(3, cartId);
            updateStatement.executeUpdate();

            String sqlInsertQuery = "INSERT INTO cart_content (product_id, cart_id, quantity)" +
                    "    SELECT ?, ?, ? WHERE NOT EXISTS (SELECT 1 FROM cart_content WHERE product_id = ? AND cart_id = ?);";

            PreparedStatement insertStatement = connection.prepareStatement(sqlInsertQuery);
            insertStatement.setInt(1, productInCart.getProductId());
            insertStatement.setInt(2, cartId);
            insertStatement.setInt(3, productInCart.getQuantity());
            insertStatement.setInt(4, productInCart.getProductId());
            insertStatement.setInt(5, cartId);
            insertStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error while updating a Product.", e);
        }
    }
}
