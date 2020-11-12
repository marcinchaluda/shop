package com.codecool.shop.sites;


import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.logic.OrderLogic;
import com.codecool.shop.logic.SessionLogic;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.ProductInCart;
import com.codecool.shop.model.User;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"/summary/*"})
public class SummaryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || !pathInfo.contains("/") || session.getAttribute("user") == null) {
            response.sendRedirect("/login");
        } else {
            SessionLogic sessionLogic = SessionLogic.getInstance();
            String[] splits = pathInfo.split("/");
            User user = (User) session.getAttribute("user");
            if (sessionLogic.orderBelongsToUser(user.getId(), Integer.parseInt(splits[1]))) {
                TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
                WebContext context = new WebContext(request, response, request.getServletContext());

                Order order = OrderLogic.getInstance().getElement(Integer.parseInt(splits[1]));
                context.setVariable("order", order);
                context.setVariable("products", order.getCart().getProducts());
                context.setVariable("paymentLink", "../payment/" + order.getCart().getId());
                context.setVariable("user", order.getCart().getUser());
                context.setVariable("space", " ");

                context.setVariable("totalPrice", order.getTotalPrice());

                engine.process("order/order-summary.html", context, response.getWriter());
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
    }
}