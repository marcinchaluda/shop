package com.codecool.shop.servlets;

import com.codecool.shop.logic.ProductLogic;
import com.codecool.shop.logic.enumerators.SortType;
import com.codecool.shop.model.Product;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

@WebServlet(urlPatterns = {"/api/products"})
public class ProductsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String sortType = HelpServlet.getParameterIfExist(request, "sort", "default");
        String sortBy = HelpServlet.getParameterIfExist(request, "by", "default");

        if (!sortType.equals("default") && !sortBy.equals("default")) {
            Arrays.stream(SortType.values()).forEach(type -> {
                if (type.getName().equals(sortType)) {
                    List<Product> products = ProductLogic.getAllProductsFromDatabase(type, sortBy);
                    out.print(new Gson().toJson(products));
                }
            });
        } else {
            List<Product> products = ProductLogic.getAllProductsFromDatabase(SortType.ALL, sortBy);
            out.print(new Gson().toJson(products));
        }
        out.flush();
    }
}
