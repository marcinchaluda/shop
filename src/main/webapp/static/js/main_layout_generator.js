import { navButtonHandler } from "./buttons_handler.js";

const container = document.querySelector(".container");

export const layoutGenerator = {
    createProductCards: function (products, category) {
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
        const cardButton = createButtonElement.call(this);

        cardDetails.id = product.id
        cardDetails.appendChild(cardTitle);
        cardDetails.appendChild(imageContainer);
        cardDetails.appendChild(cardHeader);
        cardDetails.appendChild(cardPrice);
        cardDetails.appendChild(cardButton);
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
        button.addEventListener("click", function() {
            const productId = document.querySelector(".card-details").id
            navButtonHandler.addProductToCart(productId)
            // navButtonHandler.showProduct(productId);
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
