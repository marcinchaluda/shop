package com.codecool.shop.model;

import com.google.gson.annotations.SerializedName;

public class User extends BaseDescribedModel {

    @SerializedName(value="email")
    private String email;

    @SerializedName(value="phoneNumber")
    private String phoneNumber;

    @SerializedName(value="billingAddress")
    private Address billingAddress;

    @SerializedName(value="shippingAddress")
    private Address shippingAddress;

    public User(String name, String email, String phoneNumber, Address billingAddress, Address shippingAddress) {
        super(name);
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.billingAddress = billingAddress;
        this.shippingAddress = shippingAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    @Override
    public String toString() {
        return String.format("id: %1$d, " +
                        "name: %2$s, " +
                        "email: %3$s, " +
                        "phoneNumber: %4$s",
                this.id,
                this.name,
                this.email,
                this.phoneNumber);
    }
}
