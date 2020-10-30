package com.codecool.shop.dao;

public interface Dao<T> {

    /**
     * DAO's method that add element to database
     */
    int add(T thing);

    /**
     * DAO's method that get element from database
     */
    T get(int id);
}
