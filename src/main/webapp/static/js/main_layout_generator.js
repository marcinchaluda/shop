import { buttonHandler } from "./buttons_handler.js";

const container = document.querySelector(".container");

export const layoutGenerator = {
    createProductCards: function (products) {
        const cardContainer = this.createElementWithClasses("div", "flex-row");

        products.forEach(product => {
            cardContainer.appendChild(this.createProductCard(product));

        });

        container.appendChild(cardContainer);
        handleAddToCartButton();
        return cardContainer;
    },

    createProductCard: function (product) {
        const card = this.createElementWithClasses("div", "card");
        const cardDetails = this.createElementWithClasses("div", "card-details", "flex-col");
        const cardTitle = createNameElement.call(this, product);
        const imageContainer = createImageElement.call(this);
        const cardHeader = createDescriptionElement.call(this, product);
        const cardPrice = createPriceElement.call(this, product);
        const cardAddingBar = createAddingBar.call(this, product);

        cardDetails.id = product.id
        cardDetails.appendChild(cardTitle);
        cardDetails.appendChild(imageContainer);
        cardDetails.appendChild(cardHeader);
        cardDetails.appendChild(cardPrice);
        cardDetails.appendChild(cardAddingBar);
        card.appendChild(cardDetails);

        return card;
    },

    createElementWithClasses: function (element, ...args) {
        const newElement = document.createElement(element);
        args.forEach(arg => {
            newElement.classList.add(arg);
        });
        return newElement;
    },

    removeContent: function (element) {
        element.innerHTML = '';
    }
}

function handleAddToCartButton() {
    const addToCartButtons = document.querySelectorAll(".btn");
    addToCartButtons.forEach(button => {
        button.addEventListener("click", function () {
            const productId = document.querySelector(".card-details").id
            const input = document.querySelector(`div[id='${productId}'] input`);
            const quantity = parseInt(input.getAttribute("value"));

            buttonHandler.addProductToCart(1, productId, quantity) //TODO hardcode cartID
        })
    })
}

function createNameElement(product) {
    const cardTitle = this.createElementWithClasses("h4", "card-title");
    cardTitle.innerHTML = product.name;
    return cardTitle;
}

function createImageElement() {
    const imageContainer = this.createElementWithClasses("div", "image-container");
    const image = this.createElementWithClasses("img", "image");
    // image.src = product.image_path;
    imageContainer.appendChild(image);
    return imageContainer;
}

function createDescriptionElement(product) {
    const cardHeader = this.createElementWithClasses("div", "card-header");
    const cardText = this.createElementWithClasses("p", "card-text");
    cardText.textContent = product.description;
    cardHeader.appendChild(cardText);
    return cardHeader;
}

function createPriceElement(product) {
    const cardPrice = this.createElementWithClasses("div", "card-price");
    const lead = this.createElementWithClasses("p", "lead");
    // const price = product.defaultPrice + product.currency;
    lead.textContent = product.currency;
    cardPrice.appendChild(lead)
    return cardPrice;
}

function createAddingBar(product) {
    const addingBar = this.createElementWithClasses("div", "adding-bar");
    const cardButton = createButtonElement.call(this);
    const quantity = createQuantityChooser.call(this, product);

    addingBar.appendChild(cardButton);
    addingBar.appendChild(quantity);

    return addingBar;
}

function createButtonElement() {
    const cardButton = this.createElementWithClasses("div", "card-button");
    const button = this.createElementWithClasses("a", "btn");
    button.href = "#";
    // const buttonIcon = this.createElementWithClasses("i", "fas", "fa-shopping-cart");
    button.innerHTML = "<i class=\"fas fa-shopping-cart\"></i>";
    // button.appendChild(buttonIcon);
    button.textContent += "Add to cart";
    cardButton.appendChild(button);
    return cardButton;
}

function createQuantityChooser(product) {
    const quantity = this.createElementWithClasses("div", "quantity-container");

    const inputQuantity = document.createElement("input");
    inputQuantity.setAttribute("type", "number");
    inputQuantity.setAttribute("name", "quantity");
    inputQuantity.setAttribute("min", "1");
    inputQuantity.setAttribute("value", "1");
    inputQuantity.disabled = true;

    const leftButton = document.createElement("button");
    leftButton.innerText = "-";
    leftButton.addEventListener("click", function () {
        const input = document.querySelector(`div[id='${product.id}'] input`);
        let quantityValue = input.getAttribute("value");
        quantityValue = Math.max(quantityValue - 1, 1);
        input.setAttribute("value", quantityValue.toString());
    })

    const rightButton = document.createElement("button");
    rightButton.innerText = "+";
    rightButton.addEventListener("click", function () {
        const input = document.querySelector(`div[id='${product.id}'] input`);
        let quantityValue = input.getAttribute("value");
        quantityValue++;
        input.setAttribute("value", quantityValue.toString());
    })

    quantity.appendChild(leftButton);
    quantity.appendChild(inputQuantity);
    quantity.appendChild(rightButton);

    return quantity;
}
