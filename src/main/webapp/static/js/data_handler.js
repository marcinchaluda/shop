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

    sendProductToCart: function (productDetails, callback) {
        this._api_post("api/cart", productDetails, response => {
            this._data['product-details'] = response;
            callback(response);
        });
    },

    getOrder: function (orderId, callback) {
        this._api_get(`api/orders/${orderId}`, response => {
            this._data['order'] = response;
            callback(response);
        })
    },

    updateOrder: function (orderId, orderDetails) {
        this._api_put(`api/orders/${orderId}`, orderDetails, response => {
            this._data['order-details'] = response;
        });
    },
}