const registerButton = document.querySelector(".register-button");
const message = document.querySelector(".message");
const messageContainer = document.querySelector(".status-message");

function init() {
    messageContainer.style.backgroundColor = "$silver";
    message.textContent = "";
}
registerButton.addEventListener("click", () => {
    messageContainer.style.backgroundColor = "red";
    message.textContent = "This user already exist! Please input valid fields."
});

init();