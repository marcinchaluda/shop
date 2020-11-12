import {dataHandler} from "./data_handler.js";
import {util} from "./util.js";

const registerButton = document.querySelector(".register-button");
const message = document.querySelector(".message");
const messageContainer = document.querySelector(".status-message");

function init() {
    clearErrorMessage();
}

registerButton.addEventListener("click", () => {
    messageContainer.style.backgroundColor = "#EAE9F2";
    if (validateEmptyField()) {
        dataHandler.postUser(getDataFromRegistrationForm(), function (status) {
            handleRegistrationResponse(status, "../login");
        });
    }
});

function handleRegistrationResponse(response, redirectURL) {
    if (response.status !== 201) {
        displayErrorMessage("This user already exist!");
    } else {
        clearErrorMessage();
        window.location.href = redirectURL;
    }
}

function validateEmptyField() {

    const data = getDataFromRegistrationForm();
    if (data.password.value === data.confirmPassword.value) {
        if (!util.validateUserName(data.name)) {
            displayErrorMessage("Please provide valid card holder's name. (Only a-Z)");
            return false;
        }

        if (!util.validateEmail(data.email)) {
            displayErrorMessage("Please enter valid email. (Email format with @)");
            return false;
        }
        if (!util.alidatePassword(data.password)) {
            displayErrorMessage("Please enter valid password. (One lower, one upper case letter, digits, min 8 chars)");
            return false;
        }

        clearErrorMessage();
        return true;
    }
}

function displayErrorMessage(errorMessage) {
    messageContainer.style.backgroundColor = "red";
    message.textContent = errorMessage;
}

function clearErrorMessage() {
    messageContainer.style.backgroundColor = "#EAE9F2";
    message.textContent = "";
}

function getDataFromRegistrationForm() {
    return {
        name: document.querySelector(".user-name").value,
        email: document.querySelector(".user-email").value,
        password: document.querySelector(".user-password").value,
        confirmPassword: document.querySelector(".user-confirm-password").value,
    }
}

init();
util.redirectToHomePage();