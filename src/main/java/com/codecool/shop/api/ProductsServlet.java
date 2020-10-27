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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final int MODEL_ID_INDEX = 1;

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String pathInfo = request.getPathInfo();
        System.out.println(pathInfo);

        if (pathInfo == null || pathInfo.equals("/")){
            responseWithAllElements(request, out);
            return;
        }

        String[] splits = pathInfo.split("/");

        if(splits.length != 2) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            int modelId = Integer.parseInt(splits[MODEL_ID_INDEX]);
            responseWithOneElement(modelId, out);
        } catch (NumberFormatException e) {
            throw new ServletException("Id parameter is not valid.", e);
        }
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

    private void responseWithOneElement(int modelId, PrintWriter out) {
        Product product = productLogic.getElement(modelId);
        out.print(new Gson().toJson(product));
        out.flush();
    }
}
