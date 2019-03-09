package com.example.loaiaboelsooud.pharma;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface JsonPostPlaceHolderApi {
    @POST("facebook")
    Call<User> createUser(@Body User user);

}
