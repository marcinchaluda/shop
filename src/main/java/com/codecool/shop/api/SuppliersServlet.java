package com.codecool.shop.api;

import com.codecool.shop.logic.SupplierLogic;
import com.codecool.shop.model.Supplier;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/api/suppliers/*"})
public class SuppliersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        SupplierLogic supplierLogic = SupplierLogic.getInstance();
        HelpServlet.sendRequestForAllElementsAndCheckSortAbility(request, response, supplierLogic);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SupplierLogic supplierLogic = SupplierLogic.getInstance();
        HelpServlet.createInstanceAndUpdateElement(request, response, supplierLogic, Supplier.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SupplierLogic supplierLogic = SupplierLogic.getInstance();
        HelpServlet.createInstanceAndAddElement(request, response, supplierLogic, Supplier.class);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SupplierLogic supplierLogic = SupplierLogic.getInstance();
        HelpServlet.createInstanceAndRemoveElement(request, response, supplierLogic, Supplier.class);
    }
}
