import {dataHandler} from "./data_handler.js";
import {util} from "./util.js";

const creditCardBtn = document.querySelector(".credit-card a");
const payPalBtn = document.querySelector(".paypal a");
const payBtn = document.querySelector(".pay-button");
const loginBtn = document.querySelector(".login-button");
const creditCartInfo = document.querySelector(".credit-card .personal-info");
const paypalInfo = document.querySelector(".paypal .personal-info");
const statusMessage = document.querySelector(".status-message");

const payment = {
    orderId: document.location.pathname.split("/")[2],
    init: function () {
        this.showCreditCardContent();
        this.showPaypalContent();
        this.submitCreditCardPayment();
        this.submitPayPalPayment();
    },

    showCreditCardContent: function () {
        creditCardBtn.addEventListener("click", () => {
            creditCartInfo.style.display = "flex";
            paypalInfo.style.display = "none";
            clearStatusMessageContent();
        });
    },

    showPaypalContent: function () {
        payPalBtn.addEventListener("click", () => {
            creditCartInfo.style.display = "none";
            paypalInfo.style.display = "flex";
            clearStatusMessageContent();
        });
    },

    submitCreditCardPayment: function () {
        payBtn.addEventListener("click", () => {
            if (creditCardFieldsValid()) {
                statusMessage.textContent = generatePaymentStatus(payment.orderId);
            }
        });
    },

    submitPayPalPayment: function () {
        loginBtn.addEventListener("click", () => {
            if (payPalFieldsValid()) {
                statusMessage.textContent = generatePaymentStatus(payment.orderId);
            }
        });
    },

}

function clearStatusMessageContent() {
    statusMessage.textContent = "";
    statusMessage.style.backgroundColor = "#eae9f2";
}

function creditCardFieldsValid() {
    const cardNumber = util.validateCardNumber(document.getElementById("card-number").value);
    const cardHolder = util.validateCardHolder(document.getElementById("holder").value);
    const validationCode = util.validateCode(document.getElementById("code").value);

    if (!cardNumber) {
        displayErrorMessage("Please provide valid card number. (16 digits)");
    } else if (!cardHolder) {
        displayErrorMessage("Please provide valid card holder's name. (Only a-Z)");
    } else if (!validationCode) {
        displayErrorMessage("Please provide valid verification code. (3 digits)");
    }
    return cardHolder && cardNumber && validationCode;
}

function payPalFieldsValid() {
    const name = util.validateUserName(document.getElementById("username").value);
    const password = util.validatePassword(document.getElementById("pass").value);

    if (!name) {
        displayErrorMessage("Please provide valid name. (Only a-Z)");
    } else if (!password) {
        displayErrorMessage("Please provide valid password. (One lower, one upper case letter, digits, min 8 chars)");
    }
    return name && password;
}

function generatePaymentStatus(orderId) {
    let message;
    const randomNumber = Math.floor(Math.random() * 10);
    if (randomNumber < 6) {
        message = "payment successful!";
        statusMessage.style.backgroundColor = "lightgreen";
        const json = `{\"paid\": \"true\", \"orderId\": ${orderId}}`;
        dataHandler.patchOrder(orderId, JSON.parse(json));
    } else {
        message = "your payment has been decline!";
        statusMessage.style.backgroundColor = "red";
    }
    return message;
}

function displayErrorMessage(errorMessage) {
    statusMessage.style.backgroundColor = "red";
    statusMessage.textContent = errorMessage;
}

payment.init();
util.redirectToHomePage();