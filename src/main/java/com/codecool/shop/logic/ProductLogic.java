package com.codecool.shop.logic;

import com.codecool.shop.logic.enumerators.SortType;
import com.codecool.shop.model.Product;

import java.util.List;

public class ProductLogic extends BusinessLogic {

    public static Product getElementFromDatabase() {
        // TODO
        throw new RuntimeException("Not implemented yet!");
    }

    public static void setProductInDatabase(Product product) {
        // TODO
        throw new RuntimeException("Not implemented yet!");
    }

    public static List<Product> getAllProductsFromDatabase(SortType sortType, String element) {
        // TODO
        //  element -> name of supplier or name of category

        switch (sortType) {
            case SUPPLIER:
                break;
            case CATEGORY:
                break;
            default:
                break;
        }

        throw new RuntimeException("Not implemented yet!");
    }

    public static void setAllProductsInDatabase(List<Product> products) {
        // TODO
        throw new RuntimeException("Not implemented yet!");
    }

}
