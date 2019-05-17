package com.example.loaiaboelsooud.pharma;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PrescriptionsCommentsResponse {

    @SerializedName("data")
    private List<PrescriptionsComments> prescriptionsComments;

    @SerializedName("meta")
    private MetaData metaData;

    public MetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }

    public List<PrescriptionsComments> getPrescriptionsComments() {
        return prescriptionsComments;
    }

    public void setPrescriptionsComments(List<PrescriptionsComments> prescriptionsComments) {
        this.prescriptionsComments = prescriptionsComments;
    }
}


