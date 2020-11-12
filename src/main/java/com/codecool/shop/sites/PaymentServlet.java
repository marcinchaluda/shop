package com.codecool.shop.sites;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.logic.CartLogic;
import com.codecool.shop.logic.SessionLogic;
import com.codecool.shop.model.User;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"/payment/*"})
public class PaymentServlet extends HttpServlet {
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
            CartLogic cartLogic = CartLogic.getInstance();
            session.setAttribute("cartId", cartLogic.getElementByUser(user.getId()).getId());
            if (sessionLogic.orderBelongsToUser(user.getId(), Integer.parseInt(splits[1]))) {
                TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
                WebContext context = new WebContext(request, response, request.getServletContext());
                engine.process("payment/payment.html", context, response.getWriter());
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
    }
}
