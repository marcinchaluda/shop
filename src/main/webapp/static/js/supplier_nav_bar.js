import {suppliers, category} from "./enumerators.js";
import {buttonHandler} from "./buttons_handler.js";

const amazonBtn = document.querySelector(".amazon");

export const suppliersNavBar = {
    activateAllSuppliersButtons: function () {
        this.amazonButtonHandler();
        this.lenovoButtonHandler();
        this.appleButtonHandler();
    },

    amazonButtonHandler: function () {
        amazonBtn.addEventListener("click", function () {
            buttonHandler.displayProducts(category.supplier, suppliers.amazon, amazonBtn);
        });
    },

    lenovoButtonHandler: function () {
        const lenovoBtn = document.querySelector(".lenovo");
        lenovoBtn.addEventListener("click", () => {
            buttonHandler.displayProducts(category.supplier, suppliers.lenovo, lenovoBtn);
        });
    },

    appleButtonHandler: function () {
        const appleBtn = document.querySelector(".apple");
        appleBtn.addEventListener("click", () => {
            buttonHandler.displayProducts(category.supplier, suppliers.apple, appleBtn);
        });
    },
}