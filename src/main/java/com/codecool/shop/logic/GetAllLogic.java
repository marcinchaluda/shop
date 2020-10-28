package com.codecool.shop.logic;

import java.util.List;

public interface GetAllLogic<T> extends BusinessLogic<T> {
    List<T> getAllElements();
}
