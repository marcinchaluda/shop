package com.codecool.shop.logic;

import java.util.List;

public interface BusinessLogic<T> {
    void addToDatabase(T element);
    void updateInDatabase(T element);
    T getFromDatabase(int id);
    List<T> getAllFromDatabase();
}
