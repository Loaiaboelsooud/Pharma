package com.example.loaiaboelsooud.pharma;

import com.google.gson.annotations.SerializedName;

public class DrugEyeVersion {
    @SerializedName("last_updated")
    private Long drugEyeVersion;

    public Long getDrugEyeVersion() {
        return drugEyeVersion;
    }

    public void setDrugEyeVersion(Long drugEyeVersion) {
        this.drugEyeVersion = drugEyeVersion;
    }
}
