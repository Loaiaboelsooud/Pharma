package com.example.loaiaboelsooud.pharma;

import java.util.List;

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
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface JsonPostPlaceHolderApi {
    final String AUTHORIZATION = "Authorization";

    @POST("auth/facebook")
    Call<UserResponse> createUser(@Body User user);

    @DELETE("auth/logout")
    Call<Void> logoutUser(@Header(AUTHORIZATION) String token);

    @GET("auth/user")
    Call<UserResponse> getUser(@Header(AUTHORIZATION) String token);

    @PUT("auth/refresh")
    Call<UserResponse> refreshToken(@Header(AUTHORIZATION) String token);

    @Multipart
    @POST("prescriptions")
    Call<PrescriptionsItemResponse> addPrescriptions(@Part MultipartBody.Part picture, @Part("description") RequestBody description, @Header(AUTHORIZATION) String token);

    @GET
    Call<PrescriptionsItemsResponse> getAllPrescriptions(@Url String url);

    @GET
    Call<PrescriptionsCommentsResponse> getAllPrescriptionsComments(@Header(AUTHORIZATION) String token, @Url String url);

    @POST
    Call<PrescriptionsCommentResponse> addPrescriptionsComments(@Header(AUTHORIZATION) String token, @Url String url, @Query("comment") String comment);

    @GET
    Call<PropertiesItemsResponse> getAllProperties(@Url String url);

    @POST("properties")
    Call<PropertiesItemResponse> addProperties(@Header(AUTHORIZATION) String token, @Query("name") String name, @Query("city") String city,
                                               @Query("region") String region, @Query("address") String address, @Query("area") String area,
                                               @Query("listed_for") String listedFor, @Query("type") String type, @Query("price") int price,
                                               @Query("description") String description, @Query("notes") String notes,
                                               @Query("mobile_numbers[]") List<String> mobileNumber, @Query("landline_numbers[]") List<String> landLineNumbers);

    @GET()
    Call<PropertiesItemsResponse> getFilteredProperties(@Url String url, @Header(AUTHORIZATION) String token, @Query("listed_for[]") String selling,
                                                        @Query("listed_for[]") String renting, @Query("type") String pharmacy, @Query("type") String wareHouse,
                                                        @Query("type") String hospital, @Query("type") String factory);

    @GET
    Call<PropertiesItemResponse> getPropertyById(@Header(AUTHORIZATION) String token, @Url String url);

}
