package com.example.loaiaboelsooud.pharma;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DrugEyeResponse {
    @SerializedName("data")
    private List<DrugEyeItem> drugEyeItems;

    public List<DrugEyeItem> getDrugEyeItems() {
        return drugEyeItems;
    }

    public void setDrugEyeItems(List<DrugEyeItem> drugEyeItems) {
        this.drugEyeItems = drugEyeItems;
    }
}
