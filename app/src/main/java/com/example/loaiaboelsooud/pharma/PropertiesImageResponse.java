package com.example.loaiaboelsooud.pharma;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PropertiesImageResponse {
    @SerializedName("data")
    private List<PropertiesImageItem> propertiesImagesItem;

    public List<PropertiesImageItem> getPropertiesImagesItem() {
        return propertiesImagesItem;
    }

    public void setPropertiesImagesItem(List<PropertiesImageItem> propertiesImagesItem) {
        this.propertiesImagesItem = propertiesImagesItem;
    }

    public class PropertiesImageItem {

        @SerializedName("url")
        String image;
        @SerializedName("id")
        int id;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
