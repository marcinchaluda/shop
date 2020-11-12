package com.codecool.shop.sites;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.logic.OrderLogic;
import com.codecool.shop.model.OrderHistory;
import com.codecool.shop.model.User;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"/orders"})
public class UserOrdersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {
            response.sendRedirect("/login");
        } else {
            User user = (User) session.getAttribute("user");
            WebContext context = new WebContext(request, response, request.getServletContext());
            int userId = Integer.parseInt(request.getParameter("user"));
            if (userId == user.getId()) {
                OrderHistory orderHistory = OrderLogic.getInstance().getOrdersHistoryBy(userId);
                context.setVariable("user", orderHistory.getUser());
                context.setVariable("orders", orderHistory.getOrderList());
                TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
                engine.process("order/order-list.html", context, response.getWriter());
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
    }
}
