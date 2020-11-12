export const util = {
    createElementWithClasses: function (element, ...args) {
        const newElement = document.createElement(element);
        args.forEach(arg => {
            newElement.classList.add(arg);
        });
        return newElement;
    },

    removeContent: function (element) {
        element.innerHTML = "";
    },

    redirectToHomePage: function () {
        const homeButton = document.querySelector(".home-page");
        homeButton.addEventListener("click", () => {
            window.location.href = "/";
        });
    },

    validateUserName: function (username) {
        const regexName = new RegExp('^[A-Za-z0-9]+$');
        return username.match(regexName);
    },

    validateCardHolder: function (cardHolder) {
        const regexCardHolder = new RegExp('^[A-Za-z]*$');
        return cardHolder.match(regexCardHolder);
    },

    validateCardNumber: function (cardNumber) {
        const regexCardNumber = new RegExp("^[0-9]{16}");
        return cardNumber.match(regexCardNumber);
    },

    validateCode: function (code) {
        const regexPass = new RegExp("^[0-9]{3}");
        return code.match(regexPass);
    },

    validatePassword: function (password) {
        const regexPass = new RegExp("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}");
        return password.match(regexPass);
    },

    validateEmail: function (email) {
        const regexPass = new RegExp("[^@]+@[^\\.]+\\..+");
        return email.match(regexPass);
    },

}
