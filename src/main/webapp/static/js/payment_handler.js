import {buttonDescription} from "./enumerators.js";

const creditCardBtn = document.querySelector(".credit-card a");
const payPalBtn = document.querySelector(".paypal a");
const submitBtn = document.querySelector(".button");
const creditCartInfo = document.querySelector(".credit-card .personal-info");
const paypalInfo = document.querySelector(".paypal .personal-info");
const statusMessage = document.querySelector(".status-message");

const payment = {
    init: function () {
        submitBtn.innerText = buttonDescription.pay;
        submitBtn.style.display = "none";
        this.showCreditCardContent();
        this.showPaypalContent();
        this.submitPayment();
    },

    showCreditCardContent: function () {
        creditCardBtn.addEventListener("click", () => {
            submitBtn.style.display = "block";
            creditCartInfo.style.display = "flex";
            paypalInfo.style.display = "none";
            submitBtn.innerText = buttonDescription.pay;
        });
    },

    showPaypalContent: function () {
        payPalBtn.addEventListener("click", () => {
            submitBtn.style.display = "block";
            creditCartInfo.style.display = "none";
            paypalInfo.style.display = "flex";
            submitBtn.innerText = buttonDescription.login;
        });
    },

    submitPayment: function () {
        submitBtn.addEventListener("click", () => {
            sendActivePayment();
        });
    },
}

function sendActivePayment() {
    let message = generatePaymentStatus()
    if (submitBtn.innerText == buttonDescription.pay) {
        statusMessage.textContent = message;
        document.querySelector(".credit-card .details").submit();
    } else {
        statusMessage.textContent = message;
        document.querySelector(".paypal .details").submit();
    }
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