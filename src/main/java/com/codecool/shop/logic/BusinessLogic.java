package com.codecool.shop.logic;

import java.util.List;

public interface BusinessLogic<T> {
    void addElement(T element);
    void updateElement(T element);
    T getElement(int id);
    List<T> getAllElements();
}
