import {cartButtonHandler} from "./cart_button_handler.js";
import {cartFormHandler} from "./cart_form_handler.js";
import {dataHandler} from "./data_handler.js";

const itemsContainer = document.querySelector("#items");
const usernameTitleContainer = document.querySelector("#username-cart-title");

const cartHandler = {
    activate: () => {
        generateItemsInBasket(1);
        cartHandler.initAllButtonHandlers();
    },
    initAllButtonHandlers: () => {

    },

}

const generateItemsInBasket = (cartId) => {
    dataHandler.getCart(cartId, (response) => {
        createListOfItems(response);
    })
}

const createListOfItems = (data) => {
    for (let i = 0; i < data.products.length; i++) {
        const productDetails = data.products[i].product;
        const dataToInsert = {
            name: data.products[i].name,
            quantity: data.products[i].quantity,
            totalPrice: data.products[i].quantity * productDetails.unitPrice
        }
        addItemToDisplay(dataToInsert);
    }
    usernameTitleContainer.textContent = data.user.name + "'s cart";
}

const addItemToDisplay = (dataToInsert) => {
    itemsContainer.innerHTML += `<div class="card-details item flex-row">
                        <p class="item-name">` + dataToInsert.name + `</p>
                        <div class="quantity flex-row">
                            <a class="plus-minus" href="#" class="decrease-quantity"><i class="fas fa-minus"></i></a>
                            <p class="number">` + dataToInsert.quantity + `</p>
                            <a class="plus-minus" href="#" class="increase-quantity"><i class="fas fa-plus"></i></a>
                        </div>
                        <p class="total-cost">` + dataToInsert.totalPrice + `</p>
                        <a href="#" class="remove-from-cart-button"><i class="fas fa-trash-alt"></i>Remove Item</a>
                    </div>`
}


cartHandler.activate();