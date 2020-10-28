package com.codecool.shop.api;

import com.codecool.shop.logic.AddressLogic;
import com.codecool.shop.model.Address;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/api/addresses/*"})
public class AddressesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        AddressLogic addressLogic = AddressLogic.getInstance();
        HelpServlet.sendRequestForAllElementsAndCheckSortAbility(request, response, addressLogic);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        AddressLogic addressLogic = AddressLogic.getInstance();
        HelpServlet.createInstanceAndUpdateElement(request, response, addressLogic, Address.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        AddressLogic addressLogic = AddressLogic.getInstance();
        HelpServlet.createInstanceAndAddElement(request, response, addressLogic, Address.class);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        AddressLogic addressLogic = AddressLogic.getInstance();
        HelpServlet.createInstanceAndRemoveElement(request, response, addressLogic, Address.class);
    }
}
