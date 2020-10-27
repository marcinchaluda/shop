package com.codecool.shop.dao;

import java.util.List;

public interface Dao<T> {
    void add(T thing);
    void update(T thing);
    void remove(int id);
    T get(int id);
    List<T> getAll();
}
