package com.codecool.shop.dao;

import java.util.List;

public interface SimpleInOutDao<T> {
    void add(T thing);
    T get(int id);
    List<T> getAll();
}
