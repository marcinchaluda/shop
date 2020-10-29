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
                "content-type": "application/json"
            })
        })
            .then(response => response.json())
            .then(json_response => callback(json_response))
    },

    _api_put: function (url, data, callback) {
        fetch(url, {
            method: 'PUT',
            credentials: 'same-origin',
            body: JSON.stringify(data),
            cache: "no-cache",
            headers: new Headers({
                "content-type": "application/json"
            })
        })
            .then(response => response.json())
            .then(json_response => callback(json_response))
    },

    getAllProducts: function (callback) {
        this._api_get("api/products", response => {
            this._data['products'] = response;
            callback(response)
        });
    },

    getProducts: function (category, sortOption, callback) {
        this._api_get("api/products?sort=" + category + "&by=" + sortOption, response => {
            this._data['products'] = response;
            callback(response)
        });
    },

    getProduct: function (productId, callback) {
        this._api_get("api/products/" + productId, response => {
            this._data['product'] = response;
            callback(response)
        });
    },

    sendProductToCart: function (productDetails, callback) {
        this._api_post("api/cart", productDetails, response => {
            this._data['product-details'] = response;
            callback(response);
        });
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
        this._api_get("api/addresses/" + id, response => {
            this._data['address-details'] = response;
            callback(response);
        })
    },

    updateAddress: function (newAddress, addressId, callback) {
        this._api_put("api/addresses/" + addressId, response => {
            this._data['address-details'] = response;
            callback(response);
        })
    },

    postOrder: function (orderDetails, callback) {
        this._api_post("api/orders", response => {
            this._data['order-details'] = response;
            callback(response);
        })
    }
}