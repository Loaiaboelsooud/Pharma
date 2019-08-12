package com.example.loaiaboelsooud.pharma;

import com.google.gson.annotations.SerializedName;

public class JobsItemResponse {
    @SerializedName("data")
    private JobsItem jobsItem;

    @SerializedName("meta")
    private MetaData metaData;

    public JobsItem getJobsItem() {
        return jobsItem;
    }

    public void setJobsItem(JobsItem jobsItem) {
        this.jobsItem = jobsItem;
    }

    public MetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }
}
