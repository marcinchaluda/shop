import {dataHandler} from "./data_handler.js";
import {layoutGenerator} from "./main_layout_generator.js";
import {cartGenerator} from "./cart_layout_generator.js";

const content = document.querySelector(".container");
const productsBtn = document.querySelector(".tablets");

export const navButtonHandler = {

    init: function () {
        showProducts();
        this.productButtonHandler();
    },

    productButtonHandler: function () {
        productsBtn.addEventListener("click", function () {
            layoutGenerator.removeContent(content);
            showProducts();
        })
    },

    addProductToCart: function (productId, cartId, userId, quantity) {
        const data = {
            productId: productId,
            cartId: cartId,
            userId: userId,
            quantity: quantity
        }
        dataHandler.sendProductToCart(data, function (response) {
            cartGenerator.createProductInfo(response);
        });
    },

}

function showProducts() {
    dataHandler.getProducts( function (products) {
        layoutGenerator.createProductCards(products, category.tablets);
    });
}

function getProduct(productId) {
    dataHandler.getProduct(productId, function (response) {
       return response;
    });
}

const category = {
    tablets: "Tablets",
    phones: "Phones",
    notebooks: "Notebooks",
    webComponents: "Web-components",
}

