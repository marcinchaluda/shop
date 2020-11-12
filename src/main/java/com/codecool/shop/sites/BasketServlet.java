package com.codecool.shop.sites;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.logic.OrderLogic;
import com.codecool.shop.model.OrderHistory;
import com.codecool.shop.model.User;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"/cart"})
public class BasketServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null || session.getAttribute("cartId") == null) {
            response.sendRedirect("/login");
        } else {
            int cartId = (int) session.getAttribute("cartId");
            System.out.println(cartId);
            WebContext context = new WebContext(request, response, request.getServletContext());
            TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
            context.setVariable("cartId", cartId);
            engine.process("cart/cart.html", context, response.getWriter());
        }
    }
}
