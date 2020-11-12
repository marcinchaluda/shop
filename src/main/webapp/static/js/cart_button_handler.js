import {dataHandler} from "./data_handler.js";

const setSameButton = document.querySelector("#set-same-button");
const submitButton = document.querySelector("#form-submit-button");

const countryBill = document.querySelector("#country");
const cityBill = document.querySelector("#city");
const zipCodeBill = document.querySelector("#zip-code");
const streetBill = document.querySelector("#street");
const localBill = document.querySelector("#local");

const countryShip = document.querySelector("#ship-country");
const cityShip = document.querySelector("#ship-city");
const zipCodeShip = document.querySelector("#ship-zip-code");
const streetShip = document.querySelector("#ship-street");
const localShip = document.querySelector("#ship-local");

export const cartButtonHandler = {
    init: cart => {
        setUpAllFieldsFromDatabase(cart.user.shippingAddress.id, cart.user.billingAddress.id);
        setSameButton.addEventListener("click", setAllFieldsWithSameValues);
        submitButton.addEventListener("click", () => {
            checkFieldsAndCreateNewOrder(cart.user.shippingAddress.id, cart.id);
        });
    },
    initWithoutSubmit: userId => {
        setSameButton.addEventListener("click", setAllFieldsWithSameValues);
    }
}

const setUpAllFieldsFromDatabase = (shipId, billId) => {
    dataHandler.getAddress(shipId, setUpShipAddress);
    dataHandler.getAddress(billId, setUpBillAddress);
}

const setUpShipAddress = data => {
    countryShip.setAttribute("value", data.country);
    cityShip.setAttribute("value", data.city);
    zipCodeShip.setAttribute("value", data.zipCode);
    streetShip.setAttribute("value", data.street);
    localShip.setAttribute("value", data.localNumber);
}

const setUpBillAddress = data => {
    countryBill.setAttribute("value", data.country);
    cityBill.setAttribute("value", data.city);
    zipCodeBill.setAttribute("value", data.zipCode);
    streetBill.setAttribute("value", data.street);
    localBill.setAttribute("value", data.localNumber);
}

const setAllFieldsWithSameValues = () => {
    countryShip.setAttribute("value", countryBill.value);
    cityShip.setAttribute("value", cityBill.value);
    zipCodeShip.setAttribute("value", zipCodeBill.value);
    streetShip.setAttribute("value", streetBill.value);
    localShip.setAttribute("value", localBill.value);
}

const checkFieldsAndCreateNewOrder = (addressId, cartId) => {
    const address = '{"country": "'+countryShip.value+'", "city": "'+cityShip.value+'", "zipCode": "'+zipCodeShip.value+'", "street": "'+streetShip.value+'", "localNumber": '+localShip.value+', "id": '+addressId+'}';
    if (address != null) {
        let addressParsed = JSON.parse(address);
        dataHandler.updateAddress(addressParsed, addressId, () => {});
    }
    dataHandler.getCart(cartId, response => generateNewOrder(response));
}

const generateNewOrder = response => {
    const newOrder = "{\"paid\": false, \"cart\": "+ JSON.stringify(response) +"}";
    dataHandler.postOrder(JSON.parse(newOrder), response => {
        window.location.replace("/payment/" + response.id);
    })
}
