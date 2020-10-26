package com.codecool.shop.model;

import java.util.HashMap;
import java.util.Map;

public class Cart extends BaseModel {
    private User user;
    private Map<Product, Integer> productList = new HashMap<>();

    public Cart(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Map<Product, Integer> getProductList() {
        return productList;
    }

    public void setProductList(Map<Product, Integer> productList) {
        this.productList = productList;
    }

    @Override
    public String toString() {
        StringBuilder cartContent = new StringBuilder("Products in bucket:\n");
        for (Product product : productList.keySet()) {
            cartContent.append(String.format("%s - %d\n", product.getName(), productList.get(product)));
        }

        return String.format("id: %1$d, " +
                        "user: %2$s, " +
                        cartContent,
                this.id,
                this.user.getName()
        );
    }
}
