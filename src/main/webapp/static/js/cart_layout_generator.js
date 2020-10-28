import {layoutGenerator} from "./main_layout_generator.js";

const layout = layoutGenerator;
const cartContainer = document.querySelector(".content");
const cartForm= document.querySelector(".content form");
let productQuantity = 1;

export const cartGenerator = {
    createProductInfo: function (productJson) {
        const cart = layout.createElementWithClasses("div", "user-items", "flex-col");
        const cartHeader = createCartHeader();
        const items = createItem(productJson);

        cart.appendChild(cartHeader);
        cart.appendChild(items);

        cartForm.appendChild(cart);
        cartContainer.appendChild(cartForm);
    }
}

function createCartHeader() {
    const cartHeader = layout.createElementWithClasses("div", "cart-header", "flex-row");
    const headerTitle = document.createElement("h1");
    const removeBasketBtn = document.createElement("a");
    removeBasketBtn.href = "#";
    removeBasketBtn.innerHTML = "<i class=\"fas fa-trash-alt\"></i>";
    removeBasketBtn.textContent = "Remove Basket";
    cartHeader.appendChild(headerTitle);
    cartHeader.appendChild(removeBasketBtn);
    return cartHeader;
}

function createItem(productJson) {
    const items = layout.createElementWithClasses("div", "items", "flex-col");
    const cardDetails = layout.createElementWithClasses("div", "card-details", "flex-row");

    const itemName = createItemName(productJson);
    const quantity = createQuantity();
    const totalCost = createTotalCost(productJson);
    const removeItem = createRemoveItem();

    cardDetails.appendChild(removeItem);
    cardDetails.appendChild(itemName);
    cardDetails.appendChild(quantity);
    cardDetails.appendChild(totalCost);

    items.appendChild(cardDetails);
    return items;
}

function createItemName(productJson) {
    const itemName = layout.createElementWithClasses("p", "item-name");
    itemName.textContent = productJson.name;
    return itemName;
}

function createQuantity() {
    const quantity = layout.createElementWithClasses("div", "quantity", "flex-row");
    const minus = document.createElement("a");
    minus.href = "#";
    minus.innerHTML = "<i class=\"fas fa-minus\"></i>";
    const number = layout.createElementWithClasses("p", "number");
    number.textContent = productQuantity;
    const plus = document.createElement("a");
    plus.href = "#";
    plus.innerHTML = "<i class=\"fas fa-plus\"></i>";

    quantity.appendChild(minus);
    quantity.appendChild(number);
    quantity.appendChild(plus);
    return quantity;
}

function createTotalCost(productJson) {
    const totalCost = layout.createElementWithClasses("div", "total-cost");
    // const cost = productJson.quantity * productJson.unitPrice;
    // totalCost.textContent = cost
    totalCost.textContent += productJson.currency;
    return totalCost;
}

function createRemoveItem() {
    const removeItem = document.createElement("a");
    removeItem.href = "#";
    removeItem.innerHTML = "<i class=\"fas fa-trash-alt\"></i>";
    removeItem.textContent = "Remove Item";
    return removeItem;
}
