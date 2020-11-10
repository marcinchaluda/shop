const registerButton = document.querySelector(".register-button");
const message = document.querySelector(".message");
const messageContainer = document.querySelector(".status-message");

function init() {
    messageContainer.style.backgroundColor = "#EAE9F2";
    message.textContent = "";
}
registerButton.addEventListener("click", () => {
    messageContainer.style.backgroundColor = "#EAE9F2";
    if (validateEmptyField()) {
        messageContainer.style.backgroundColor = "red";
        message.textContent = "This user already exist! Please input valid fields."
    }
});

function validateEmptyField() {
    const name = document.querySelector(".user-name");
    const email = document.querySelector(".user-email");
    const password = document.querySelector(".user-password");
    const confirmPassword = document.querySelector(".user-confirm-password");

    init();
    if (name.value === "" || email.value === "" || password.value === "") {
        return false;
    }
    if (password.value != confirmPassword.value) {
        messageContainer.style.backgroundColor = "red";
        message.textContent = "Your password and confirmation password do not match.";
        return false;
    }
    return true
}

init();