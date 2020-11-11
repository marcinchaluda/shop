package com.codecool.shop.model;

import com.google.gson.annotations.SerializedName;
import org.junit.jupiter.api.Order;

import java.util.Objects;

public class Address extends BaseModel {

    @SerializedName(value="country")
    private String country;

    @SerializedName(value="city")
    private String city;

    @SerializedName(value="zipCode")
    private String zipCode;

    @SerializedName(value="street")
    private String street;

    @SerializedName(value="localNumber")
    private int localNumber;

    public Address(String country, String city, String zipCode, String street, int localNumber) {
        this.country = country;
        this.city = city;
        this.zipCode = zipCode;
        this.street = street;
        this.localNumber = localNumber;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getLocalNumber() {
        return localNumber;
    }

    public void setLocalNumber(int localNumber) {
        this.localNumber = localNumber;
    }

    @Override
    public String toString() {
        return String.format("id: %1$s, " +
                        "country: %2$s, " +
                        "city: %3$s, " +
                        "zipCode: %4$s, " +
                        "street: %5$s, " +
                        "localNumber: %6$d",
                this.id,
                this.country,
                this.city,
                this.zipCode,
                this.street,
                this.localNumber);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return localNumber == address.localNumber &&
                Objects.equals(country, address.country) &&
                Objects.equals(city, address.city) &&
                Objects.equals(zipCode, address.zipCode) &&
                Objects.equals(street, address.street);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, city, zipCode, street, localNumber);
    }
}
