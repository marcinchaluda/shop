package com.codecool.shop.controller;

import javax.servlet.ServletRequest;

public class HelpController {
    public static String getParameterIfExist(ServletRequest request, String paramName, String defaultValue) {
        if (request.getParameter(paramName) != null) {
            return request.getParameter(paramName);
        } else {
            return defaultValue;
        }
    }
}