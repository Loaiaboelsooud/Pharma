package com.example.loaiaboelsooud.pharma;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface JsonPostPlaceHolderApi {
    @POST("facebook")
    Call<UserResponse> createUser(@Body User user);


    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "logout", hasBody = true)
    Call<Void> logoutUser(@Field("token") String token);

    @GET("user")
    Call<UserResponse> getUser(@Query("token") String token);

    @PUT("refresh")
    Call<UserResponse> refreshToken(@Query("token") String token);


}
