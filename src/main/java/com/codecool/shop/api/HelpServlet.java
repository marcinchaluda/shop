package com.codecool.shop.api;

import com.codecool.shop.logic.BusinessLogic;
import com.codecool.shop.logic.NotSortable;
import com.codecool.shop.logic.Sortable;
import com.codecool.shop.model.Product;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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

    public static String getDefinedPathIfValidElseBadRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
        return pathInfo;
    }

    public static <T> void getElementsBasedOnUriId(HttpServletRequest request, HttpServletResponse response, BusinessLogic<T> logic, Class<T> classType) throws IOException, ServletException {
        final int MODEL_ID_INDEX = 1;

        PrintWriter out = HelpServlet.setResponse(response);
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            if (classType == Sortable.class) {
                sendParametrizedAllElementsRequest(request, out, (Sortable<Product>) logic);
                return;
            }
            getAllElementsFromBusinessLogicAndSendViaJson((NotSortable<T>) logic, out);
            return;
        }

        String[] splits = HelpServlet.uriGotOneIdAndIsCorrect(response, pathInfo);
        int productId = parseIdToInt(splits[MODEL_ID_INDEX]);
        getOneElementFromBusinessLogicAndSendViaJson(logic, productId, out);
    }

    private static void sendParametrizedAllElementsRequest(HttpServletRequest request, PrintWriter out, Sortable<Product> logic) {
        String sortType = HelpServlet.getParameterIfExist(request, "sort", "default");
        String sortBy = HelpServlet.getParameterIfExist(request, "by", "default");

        List<Product> elements = logic.getAllElements(sortType, sortBy);
        out.print(new Gson().toJson(elements));
        out.flush();
    }

    public static <T> void addOneElementToBusinessLogic(HttpServletRequest request, HttpServletResponse response, BusinessLogic<T> logic, Class<T> classType) throws IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            String json = request.getParameter("jsondata");
            T element = new Gson().fromJson(json, classType);
            logic.addElement(element);
            response.setStatus(200);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    public static <T> void updateOneElementInBusinessLogic(HttpServletRequest request, HttpServletResponse response, BusinessLogic<T> logic, Class<T> classType) throws IOException {
        T element = createElementFromJson(request, response, classType);
        logic.updateElement(element);
        response.setStatus(200);
    }

    public static <T> void removeOneElementToBusinessLogic(HttpServletRequest request, HttpServletResponse response, BusinessLogic<T> logic, Class<T> classType) throws IOException {
        T element = createElementFromJson(request, response, classType);
        logic.removeElement(element);
        response.setStatus(200);
    }

    private static <T> T createElementFromJson(HttpServletRequest request, HttpServletResponse response, Class<T> classType) throws IOException {
        String pathInfo = getDefinedPathIfValidElseBadRequest(request, response);
        uriGotOneIdAndIsCorrect(response, pathInfo);
        String json = request.getParameter("jsondata");
        return new Gson().fromJson(json, classType);
    }

    public static <T> void getOneElementFromBusinessLogicAndSendViaJson(BusinessLogic<T> businessClass, int elementId, PrintWriter out) {
        T element = businessClass.getElement(elementId);
        out.print(new Gson().toJson(element));
        out.flush();
    }

    public static <T> void getAllElementsFromBusinessLogicAndSendViaJson(NotSortable<T> businessClass, PrintWriter out) {
        List<T> elements = businessClass.getAllElements();
        out.print(new Gson().toJson(elements));
        out.flush();
    }
}