import {buttonDescription} from "./enumerators.js";

const creditCardBtn = document.querySelector(".credit-card a");
const payPalBtn = document.querySelector(".paypal a");
const submitBtn = document.querySelector(".button");
const creditCartInfo = document.querySelector(".credit-card .personal-info");
const paypalInfo = document.querySelector(".paypal .personal-info");

const payment = {
    init: function () {
        submitBtn.innerText = buttonDescription.pay;
        this.showCreditCardContent();
        this.showPaypalContent();
    },

    showCreditCardContent: function () {
        creditCardBtn.addEventListener("click", () => {
            creditCartInfo.style.display = "flex";
            paypalInfo.style.display = "none";
            submitBtn.innerText = buttonDescription.pay;
        });
    },

    showPaypalContent: function () {
        payPalBtn.addEventListener("click", () => {
            creditCartInfo.style.display = "none";
            paypalInfo.style.display = "flex";
            submitBtn.innerText = buttonDescription.login;
        });
    },
}

payment.init();