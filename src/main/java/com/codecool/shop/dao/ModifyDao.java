package com.codecool.shop.dao;

public interface ModifyDao<T> extends Dao<T> {

    /**
     * DAO's method that update element in database
     */
    void update(T thing);

    /**
     * DAO's method that remove element from database
     */
    void remove(int id);
}
