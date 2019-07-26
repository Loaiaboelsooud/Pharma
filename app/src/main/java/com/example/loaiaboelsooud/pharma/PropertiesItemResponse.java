package com.example.loaiaboelsooud.pharma;

import com.google.gson.annotations.SerializedName;

public class PropertiesItemResponse {
    @SerializedName("data")
    private PropertiesItem propertiesItem;

    public PropertiesItem getPropertiesItem() {
        return propertiesItem;
    }

    public void setPropertiesItem(PropertiesItem propertiesItem) {
        this.propertiesItem = propertiesItem;
    }

}
