package com.example.loaiaboelsooud.pharma;

import com.google.gson.annotations.SerializedName;

public class PrescriptionsItemResponse {
    @SerializedName("data")
    private PrescriptionsItem prescriptionsItem;

    @SerializedName("meta")
    private MetaData metaData;

    public MetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }

    public PrescriptionsItem getPrescriptionsItem() {
        return prescriptionsItem;
    }

    public void setPrescriptionsItem(PrescriptionsItem prescriptionsItem) {
        this.prescriptionsItem = prescriptionsItem;
    }
}
