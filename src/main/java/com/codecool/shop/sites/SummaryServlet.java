package com.codecool.shop.sites;


import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.logic.OrderLogic;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.ProductInCart;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/summary/*"})
public class SummaryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendRedirect("/");
        } else {
            TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
            WebContext context = new WebContext(request, response, request.getServletContext());

            String[] splits = pathInfo.split("/");
            Order order = OrderLogic.getInstance().getElement(Integer.parseInt(splits[1]));
            context.setVariable("order", order);
            context.setVariable("products", order.getCart().getProducts());
            context.setVariable("paymentLink", "../payment/" + order.getCart().getId());
            context.setVariable("user", order.getCart().getUser());
            context.setVariable("space", " ");

            context.setVariable("totalPrice", order.getTotalPrice());

            engine.process("order/order-summary.html", context, response.getWriter());
        }
    }
}