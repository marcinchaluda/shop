package com.codecool.shop.servlets;

import javax.servlet.ServletRequest;

public class HelpServlet {
    public static String getParameterIfExist(ServletRequest request, String paramName, String defaultValue) {
        if (request.getParameter(paramName) != null) {
            return request.getParameter(paramName);
        } else {
            return defaultValue;
        }
    }
}