import {dataHandler} from "./data_handler.js";
import {layoutGenerator} from "./layout_generator.js";

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

    showProduct: function (productId) {
        dataHandler.getProduct(productId, function (response) {
            console.log(productId);
        })
    },
}

function showProducts() {
    dataHandler.getProducts( function (products) {
        layoutGenerator.createProductCards(products, category.tablets);
    });
}

const category = {
    tablets: "Tablets",
    phones: "Phones",
    notebooks: "Notebooks",
    webComponents: "Web-components",
}

