package com.example.loaiaboelsooud.pharma;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

public class PrescriptionsItem {
    //@SerializedName("uploader")
    //  UserResponse userResponse;
    @SerializedName("image")
    Bitmap image;
    @SerializedName("description")
    String description;
    @SerializedName("token")
    String token;

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
