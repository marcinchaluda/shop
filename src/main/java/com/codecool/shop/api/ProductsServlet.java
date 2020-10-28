package com.codecool.shop.api;

import com.codecool.shop.logic.ProductLogic;
import com.codecool.shop.model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/api/products/*"})
public class ProductsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductLogic productLogic = ProductLogic.getInstance();
        HelpServlet.sendRequestForAllElementsAndCheckSortAbility(request, response, productLogic);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ProductLogic productLogic = ProductLogic.getInstance();
        HelpServlet.createInstanceAndUpdateElement(request, response, productLogic, Product.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ProductLogic productLogic = ProductLogic.getInstance();
        HelpServlet.createInstanceAndAddElement(request, response, productLogic, Product.class);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ProductLogic productLogic = ProductLogic.getInstance();
        HelpServlet.createInstanceAndRemoveElement(request, response, productLogic, Product.class);
    }
}
