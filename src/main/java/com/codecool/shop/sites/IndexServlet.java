package com.codecool.shop.sites;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.logic.CartLogic;
import com.codecool.shop.model.User;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"/"})
public class IndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        WebContext context = new WebContext(request, response, request.getServletContext());
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        if (session.getAttribute("user") == null || session.getAttribute("cartId") == null) {
            context.setVariable("user", false);
            context.setVariable("cartId", 0);
        } else {
            User user = (User) session.getAttribute("user");
            CartLogic cartLogic = CartLogic.getInstance();
            session.setAttribute("cartId", cartLogic.getElementByUser(user.getId()).getId());
            int cartId = (int) session.getAttribute("cartId");
            context.setVariable("user", true);
            context.setVariable("cartId", cartId);
        }
        engine.process("product/index.html", context, response.getWriter());
    }
}
