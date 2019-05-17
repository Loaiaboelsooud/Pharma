package com.example.loaiaboelsooud.pharma;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PrescriptionsItemsResponse {

    @SerializedName("data")
    private List<PrescriptionsItem> prescriptionsItems;

    @SerializedName("meta")
    private MetaData metaData;

    public MetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }

    public List<PrescriptionsItem> getPrescriptionsItems() {
        return prescriptionsItems;
    }

    public void setPrescriptionsItems(List<PrescriptionsItem> prescriptionsItems) {
        this.prescriptionsItems = prescriptionsItems;
    }
}
