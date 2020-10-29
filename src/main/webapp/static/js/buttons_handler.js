import {dataHandler} from "./data_handler.js";
import {layoutGenerator} from "./main_layout_generator.js";
import {cartGenerator} from "./cart_layout_generator.js";
import {products, category, categoryBtnDescription} from "./enumerators.js";
import {productsNavBar} from "./product_nav_bar.js";

const tabletsBtn = document.querySelector(".tablets");
const sortOptionBtn = document.getElementById("toggle-sort-option");
const ulProducts = document.querySelector(".products");
const ulSupplies = document.querySelector(".suppliers");
const navButtons = document.querySelectorAll("ul li a");

export const buttonHandler = {
    init: function () {
        this.showProducts(category.product, products.tablets);
        setInitStyles();
        productsNavBar.activateAllProductButtons();
        this.toggleNavMenuBySortOption();
        showTotalPriceAndQuantity(1);
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

    showProducts: function (category, sortOption) {
        dataHandler.getProducts(category, sortOption, function (products) {
            layoutGenerator.createProductCards(products);
        });
    },

    toggleNavMenuBySortOption: function () {
        switchSortOption();
    },

    markButtonAsCurrent: function (currentButton) {
        navButtons.forEach(button => {
            button.style.backgroundColor = "#0B2D59";
            button.style.color = "#EAE9F2";
        });
        currentButton.style.backgroundColor = "#C6C5D9";
        currentButton.style.color = "#0B2D59";
    },
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
        buttonHandler.markButtonAsCurrent(button);
    });
}

function displaySortOptionOnButton() {
    sortOptionBtn.innerText === categoryBtnDescription.SUPPLIER ? sortOptionBtn.innerText = categoryBtnDescription.PRODUCTS
        : sortOptionBtn.innerText = categoryBtnDescription.SUPPLIER;
}

function setInitStyles() {
    tabletsBtn.style.backgroundColor = "#C6C5D9";
    tabletsBtn.style.color = "#0B2D59";
    ulProducts.style.display = "flex";
    ulSupplies.style.display = "none";
}

const showTotalPriceAndQuantity = cartId => {
    dataHandler.getCart(cartId, updateTotalPriceAndQuantity);
}

const updateTotalPriceAndQuantity = data => {
    console.log(data)
    document.querySelector("#total-price-and-quantity").innerHTML = `(` + data.quantity + `) (` + data.totalPrice + `)`;
}