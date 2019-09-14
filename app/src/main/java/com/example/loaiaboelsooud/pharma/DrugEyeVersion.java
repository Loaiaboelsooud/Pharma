package com.example.loaiaboelsooud.pharma;

import com.google.gson.annotations.SerializedName;

public class DrugEyeVersion {
    @SerializedName("last_updated")
    private long drugEyeVersion;

    public long getDrugEyeVersion() {
        return drugEyeVersion;
    }

    public void setDrugEyeVersion(long drugEyeVersion) {
        this.drugEyeVersion = drugEyeVersion;
    }
}
