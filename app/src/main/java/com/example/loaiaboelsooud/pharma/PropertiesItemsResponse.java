package com.example.loaiaboelsooud.pharma;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class PropertiesItemsResponse {
    @SerializedName("data")
    private List<PropertiesItem> propertiesItems;

    @SerializedName("meta")
    private MetaData metaData;


    public List<PropertiesItem> getPropertiesItems() {
        return propertiesItems;
    }

    public void setPropertiesItems(List<PropertiesItem> propertiesItems) {
        this.propertiesItems = propertiesItems;
    }

    public MetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }


}
