package com.example.loaiaboelsooud.pharma;

import com.google.gson.annotations.SerializedName;

import java.util.Calendar;

public class User {
    @SerializedName("id")
    private String id;
    @SerializedName("avatar")
    private String avatar;
    @SerializedName("facebook_id")
    private String facebookID;
    @SerializedName("name")
    private String name;
    @SerializedName("username")
    private String email;
    @SerializedName("qualification")
    private String qualification;
    @SerializedName("job")
    private String job;
    @SerializedName("city")
    private String city;
    @SerializedName("facebook_token")
    private String facebookToken;
    @SerializedName("token")
    private String token;
    @SerializedName("expires_in")
    private long expiresIn;

    private Calendar expireDate;

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getFacebookID() {
        return facebookID;
    }

    public void setFacebookID(String facebookID) {
        this.facebookID = facebookID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFacebookToken() {
        return facebookToken;
    }

    public void setFacebookToken(String facebookToken) {
        this.facebookToken = facebookToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Calendar getExpireDate() {
        return expireDate;
    }

    public void setExpireDate() {
        Calendar expireDate = Calendar.getInstance();
        expireDate.setTime(expireDate.getTime());
        expireDate.set(Calendar.DATE, expireDate.get(Calendar.DATE) + 7);
        this.expireDate = expireDate;
    }
}
