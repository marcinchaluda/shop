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

    AddressLogic addressLogic = new AddressLogic();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HelpServlet.sendRequestForAllElementsAndCheckSortAbility(request, response, addressLogic, Address.class);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HelpServlet.createInstanceAndUpdateElement(request, response, addressLogic, Address.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HelpServlet.createInstanceAndAddElement(request, response, addressLogic, Address.class);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HelpServlet.createInstanceAndRemoveElement(request, response, addressLogic, Address.class);
    }
}
