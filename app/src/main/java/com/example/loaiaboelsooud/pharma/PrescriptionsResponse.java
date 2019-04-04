package com.example.loaiaboelsooud.pharma;

import com.google.gson.annotations.SerializedName;

public class PrescriptionsResponse {

    @SerializedName("data")
    private PrescriptionsItem prescriptionsItem;

    public PrescriptionsItem getPrescriptionsItem() {
        return prescriptionsItem;
    }

    public void setPrescriptionsItem(PrescriptionsItem prescriptionsItem) {
        this.prescriptionsItem = prescriptionsItem;
    }
}
