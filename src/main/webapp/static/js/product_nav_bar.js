import {products, category} from "./enumerators.js";
import {buttonHandler} from "./buttons_handler.js";

const tabletsBtn = document.querySelector(".tablets");

export const productsNavBar = {
    activateAllProductButtons: function () {
        this.tabletsButtonHandler();
        this.phonesButtonHandler();
        this.notebooksButtonHandler();
        this.webDevicesButtonHandler();
    },

    tabletsButtonHandler: function () {
        tabletsBtn.addEventListener("click", function () {
            buttonHandler.displayProducts(category.product, products.tablets, tabletsBtn);
        });
    },

    phonesButtonHandler: function () {
        const phonesBtn = document.querySelector(".phones");
        phonesBtn.addEventListener("click", () => {
            buttonHandler.displayProducts(category.product, products.phones, phonesBtn);
        });
    },

    notebooksButtonHandler: function () {
        const notebooksBtn = document.querySelector(".notebooks");
        notebooksBtn.addEventListener("click", () => {
            buttonHandler.displayProducts(category.product, products.notebooks, notebooksBtn);
        });
    },

    webDevicesButtonHandler: function () {
        const webDevicesBtn = document.querySelector(".web-devices");
        webDevicesBtn.addEventListener("click", () => {
            buttonHandler.displayProducts(category.product, products.webDevices, webDevicesBtn);
        });
    },
}
