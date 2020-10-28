import {dataHandler} from "./data_handler.js";
import {layoutGenerator} from "./main_layout_generator.js";
import {cartGenerator} from "./cart_layout_generator.js";

const content = document.querySelector(".container");
const tabletsBtn = document.querySelector(".tablets");
const sortOptionBtn = document.getElementById("toggle-sort-option");
const ulProducts = document.querySelector(".products");
const ulSupplies = document.querySelector(".suppliers");
const navButtons = document.querySelectorAll("ul li a");

export const navButtonHandler = {

    init: function () {
        showProducts(category.product, products.tablets);
        setInitStyles();
        this.activateAllProductButtons();
        this.toggleNavMenuBySortOption();
    },

    activateAllProductButtons: function () {
        this.tabletsButtonHandler();
        this.phonesButtonHandler();
        this.notebooksButtonHandler();
        this.webDevicesButtonHandler();
    },

    tabletsButtonHandler: function () {
        tabletsBtn.addEventListener("click", function () {
            displayProducts(category.product, products.tablets);
        });
    },

    phonesButtonHandler: function () {
        const phonesBtn = document.querySelector(".phones");
        phonesBtn.addEventListener("click", () => {
            displayProducts(category.product, products.phones);
        });
    },

    notebooksButtonHandler: function () {
        const phonesBtn = document.querySelector(".notebooks");
        phonesBtn.addEventListener("click", () => {
            displayProducts(category.product, products.notebooks);
        });
    },

    webDevicesButtonHandler: function () {
        const phonesBtn = document.querySelector(".web-devices");
        phonesBtn.addEventListener("click", () => {
            displayProducts(category.product, products.webDevices);
        });
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

function showProducts(category, sortOption) {
    dataHandler.getProducts(category, sortOption, function (products) {
        layoutGenerator.createProductCards(products);
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
        let button;
        if (sortOptionBtn.innerText === categoryBtnDescription.SUPPLIER) {
            ulProducts.style.display = "flex";
            ulSupplies.style.display = "none";
            button = tabletsBtn;
        } else {
            ulProducts.style.display = "none";
            ulSupplies.style.display = "flex";
            button = document.querySelector(".amazon");
        }
        markButtonAsCurrent(button);
    });
}

function displayProducts(category, product) {
    layoutGenerator.removeContent(content);
    markButtonAsCurrent(tabletsBtn);
    showProducts(category, products);
}

function displaySortOptionOnButton() {
    sortOptionBtn.innerText === categoryBtnDescription.SUPPLIER ? sortOptionBtn.innerText = categoryBtnDescription.PRODUCTS
        : sortOptionBtn.innerText = categoryBtnDescription.SUPPLIER;
}

function markButtonAsCurrent(currentButton) {
    navButtons.forEach(button => {
        button.style.backgroundColor = "#0B2D59";
        button.style.color = "#EAE9F2";
    });
    currentButton.style.backgroundColor = "#C6C5D9";
    currentButton.style.color = "#0B2D59";
}

function setInitStyles() {
    tabletsBtn.style.backgroundColor = "#C6C5D9";
    tabletsBtn.style.color = "#0B2D59";
    ulProducts.style.display = "flex";
    ulSupplies.style.display = "none";
}

const categoryBtnDescription = {
    SUPPLIER: "SORT BY SUPPLIERS",
    PRODUCTS: "SORT BY PRODUCTS",
}

const category = {
    product: "product",
    supplier: "supplier",
}

const products = {
    tablets: "tablets",
    phones: "phones",
    notebooks: "notebooks",
    webDevices: "web-devices",
}

const suppliers = {
    amazon: "amazon",
    lenovo: "lenovo",
    apple: "apple",
}
