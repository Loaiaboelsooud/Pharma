package com.example.loaiaboelsooud.pharma;

import com.google.gson.annotations.SerializedName;

public class PrescriptionsComments {
    @SerializedName("id")
    private String id;
    @SerializedName("comment")
    private String comment;
    @SerializedName("commenter")
    private UserResponse userResponse;

    private static final int viewType = 0;

    public void setUserResponse(UserResponse userResponse) {
        this.userResponse = userResponse;
    }

    public UserResponse getUserResponse() {
        return userResponse;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public static int getViewType() {
        return viewType;
    }
}
