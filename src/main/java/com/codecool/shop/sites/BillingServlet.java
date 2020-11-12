package com.codecool.shop.sites;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.logic.UserLogic;
import com.codecool.shop.model.User;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/billing/*"})
public class BillingServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendRedirect("/");
        } else {
            TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
            WebContext context = new WebContext(request, response, request.getServletContext());
            String[] splits = pathInfo.split("/");
            User user = UserLogic.getInstance().getElement(Integer.parseInt(splits[1]));
            context.setVariable("user", user);
            engine.process("billing/billing.html", context, response.getWriter());
        }
    }
}
