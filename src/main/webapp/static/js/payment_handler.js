import {dataHandler} from "./data_handler.js";

const creditCardBtn = document.querySelector(".credit-card a");
const payPalBtn = document.querySelector(".paypal a");
const payBtn = document.querySelector(".pay-button");
const loginBtn = document.querySelector(".login-button");
const creditCartInfo = document.querySelector(".credit-card .personal-info");
const paypalInfo = document.querySelector(".paypal .personal-info");
const statusMessage = document.querySelector(".status-message");

const payment = {
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
            document.querySelector(".credit-card .details").submit();
            if (creditCardFieldsValid(creditCartInfo)) {
                //TODO get order id from database
                const orderId = 1;
                statusMessage.textContent = generatePaymentStatus();
                getOrder(orderId);
            }
        });
    },

    submitPayPalPayment: function () {
        loginBtn.addEventListener("click", () => {
            document.querySelector(".credit-card .details").submit();
            if (creditCardFieldsValid(paypalInfo)) {
                //TODO get order id from database
                const orderId = 1;
                statusMessage.textContent = generatePaymentStatus();
                getOrder(orderId);
            }
        });
    },

}
function updateOrder(order) {
    const jsonData = {
        id: order.id,
        cart_id: order.cart.id,
        paid: true,
    }
    console.log(jsonData);
    dataHandler.updateOrder(order.id, jsonData);
}

function getOrder(orderId) {
    dataHandler.getOrder(orderId, function (response) {
        updateOrder(response);
    });
}

function clearStatusMessageContent() {
    statusMessage.textContent = "";
    statusMessage.style.backgroundColor = "#EAE9F2";
}

function creditCardFieldsValid(formToCheck) {
    let allFields = formToCheck.childNodes;
    for (let i=1; i < allFields.length - 2; i += 2) {
        if (allFields[i].children[1].value === "") {
            return false;
        }
    }
    return true;
}

function generatePaymentStatus() {
    let message;
    const randomNumber = Math.floor(Math.random() * 10);
    if (randomNumber < 6) {
        message = "payment successful!";
        statusMessage.style.backgroundColor = "lightgreen";
    } else {
        message = "your payment has been decline!";
        statusMessage.style.backgroundColor = "red";
    }
    return message;
}

payment.init();