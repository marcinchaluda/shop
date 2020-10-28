package com.codecool.shop.logic;

import java.util.List;

public interface NotSortable<T> extends BusinessLogic<T> {
    List<T> getAllElements();
}
