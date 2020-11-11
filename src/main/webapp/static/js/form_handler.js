import {dataHandler} from "./data_handler.js";

const registerButton = document.querySelector(".register-button");
const message = document.querySelector(".message");
const messageContainer = document.querySelector(".status-message");

function init() {
    clearErrorMessage();
}

registerButton.addEventListener("click", (event) => {
    messageContainer.style.backgroundColor = "#EAE9F2";
    if (validateEmptyField()) {
        dataHandler.postUser(getDataFromForm(), function (status) {
            handleRegistrationResponse(status);
        })
    }
});

function handleRegistrationResponse(response) {
    if (response.status !== 201) {
        displayErrorMessage("This user already exist! Please input valid fields.");
    } else {
        clearErrorMessage();
        window.location.href = "../login";
    }
}

function validateEmptyField() {

    const data = getDataFromForm();

    if (data.name.value === "" || data.email.value === "" || data.password.value === "") {
        return false;
    }
    if (data.password.value !== data.confirmPassword.value) {
        displayErrorMessage("Your password and confirmation password do not match.");
        return false;
    }
    return true
}

function displayErrorMessage(errorMessage) {
    messageContainer.style.backgroundColor = "red";
    message.textContent = errorMessage;
}

function clearErrorMessage() {
    messageContainer.style.backgroundColor = "#EAE9F2";
    message.textContent = "";
}

function getDataFromForm() {
    return {
        name: document.querySelector(".user-name").value,
        email: document.querySelector(".user-email").value,
        password: document.querySelector(".user-password").value,
        confirmPassword: document.querySelector(".user-confirm-password").value,
    }
}

init();