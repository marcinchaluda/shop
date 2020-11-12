import {dataHandler} from "./data_handler.js";
import {util} from "./util.js";

const loginButton = document.querySelector(".login-button");
const message = document.querySelector(".message");
const messageContainer = document.querySelector(".status-message");

function init() {
    clearErrorMessage();
}

loginButton.addEventListener("click", () => {
    messageContainer.style.backgroundColor = "#EAE9F2";
    dataHandler.postLoggedUser(getDataFromLoginForm(), function (status) {
        if (validateLoginData()) {
            handleRegistrationResponse(status, "../");
        }
    });
});

function handleRegistrationResponse(response, redirectURL) {
    if (response.status !== 201) {
        displayErrorMessage("This user already exist! Please input valid fields.");
    } else {
        clearErrorMessage();
        sessionStorage.setItem("email", document.getElementById("login-email").value);
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

function validateLoginData() {

    const data = getDataFromLoginForm();

    if (util.validateEmail(data.email) && util.validatePassword(data.password)) {
        return true;
    }
    return false;
}

function getDataFromLoginForm() {
    return {
        email: document.getElementById("login-email").value,
        password: document.getElementById("login-password").value,
    }
}

init();
util.redirectToHomePage();