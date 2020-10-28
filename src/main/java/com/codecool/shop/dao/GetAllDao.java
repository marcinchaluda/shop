package com.codecool.shop.dao;

import java.util.List;

public interface GetAllDao<T> extends ModifyDao<T> {

    /**
     * DAO's method that get list of elements from database
     */
    List<T> getAll();
}
