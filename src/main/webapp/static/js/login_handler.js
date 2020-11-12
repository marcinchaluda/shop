import {dataHandler} from "./data_handler.js";

const loginButton = document.querySelector(".login-button");
const message = document.querySelector(".message");
const messageContainer = document.querySelector(".status-message");

function init() {
    clearErrorMessage();
}

loginButton.addEventListener("click", () => {
    messageContainer.style.backgroundColor = "#EAE9F2";
    dataHandler.postLoggedUser(getDataFromLoginForm(), function (status) {
        handleRegistrationResponse(status, "../");
    });
});

function handleRegistrationResponse(response, redirectURL) {
    if (response.status !== 201) {
        displayErrorMessage("This user already exist! Please input valid fields.");
    } else {
        clearErrorMessage();
        window.location.href = redirectURL;
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

function getDataFromLoginForm() {
    return {
        email: document.getElementById("login-email").value,
        password: document.getElementById("login-password").value,
    }
}

init();