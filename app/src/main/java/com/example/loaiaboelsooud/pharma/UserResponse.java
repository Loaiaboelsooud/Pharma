package com.example.loaiaboelsooud.pharma;

import com.google.gson.annotations.SerializedName;

public class UserResponse {
    ;
    @SerializedName("data")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
