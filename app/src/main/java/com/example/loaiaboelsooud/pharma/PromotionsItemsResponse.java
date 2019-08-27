package com.example.loaiaboelsooud.pharma;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PromotionsItemsResponse {
    @SerializedName("data")
    private List<PromotionsItem> promotionsItem;

    @SerializedName("meta")
    private MetaData metaData;

    public List<PromotionsItem> getPromotionsItem() {
        return promotionsItem;
    }

    public void setPromotionsItem(List<PromotionsItem> promotionsItem) {
        this.promotionsItem = promotionsItem;
    }

    public MetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }
}
