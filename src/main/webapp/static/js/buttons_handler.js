import {dataHandler} from "./data_handler.js";
import {layoutGenerator} from "./main_layout_generator.js";
import {cartGenerator} from "./cart_layout_generator.js";
import {products, category, categoryBtnDescription, suppliers} from "./enumerators.js";
import {productsNavBar} from "./product_nav_bar.js";
import {suppliersNavBar} from "./supplier_nav_bar.js";
import {util} from "./util.js";

const tabletsBtn = document.querySelector(".tablets");
const sortOptionBtn = document.getElementById("toggle-sort-option");
const ulProducts = document.querySelector(".products");
const ulSupplies = document.querySelector(".suppliers");
const navButtons = document.querySelectorAll("ul li a");
const content = document.querySelector(".container");

export const buttonHandler = {
    init: function () {
        this.showProducts(category.product, products.tablets);
        setInitStyles();
        productsNavBar.activateAllProductButtons();
        suppliersNavBar.activateAllSuppliersButtons();
        this.toggleNavMenuBySortOption();
        showTotalPriceAndQuantity(1);
    },

    addProductToCart: function (cartId, productId, quantity) {
        const data = {
            productId: productId,
            quantity: quantity
        }
        dataHandler.increaseAmountOfProductInCart(data, cartId);
    },

    updateProductInCart: function (cartId, productId, quantity) {
        const data = {
            productId: productId,
            quantity: quantity
        }
        dataHandler.updateAmountOfProductInCart(data, cartId);
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

    displayProducts: function(category, product, currentBtn) {
        util.removeContent(content);
        buttonHandler.markButtonAsCurrent(currentBtn);
        buttonHandler.showProducts(category, product);
    },
}

function switchSortOption() {
    sortOptionBtn.addEventListener("click", () => {
        displaySortOptionOnButton();
        let button;
        if (sortOptionBtn.innerText === categoryBtnDescription.SUPPLIER) {
            showSelectedOptionNavBar(category.product);
            button = tabletsBtn;
            displayDefaultProducts(category.product, products.tablets);
        } else {
            showSelectedOptionNavBar(category.supplier);
            button = document.querySelector(".amazon");
            displayDefaultProducts(category.supplier, suppliers.amazon);
        }
        buttonHandler.markButtonAsCurrent(button);
    });
}

function showSelectedOptionNavBar(option) {
    if (option === category.product) {
        ulProducts.style.display = "flex";
        ulSupplies.style.display = "none";
    } else {
        ulProducts.style.display = "none";
        ulSupplies.style.display = "flex";
    }
}

function displayDefaultProducts(category, sortOption) {
    util.removeContent(content);
    buttonHandler.showProducts(category, sortOption);
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
    let quantity = 0;
    let totalPrice = 0;
    const currency = data.products[0].product.currency;
    data.products.forEach(product => quantity += product.quantity);
    data.products.forEach(product => totalPrice += product.product.unitPrice * product.quantity);
    document.querySelector("#total-price-and-quantity").innerHTML = `(` + quantity + ` items) (` + totalPrice + " " + currency + `)`;
}