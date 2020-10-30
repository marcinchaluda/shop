package com.codecool.shop.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart extends BaseModel {

    @SerializedName(value="user")
    private User user;

    transient private Map<Product, Integer> productList = new HashMap<>();

    @SerializedName(value="products")
    List<ProductInCart> products = new ArrayList<>();

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
        for (Map.Entry<Product, Integer> entry : productList.entrySet()) {
            products.add(new ProductInCart(entry.getKey().getName(), entry.getKey(), entry.getValue()));
        }
    }

    public List<ProductInCart> getProducts() {
        return products;
    }

    public void setProducts(List<ProductInCart> products) {
        this.products = products;
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
