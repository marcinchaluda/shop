export const dataHandler = {
    _data: {},

    _api_get: function (url, callback) {
        fetch(url, {
            method: 'GET',
            credentials:'same-origin'
        })
            .then(response => response.json())
            .then(json_response => callback(json_response))
    },

    _api_post: function (url, data, callback) {
        fetch(url, {
            method: 'POST',
            credentials: 'same-origin',
            body: JSON.stringify(data),
            cache: "no-cache",
            headers: new Headers({
                "content-type": "application/json",
                'accept': 'application/json'
            })
        })
            .then(response => response.json())
            .then(json_response => callback(json_response))
    },

    _api_patch: function (url, data, callback) {
        fetch(url, {
            method: 'PATCH',
            credentials: 'same-origin',
            body: JSON.stringify(data),
            cache: "no-cache",
            headers: new Headers({
                "content-type": "application/json",
            })
        })
            .then(response => callback(response));
    },

    _api_delete: function (url, data, callback) {
        fetch(url, {
            method: 'DELETE',
            credentials: 'same-origin',
            body: JSON.stringify(data),
            cache: "no-cache",
            headers: new Headers({
                "content-type": "application/json",
            })
        })
            .then(response => callback(response));
    },

    postDataGetResponse: (url, data, callback) => {
        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'accept': 'application/json'
            },
            body: JSON.stringify(data),
        })
            .then(response => response.json())
            .then(response => callback(response))
    },

    _api_put: function (url, data, callback) {
        fetch(url, {
            method: 'PUT',
            body: JSON.stringify(data),
            headers: new Headers({
                "content-type": "application/json",
                'accept': 'application/json'
            })
        })
            .then(response => callback(response));
    },

    getAllProducts: function (callback) {
        this._api_get("api/products", response => {
            this._data['products'] = response;
            callback(response)
        });
    },

    getProducts: function (category, sortOption, callback) {
        this._api_get(`api/products?sort=${category}&by=${sortOption}`, response => {
            this._data['products'] = response;
            callback(response)
        });
    },

    getProduct: function (productId, callback) {
        this._api_get(`api/products/${productId}`, response => {
            this._data['product'] = response;
            callback(response)
        });
    },

    increaseAmountOfProductInCart: function (productDetails, cartId) {
        this._api_patch(`api/carts/${cartId}?action=add`, productDetails, response => {
            this._data['product-details'] = response;
        });
    },

    updateAmountOfProductInCart: function (productDetails, cartId) {
        this._api_patch(`api/carts/${cartId}?action=update`, productDetails, response => {
            this._data['product-details'] = response;
        });
    },

    getUser: function (userId, callback) {
        this._api_get("../api/users/" + userId, response => {
            this._data['user-details'] = response;
            callback(response);
        })
    },

    updateUser: function (user, userId, callback) {
        this._api_put("../api/users/" + userId, user, response => {
            this._data['user-details'] = response;
            callback(response);
        })
    },

    getCart: function (cartId, callback) {
        this._api_get("api/carts/" + cartId, response => {
            this._data['cart-details'] = response;
            callback(response);
        })
    },

    postCart: function (cartDetails, callback) {
        this._api_post("api/carts", response => {
            this._data['cart-details'] = response;
            callback(response);
        })
    },

    getAddress: function (id, callback) {
        dataHandler._api_get("api/addresses/" + id, response => {
            this._data['address-details'] = response;
            callback(response);
        })
    },

    addNewAddress: (newAddress, callback) => {
        dataHandler._api_post("../api/addresses", response => {})
    },

    updateAddress: (newAddress, addressId, callback) => {
        dataHandler._api_put("../api/addresses/" + addressId, newAddress, response => {})
    },

    postOrder: function (orderDetails, callback) {
        dataHandler.postDataGetResponse("api/orders", orderDetails, response => {
            this._data['order-details'] = response;
            callback(response);
        });
    },

    getOrder: function (orderId, callback) {
        this._api_get(`api/orders/` + orderId, response => {
            this._data['order-details'] = response;
            callback(response);
        })
    },

    updateOrder: function (orderId, orderDetails) {
        this._api_put(`api/orders/${orderId}`, orderDetails, response => {
            this._data['order-details'] = response;
        });
    },

    patchOrder: function (orderId, orderDetails) {
        this._api_patch(`../api/orders/${orderId}`, orderDetails, response => {
            window.location.replace(`../summary/${orderId}`);
        });
    },

    postUser: function (data, callback) {
        this.postDataGetResponse("registration", data ,response => {
            this._data['user-details'] = response;
            callback(response);
        });
    },

    postLoggedUser: function (data, callback) {
        this.postDataGetResponse("login", data ,response => {
            this._data['user-details'] = response;
            callback(response);
        });
    },
}