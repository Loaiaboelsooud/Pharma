package com.example.loaiaboelsooud.pharma;

import com.google.gson.annotations.SerializedName;

public class PrescriptionsItem {
    @SerializedName("uploader")
    UserResponse userResponse;
    @SerializedName("image_url")
    String image;
    @SerializedName("description")
    String description;
    @SerializedName("id")
    int id;
    @SerializedName("created_at")
    String createdAt;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public UserResponse getUserResponse() {
        return userResponse;
    }

    public void setUserResponse(UserResponse userResponse) {
        this.userResponse = userResponse;
    }
}
