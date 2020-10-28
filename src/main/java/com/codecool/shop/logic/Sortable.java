package com.codecool.shop.logic;

import java.util.List;

public interface Sortable<T> extends BusinessLogic<T> {
    List<T> getAllElements(String sortType, String sortBy);
}
