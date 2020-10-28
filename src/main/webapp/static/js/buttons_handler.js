import {dataHandler} from "./data_handler.js";
import {layoutGenerator} from "./main_layout_generator.js";
import {cartGenerator} from "./cart_layout_generator.js";

const content = document.querySelector(".container");
const tabletsBtn = document.querySelector(".tablets");
const sortOptionBtn = document.getElementById("toggle-sort-option");
const ulProducts = document.querySelector(".products");
const ulSupplies = document.querySelector(".suppliers");

export const navButtonHandler = {

    init: function () {
        showProducts();
        this.productButtonHandler();
        this.toggleNavMenuBySortOption();
    },

    productButtonHandler: function () {
        tabletsBtn.addEventListener("click", function () {
            layoutGenerator.removeContent(content);
            showProducts();
        })
    },

    addProductToCart: function (productId) {
        const data = getProduct(productId);
        dataHandler.sendProductToCart(data, function (response) {
            cartGenerator.createProductInfo(response);
        });
    },

    toggleNavMenuBySortOption: function () {
        switchSortOption();
    }
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

function switchSortOption() {
    sortOptionBtn.addEventListener("click", () => {
        displaySortOptionOnButton();
        if (sortOptionBtn.innerText === category.SUPPLIER) {
            ulProducts.style.display = "flex";
            ulSupplies.style.display = "none";
        } else {
            ulProducts.style.display = "none";
            ulSupplies.style.display = "flex";
        }
    });
}

function displaySortOptionOnButton() {
    sortOptionBtn.innerText === category.SUPPLIER ? sortOptionBtn.innerText = category.PRODUCTS
        : sortOptionBtn.innerText = category.SUPPLIER;
}

const category = {
    SUPPLIER: "SORT BY SUPPLIERS",
    PRODUCTS: "SORT BY PRODUCTS",
}

