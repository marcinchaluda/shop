import { buttonHandler } from "./buttons_handler.js";
import { util } from "./util.js";

const container = document.querySelector(".container");

export const layoutGenerator = {
    createProductCards: function (products) {
        const cardContainer = util.createElementWithClasses("div", "flex-row");

        products.forEach(product => {
            cardContainer.appendChild(this.createProductCard(product));

        });

        container.appendChild(cardContainer);
        handleAddToCartButton();
        return cardContainer;
    },

    createProductCard: function (product) {
        const card = util.createElementWithClasses("div", "card");
        const cardDetails = util.createElementWithClasses("div", "card-details", "flex-col");
        const cardTitle = createNameElement.call(this, product);
        const imageContainer = createImageElement.call(this, product);
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
    const cardTitle = util.createElementWithClasses("h4", "card-title");
    cardTitle.innerHTML = product.name;
    return cardTitle;
}

function createImageElement(product) {
    const imageContainer = util.createElementWithClasses("div", "image-container");
    const image = util.createElementWithClasses("img", "image");
    image.src = product.image;
    imageContainer.appendChild(image);
    return imageContainer;
}

function createDescriptionElement(product) {
    const cardHeader = util.createElementWithClasses("div", "card-header");
    const cardText = util.createElementWithClasses("p", "card-text");
    cardText.textContent = product.description;
    cardHeader.appendChild(cardText);
    return cardHeader;
}

function createPriceElement(product) {
    const cardPrice = util.createElementWithClasses("div", "card-price");
    const lead = util.createElementWithClasses("p", "lead");
    const price = product.unitPrice + " " + product.currency;
    lead.textContent = price;
    cardPrice.appendChild(lead)
    return cardPrice;
}

function createAddingBar(product) {
    const addingBar = util.createElementWithClasses("div", "adding-bar");
    const cardButton = createButtonElement.call(this);
    const quantity = createQuantityChooser.call(this, product);

    addingBar.appendChild(cardButton);
    addingBar.appendChild(quantity);

    return addingBar;
}

function createButtonElement() {
    const cardButton = util.createElementWithClasses("div", "card-button");
    const button = util.createElementWithClasses("a", "btn");
    button.href = "#";
    // const buttonIcon = util.createElementWithClasses("i", "fas", "fa-shopping-cart");
    button.innerHTML = "<i class=\"fas fa-shopping-cart\"></i>";
    // button.appendChild(buttonIcon);
    button.textContent += "Add to cart";
    cardButton.appendChild(button);
    return cardButton;
}

function createQuantityChooser(product) {
    const quantity = util.createElementWithClasses("div", "quantity-container");

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
