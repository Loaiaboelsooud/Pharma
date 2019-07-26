package com.example.loaiaboelsooud.pharma;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PropertiesItem {
    @SerializedName("address")
    private String address;
    @SerializedName("city")
    private String city;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("description")
    private String description;
    @SerializedName("id")
    private int id;
    @SerializedName("listed_for")
    private String listedFor;
    @SerializedName("mobile_numbers")
    private String[] mobileNumber;
    @SerializedName("landline_numbers")
    private String[] landLineNumbers;
    @SerializedName("name")
    private String name;
    @SerializedName("notes")
    private String notes;
    @SerializedName("owner")
    private UserResponse userResponse;
    @SerializedName("price")
    private int price;
    @SerializedName("region")
    private String region;
    @SerializedName("type")
    private String type;
    @SerializedName("images")
    private Images images;
    @SerializedName("updated_at")
    private String updatedAt;

    public class Images {
        @SerializedName("data")
        private List<Data> data;

        public List<Data> getData() {
            return data;
        }

        public void setData(List<Data> data) {
            this.data = data;
        }

        public class Data {
            @SerializedName("id")
            private int id;

            @SerializedName("url")
            private String url;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

        }
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getListedFor() {
        return listedFor;
    }

    public void setListedFor(String listedFor) {
        this.listedFor = listedFor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserResponse getUserResponse() {
        return userResponse;
    }

    public void setUserResponse(UserResponse userResponse) {
        this.userResponse = userResponse;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String[] getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String[] mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String[] getLandLineNumbers() {
        return landLineNumbers;
    }

    public void setLandLineNumbers(String[] landLineNumbers) {
        this.landLineNumbers = landLineNumbers;
    }
}
