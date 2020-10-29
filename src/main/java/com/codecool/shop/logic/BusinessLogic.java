package com.codecool.shop.logic;

public interface BusinessLogic<T> {
    int addElement(T element);
    void updateElement(T element, int id);
    void removeElement(T element);
    T getElement(int id);
}
