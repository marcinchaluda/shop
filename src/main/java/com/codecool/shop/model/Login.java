package com.codecool.shop.model;

import com.google.gson.annotations.SerializedName;

public class Login {

    @SerializedName(value="email")
    private String email;

    @SerializedName(value="password")
    private String password;

    public Login(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return String.format("email: %1$s,", this.email);
    }
}
