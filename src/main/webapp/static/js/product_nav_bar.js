import {products, category} from "./enumerators.js";
import {layoutGenerator} from "./main_layout_generator.js";
import {buttonHandler} from "./buttons_handler.js";

const tabletsBtn = document.querySelector(".tablets");
const content = document.querySelector(".container");

export const productsNavBar = {
    activateAllProductButtons: function () {
        this.tabletsButtonHandler();
        this.phonesButtonHandler();
        this.notebooksButtonHandler();
        this.webDevicesButtonHandler();
    },

    tabletsButtonHandler: function () {
        tabletsBtn.addEventListener("click", function () {
            displayProducts(category.product, products.tablets, tabletsBtn);
        });
    },

    phonesButtonHandler: function () {
        const phonesBtn = document.querySelector(".phones");
        phonesBtn.addEventListener("click", () => {
            displayProducts(category.product, products.phones, phonesBtn);
        });
    },

    notebooksButtonHandler: function () {
        const notebooksBtn = document.querySelector(".notebooks");
        notebooksBtn.addEventListener("click", () => {
            displayProducts(category.product, products.notebooks, notebooksBtn);
        });
    },

    webDevicesButtonHandler: function () {
        const webDevicesBtn = document.querySelector(".web-devices");
        webDevicesBtn.addEventListener("click", () => {
            displayProducts(category.product, products.webDevices, webDevicesBtn);
        });
    },
}

function displayProducts(category, product, currentBtn) {
    content.innerHTML = "";
    // layoutGenerator.removeContent(content); // method not working :(
    buttonHandler.markButtonAsCurrent(currentBtn);
    buttonHandler.showProducts(category, product);
}