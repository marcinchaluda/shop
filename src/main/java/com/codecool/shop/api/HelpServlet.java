package com.codecool.shop.api;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class HelpServlet {
    public static String getParameterIfExist(ServletRequest request, String paramName, String defaultValue) {
        if (request.getParameter(paramName) != null) {
            return request.getParameter(paramName);
        } else {
            return defaultValue;
        }
    }

    public static void defineResponseTypeJson(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }

    public static int parseIdToInt(String element) throws ServletException {
        try {
            return Integer.parseInt(element);
        } catch (NumberFormatException e) {
            throw new ServletException("Id parameter is not valid.", e);
        }
    }

    public static PrintWriter setResponse(HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        HelpServlet.defineResponseTypeJson(response);
        return out;
    }

    public static String[] uriGotOneIdAndIsCorrect(HttpServletResponse response, String pathInfo) throws IOException {
        String[] splits = pathInfo.split("/");

        if(splits.length != 2) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

        return splits;
    }
}