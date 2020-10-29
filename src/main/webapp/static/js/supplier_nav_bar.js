import {products, category} from "./enumerators.js";
import {layoutGenerator} from "./main_layout_generator.js";
import {buttonHandler} from "./buttons_handler.js";

const amazonBtn = document.querySelector(".amazon");
const content = document.querySelector(".container");

export const suppliersNavBar = {
    activateAllSuppliersButtons: function () {

    },

    amazonButtonHandler: function () {
        amazonBtn.addEventListener("click", function () {
            displayProducts(category.product, products.tablets, amazonBtn);
        });
    },
}