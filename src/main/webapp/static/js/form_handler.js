const registerButton = document.querySelector(".register-button");
const message = document.querySelector(".message");
const messageContainer = document.querySelector(".status-message");

function init() {
    clearErrorMessage();
}
registerButton.addEventListener("click", (event) => {
    messageContainer.style.backgroundColor = "#EAE9F2";
    if (validateEmptyField()) {
        const status = fetch("http://localhost:8080/registration").then(response => response.status);
        if (status !== 201) {
            displayErrorMessage("This user already exist! Please input valid fields.");
        } else {
            clearErrorMessage();
        }
    }
});

function validateEmptyField() {

    const name = document.querySelector(".user-name");
    const email = document.querySelector(".user-email");
    const password = document.querySelector(".user-password");
    const confirmPassword = document.querySelector(".user-confirm-password");

    if (name.value === "" || email.value === "" || password.value === "") {
        return false;
    }
    if (password.value !== confirmPassword.value) {
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

init();