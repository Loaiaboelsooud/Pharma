package com.example.loaiaboelsooud.pharma;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JobsItemsResponse {
    @SerializedName("data")
    private List<JobsItem> jobsItems;

    @SerializedName("meta")
    private MetaData metaData;

    public List<JobsItem> getJobsItems() {
        return jobsItems;
    }

    public void setJobsItems(List<JobsItem> jobsItems) {
        this.jobsItems = jobsItems;
    }

    public MetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }

}
