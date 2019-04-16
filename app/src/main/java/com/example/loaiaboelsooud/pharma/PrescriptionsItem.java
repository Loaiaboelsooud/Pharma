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
    String id;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public UserResponse getUserResponse() {
        return userResponse;
    }

    public void setUserResponse(UserResponse userResponse) {
        this.userResponse = userResponse;
    }
}
