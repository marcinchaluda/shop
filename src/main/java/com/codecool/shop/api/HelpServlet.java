package com.codecool.shop.api;

import com.codecool.shop.logic.BusinessLogic;
import com.codecool.shop.logic.GetAllLogic;
import com.codecool.shop.logic.Sortable;
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

    public static void setResponseTypeToJson(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }

    public static int parseParameterIdToInteger(String element) throws ServletException {
        try {
            return Integer.parseInt(element);
        } catch (NumberFormatException e) {
            throw new ServletException("Id parameter is not valid.", e);
        }
    }

    public static PrintWriter createPrintWriterAndSetItUp(HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        HelpServlet.setResponseTypeToJson(response);
        return out;
    }

    public static String[] getSplitUrlIfLengthIsEqual2(HttpServletResponse response, String pathInfo) throws IOException {
        String[] splits = pathInfo.split("/");

        if (splits.length != 2) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

        return splits;
    }

    public static String getPathInfoWhenUriContainsIdParameter(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
        return pathInfo;
    }

    public static <T> void sendRequestForAllElementsAndCheckSortAbility(HttpServletRequest request, HttpServletResponse response, BusinessLogic<T> logic, Class<T> classType) throws IOException, ServletException {
        final int MODEL_ID_INDEX = 1;

        PrintWriter out = HelpServlet.createPrintWriterAndSetItUp(response);
        if (classType == Sortable.class) {
            getAllSortedElements(request, out, (Sortable<T>) logic);
        }

        if (classType == GetAllLogic.class) {
            String pathInfo = request.getPathInfo();
            if (pathInfo == null || pathInfo.equals("/")) {
                getAllUnsortedElements((GetAllLogic<T>) logic, out);
                return;
            }
            String[] splits = HelpServlet.getSplitUrlIfLengthIsEqual2(response, pathInfo);
            int productId = parseParameterIdToInteger(splits[MODEL_ID_INDEX]);
            createJsonFromElement(logic, productId, out);
        }
    }

    public static <T> void createInstanceAndAddElement(HttpServletRequest request, HttpServletResponse response, BusinessLogic<T> logic, Class<T> classType) throws IOException {
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

    public static <T> void createInstanceAndUpdateElement(HttpServletRequest request, HttpServletResponse response, BusinessLogic<T> logic, Class<T> classType) throws IOException {
        T element = createElementFromJson(request, response, classType);
        logic.updateElement(element);
        response.setStatus(200);
    }

    public static <T> void createInstanceAndRemoveElement(HttpServletRequest request, HttpServletResponse response, BusinessLogic<T> logic, Class<T> classType) throws IOException {
        T element = createElementFromJson(request, response, classType);
        logic.removeElement(element);
        response.setStatus(200);
    }

    private static <T> T createElementFromJson(HttpServletRequest request, HttpServletResponse response, Class<T> classType) throws IOException {
        String pathInfo = getPathInfoWhenUriContainsIdParameter(request, response);
        getSplitUrlIfLengthIsEqual2(response, pathInfo);
        String json = request.getParameter("jsondata");
        return new Gson().fromJson(json, classType);
    }

    public static <T> void createJsonFromElement(BusinessLogic<T> businessClass, int elementId, PrintWriter out) {
        T element = businessClass.getElement(elementId);
        out.print(new Gson().toJson(element));
        out.flush();
    }

    private static <T> void getAllSortedElements(HttpServletRequest request, PrintWriter out, Sortable<T> logic) {
        String sortType = HelpServlet.getParameterIfExist(request, "sort", "default");
        String sortBy = HelpServlet.getParameterIfExist(request, "by", "default");

        List<T> elements = logic.getAllElements(sortType, sortBy);
        out.print(new Gson().toJson(elements));
        out.flush();
    }

    public static <T> void getAllUnsortedElements(GetAllLogic<T> businessClass, PrintWriter out) {
        List<T> elements = businessClass.getAllElements();
        out.print(new Gson().toJson(elements));
        out.flush();
    }
}