package com.codecool.shop.api;

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

@WebServlet(urlPatterns = {"/api/products/*"})
public class ProductsServlet extends HttpServlet {

    ProductLogic productLogic = new ProductLogic();
    final int MODEL_ID_INDEX = 1;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = HelpServlet.setResponse(response);
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")){
            responseWithAllElements(request, out);
            return;
        }

        String[] splits = HelpServlet.uriGotOneIdAndIsCorrect(response, pathInfo);
        int productId = HelpServlet.parseIdToInt(splits[MODEL_ID_INDEX]);
        responseWithOneElement(productId, out);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

        HelpServlet.uriGotOneIdAndIsCorrect(response, pathInfo);
        String json = request.getParameter("jsondata");
        productLogic.updateElement(new Gson().fromJson(json, Product.class));
        response.setStatus(200);
    }

    private void responseWithAllElements(HttpServletRequest request, PrintWriter out) {
        String sortType = HelpServlet.getParameterIfExist(request, "sort", "default");
        String sortBy = HelpServlet.getParameterIfExist(request, "by", "default");

        if (!sortType.equals("default") && !sortBy.equals("default")) {
            Arrays.stream(SortType.values()).forEach(type -> {
                if (type.getName().equals(sortType)) {
                    List<Product> products = productLogic.getAllFromDatabase(type, sortBy);
                    out.print(new Gson().toJson(products));
                }
            });
        } else {
            List<Product> products = productLogic.getAllElements();
            out.print(new Gson().toJson(products));
        }
        out.flush();
    }

    private void responseWithOneElement(int productId, PrintWriter out) {
        Product product = productLogic.getElement(productId);
        out.print(new Gson().toJson(product));
        out.flush();
    }
}
