package com.example.loaiaboelsooud.pharma;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public interface JsonPostPlaceHolderApi {
    @POST("auth/facebook")
    Call<UserResponse> createUser(@Body User user);


    // @FormUrlEncoded
    //@HTTP(method = "DELETE", path = "auth/logout", hasBody = true)
    @DELETE("auth/logout")
    Call<Void> logoutUser(@Header("Authorization") String token);

    @GET("auth/user")
    Call<UserResponse> getUser(@Header("Authorization") String token);

    @PUT("auth/refresh")
    Call<UserResponse> refreshToken(@Header("Authorization") String token);

    @Multipart
    @POST("prescriptions")
    Call<PrescriptionsResponse> addPrescriptions(@Part MultipartBody.Part picture, @Part("description") RequestBody description, @Header("Authorization") String token);

    @GET("prescriptions")
    Call<PrescriptionsResponse> getAllPrescriptions();

}
