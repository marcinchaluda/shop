import {cartButtonHandler} from "./cart_button_handler.js";
import {dataHandler} from "./data_handler.js";

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

const addTotalPriceContainer = totalPrice => {
    itemsContainer.innerHTML += `<div class="card-details item flex-row">
                        <p class="number">Total price: ` + totalPrice + `</p>
                    </div>`
}

const addItemToDisplay = dataToInsert => {
    itemsContainer.innerHTML += `<div class="card-details item flex-row">
                        <p class="item-name">` + dataToInsert.name + `</p>
                        <div class="quantity flex-row">
                            <a class="plus-minus" href="#" class="decrease-quantity"><i class="fas fa-minus"></i></a>
                            <p class="number">` + dataToInsert.quantity + `</p>
                            <a class="plus-minus" href="#" class="increase-quantity"><i class="fas fa-plus"></i></a>
                        </div>
                        <p class="total-cost">` + dataToInsert.subTotalPrice + " " + dataToInsert.currency + `</p>
                        <a href="#" class="remove-from-cart-button"><i class="fas fa-trash-alt"></i>Remove Item</a>
                    </div>`
}


cartHandler.activate();