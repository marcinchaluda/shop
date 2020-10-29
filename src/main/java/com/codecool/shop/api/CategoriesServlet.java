package com.codecool.shop.api;

import com.codecool.shop.logic.CategoryLogic;
import com.codecool.shop.model.Category;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/api/categories/*"})
public class CategoriesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        CategoryLogic categoryLogic = CategoryLogic.getInstance();
        HelpServlet.sendRequestForAllElementsAndCheckSortAbility(request, response, categoryLogic);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CategoryLogic categoryLogic = CategoryLogic.getInstance();
        HelpServlet.createInstanceAndUpdateElement(request, response, categoryLogic, Category.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CategoryLogic categoryLogic = CategoryLogic.getInstance();
        HelpServlet.createInstanceAndAddElement(request, response, categoryLogic, Category.class);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CategoryLogic categoryLogic = CategoryLogic.getInstance();
        HelpServlet.createInstanceAndRemoveElement(request, response, categoryLogic, Category.class);
    }
}
