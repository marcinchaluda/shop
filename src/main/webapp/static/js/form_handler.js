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
        displayErrorMessage("This user already exist! Please input valid fields.");
    } else {
        clearErrorMessage();
        window.location.href = redirectURL;
    }
}

function validateEmptyField() {

    const data = getDataFromRegistrationForm();
    if (data.password.value === data.confirmPassword.value) {
        if (util.validateUserName(data.name) && util.validateEmail(data.email) && util.validatePassword(data.password)) {
            return true;
        }
    }
    return false;
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