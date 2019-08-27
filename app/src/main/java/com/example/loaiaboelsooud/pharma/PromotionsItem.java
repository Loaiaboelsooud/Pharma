package com.example.loaiaboelsooud.pharma;

import com.google.gson.annotations.SerializedName;

public class PromotionsItem {
    @SerializedName("company")
    String company;
    @SerializedName("created_at")
    String createdAt;
    @SerializedName("image_url")
    String image;
    @SerializedName("description")
    String description;
    @SerializedName("id")
    int id;
    @SerializedName("name")
    String name;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
