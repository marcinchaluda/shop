package com.codecool.shop.servlets;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.logic.enumerators.Category;
import com.codecool.shop.logic.enumerators.Supplier;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {"/"})
public class IndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        WebContext context = new WebContext(request, response, request.getServletContext());

        Map<String, Object> params = new HashMap<>();
        params.put("supplier", Supplier.values());
        params.put("category", Category.values());
        params.put("products", );
        context.setVariables(params);

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        engine.process("product/index.html", context, response.getWriter());
    }
}

//        ProductDao productDataStore = ProductDaoMem.getInstance();
//        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
//
//        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
//        WebContext context = new WebContext(req, resp, req.getServletContext());
//        context.setVariable("category", productCategoryDataStore.find(1));
//        context.setVariable("products", productDataStore.getBy(productCategoryDataStore.find(1)));
//        // // Alternative setting of the template context
//        // Map<String, Object> params = new HashMap<>();
//        // params.put("category", productCategoryDataStore.find(1));
//        // params.put("products", productDataStore.getBy(productCategoryDataStore.find(1)));
//        // context.setVariables(params);
//        engine.process("product/index.html", context, resp.getWriter());
