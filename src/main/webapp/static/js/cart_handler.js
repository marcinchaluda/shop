import {cartButtonHandler} from "./cart_button_handler.js";
import {dataHandler} from "./data_handler.js";
import {util} from "./util.js";
import {buttonHandler} from "./buttons_handler.js";

const itemsContainer = document.querySelector("#items");
const usernameTitleContainer = document.querySelector("#username-cart-title");

const cartHandler = {
    activate: () => {
        generateItemsInBasket(1);
    }
}

const generateItemsInBasket = cartId => {
    dataHandler.getCart(cartId, response => {
        createListOfItems(response);
        cartButtonHandler.init(response);
    })
}

const createListOfItems = data => {
    let totalPrice = 0;
    for (let i = 0; i < data.products.length; i++) {
        const productDetails = data.products[i].product;
        const dataToInsert = {
            product_id: data.products[i].product.id,
            name: data.products[i].name,
            quantity: data.products[i].quantity,
            subTotalPrice: data.products[i].quantity * productDetails.unitPrice,
            currency: productDetails.currency
        }
        totalPrice += data.products[i].quantity * productDetails.unitPrice;
        addItemToDisplay(dataToInsert);
    }
    usernameTitleContainer.textContent = data.user.name + "'s cart";
    addTotalPriceContainer(totalPrice + " " + data.products[0].product.currency);
}

const addItemToDisplay = dataToInsert => {
    const cardDetails = util.createElementWithClasses("div", "card-details", "item", "flex-row");
    const name = util.createElementWithClasses("p", "item-name");
    name.innerText = dataToInsert.name;

    const quantityContainer = util.createElementWithClasses("div", "quantity", "flex-row");
    quantityContainer.setAttribute("product_id", dataToInsert.product_id)

    const minusButton = util.createElementWithClasses("a", "plus-minus", "decrease-quantity");
    minusButton.setAttribute("href", "#");
    const minusIcon = util.createElementWithClasses("i", "fas", "fa-minus");
    minusButton.appendChild(minusIcon);

    minusButton.addEventListener("click", function () {
        const quantityNumber = document.querySelector(`div[product_id='${dataToInsert.product_id}'] p`);
        let quantity = parseInt(quantityNumber.textContent);
        quantity = Math.max((quantity - 1), 1);
        quantityNumber.textContent = quantity.toString();

        buttonHandler.updateProductInCart(1, dataToInsert.product_id, quantity) //TODO hardcode cartID
    })

    const quantityValue = util.createElementWithClasses("p", "number");
    quantityValue.innerText = dataToInsert.quantity;

    const plusButton = util.createElementWithClasses("a", "plus-minus", "increase-quantity");
    plusButton.setAttribute("href", "#");
    const plusIcon = util.createElementWithClasses("i", "fas", "fa-plus");
    plusButton.appendChild(plusIcon);

    plusButton.addEventListener("click", function () {
        const quantityNumber = document.querySelector(`div[product_id='${dataToInsert.product_id}'] p`);
        let quantity = parseInt(quantityNumber.textContent);
        quantity = quantity + 1;
        quantityNumber.textContent = quantity.toString();

        buttonHandler.updateProductInCart(1, dataToInsert.product_id, quantity) //TODO hardcode cartID
    })

    quantityContainer.appendChild(minusButton);
    quantityContainer.appendChild(quantityValue);
    quantityContainer.appendChild(plusButton);

    const totalCost = util.createElementWithClasses("p", "total-cost");
    totalCost.innerText = " " + dataToInsert.subTotalPrice + " " + dataToInsert.currency + " ";

    const removeButton = util.createElementWithClasses("a", "remove-from-cart-button");
    const removeIcon = util.createElementWithClasses("i", "fas", "fa-trash-alt");
    removeButton.appendChild(removeIcon);
    removeButton.innerText = "Remove Item";

    cardDetails.appendChild(name);
    cardDetails.appendChild(quantityContainer);
    cardDetails.appendChild(totalCost);
    cardDetails.appendChild(removeButton);

    itemsContainer.appendChild(cardDetails);
}

const addTotalPriceContainer = totalPrice => {
    const totalPriceBox = util.createElementWithClasses("div", "card-details", "item", "flex-row");
    const number = util.createElementWithClasses("p", "number");
    number.innerText = `Total price: ${totalPrice} `;

    totalPriceBox.appendChild(number);
    itemsContainer.appendChild(totalPriceBox);
}

cartHandler.activate();