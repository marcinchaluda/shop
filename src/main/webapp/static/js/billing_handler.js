import {cartButtonHandler} from "./cart_button_handler.js";
import {dataHandler} from "./data_handler.js";

const countryBill = document.querySelector("#country");
const cityBill = document.querySelector("#city");
const zipCodeBill = document.querySelector("#zip-code");
const streetBill = document.querySelector("#street");
const localBill = document.querySelector("#local");
const idBill = document.querySelector("#bill-id").getAttribute("address_id");

const countryShip = document.querySelector("#ship-country");
const cityShip = document.querySelector("#ship-city");
const zipCodeShip = document.querySelector("#ship-zip-code");
const streetShip = document.querySelector("#ship-street");
const localShip = document.querySelector("#ship-local");
const idShip = document.querySelector("#ship-id").getAttribute("address_id");

const setPhoneNumberButton = document.querySelector("#set-phone-number");
const setAddresses = document.querySelector("#set-addresses");

const billingHandler = {
    init: () => {
        let userId = document.location.pathname.split("/")[2];
        cartButtonHandler.initWithoutSubmit(userId);
        setPhoneNumberButton.addEventListener("click", () => {
            dataHandler.getUser(userId, response => {
                response.phoneNumber = document.querySelector("#phoneNumber").value;
                dataHandler.updateUser(response, userId, () => {})
            })
        });
        setAddresses.addEventListener("click", setUpAddresses);
    }
}

const setUpAddresses = () => {
    setUpAddress(countryShip.value, cityShip.value, zipCodeShip.value, streetShip.value, localShip.value, idShip);
    setUpAddress(countryBill.value, cityBill.value, zipCodeBill.value, streetBill.value, localBill.value, idBill);
}

const setUpAddress = (country, city, zipCode, street, local, id) => {
    const address = '{"country": "' +country
        +'", "city": "' +city
        +'", "zipCode": "'+zipCode
        +'", "street": "'+street
        +'", "localNumber": '+local
        +', "id": '+id+'}';
    if (address != null) {
        let addressParsed = JSON.parse(address);
        dataHandler.updateAddress(addressParsed, id, () => {});
    };
}

billingHandler.init();
