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
}
