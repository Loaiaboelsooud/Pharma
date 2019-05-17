package com.example.loaiaboelsooud.pharma;

import com.google.gson.annotations.SerializedName;

public class PrescriptionsCommentResponse {

    @SerializedName("data")
    private PrescriptionsComments prescriptionsComment;

    @SerializedName("meta")
    private MetaData metaData;

    public MetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }

    public PrescriptionsComments getPrescriptionsComment() {
        return prescriptionsComment;
    }

    public void setPrescriptionsComments(PrescriptionsComments prescriptionsComment) {
        this.prescriptionsComment = prescriptionsComment;
    }
}
