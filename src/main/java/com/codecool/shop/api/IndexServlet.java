package com.codecool.shop.api;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.logic.enumerators.Category;
import com.codecool.shop.logic.enumerators.Supplier;
import com.codecool.shop.model.Product;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/"})
public class IndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        WebContext context = new WebContext(request, response, request.getServletContext());

        List<Product> sampleProducts = Arrays.asList(
                new Product("Amazon Fire", 49.9f, "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", Category.TABLET, Supplier.AMAZON),
                new Product("Lenovo IdeaPad Mix 700", 479, "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", Category.TABLET, Supplier.LENOVO),
                new Product("Amazon Fire HD 8", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", Category.TABLET, Supplier.AMAZON)
        );

        // List<Product> products = ProductLogic.getAllProductsFromDatabase("default", "default");

        Map<String, Object> params = new HashMap<>();
        params.put("suppliers", Supplier.values());
        params.put("categories", Category.values());
        params.put("products", sampleProducts);
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
