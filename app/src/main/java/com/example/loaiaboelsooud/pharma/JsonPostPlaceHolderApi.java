package com.example.loaiaboelsooud.pharma;

import java.util.List;
import java.util.Map;

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
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface JsonPostPlaceHolderApi {


    @POST(PharmaConstants.API + "auth/facebook")
    Call<UserResponse> createUser(@Body User user);

    @DELETE(PharmaConstants.API + "auth/logout")
    Call<Void> logoutUser(@Header(PharmaConstants.AUTHORIZATION) String token);

    @GET(PharmaConstants.API + "auth/user")
    Call<UserResponse> getUser(@Header(PharmaConstants.AUTHORIZATION) String token);

    @PUT(PharmaConstants.API + "auth/refresh")
    Call<UserResponse> refreshToken(@Header(PharmaConstants.AUTHORIZATION) String token);

    @Multipart
    @POST(PharmaConstants.API + "prescriptions")
    Call<PrescriptionsItemResponse> addPrescriptions(@Part MultipartBody.Part picture, @Part("description") RequestBody description, @Header(PharmaConstants.AUTHORIZATION) String token);

    @GET
    Call<PrescriptionsItemsResponse> getAllPrescriptions(@Url String url);

    @GET
    Call<PrescriptionsCommentsResponse> getAllPrescriptionsComments(@Url String url);

    @POST
    Call<PrescriptionsCommentResponse> addPrescriptionsComments(@Header(PharmaConstants.AUTHORIZATION) String token, @Url String url, @Query("comment") String comment);

    @GET
    Call<PropertiesItemsResponse> getAllProperties(@Url String url);

    @POST(PharmaConstants.API + "properties")
    Call<PropertiesItemResponse> addProperties(@Header(PharmaConstants.AUTHORIZATION) String token, @Query("name") String name, @Query("city") String city,
                                               @Query("region") String region, @Query("address") String address, @Query("area") String area,
                                               @Query("listed_for") String listedFor, @Query("type") String type, @Query("price") int price,
                                               @Query("description") String description, @Query("average_daily_income") int averageDailyIncome,
                                               @Query("mobile_numbers[]") List<String> mobileNumber, @Query("status") String status);

    @GET()
    Call<PropertiesItemsResponse> getFilteredProperties(@Url String url, @QueryMap() Map<String, String> propertiesParam);

    @GET
    Call<PropertiesItemResponse> getPropertyById(@Url String url);

    @DELETE
    Call<PropertiesItemResponse> deletePropertyById(@Header(PharmaConstants.AUTHORIZATION) String token, @Url String url);

    @Multipart
    @POST()
    Call<PropertiesImageResponse> addPropertiesImages(@Part List<MultipartBody.Part> images, @Header(PharmaConstants.AUTHORIZATION) String token, @Url String url);

    @GET
    Call<JobsItemsResponse> getAllJobs(@Url String url);

    @POST(PharmaConstants.API + "job-ads")
    Call<JobsItemResponse> addJob(@Header(PharmaConstants.AUTHORIZATION) String token, @Query("name") String name, @Query("description") String description,
                                  @Query("min_salary") int from, @Query("max_salary") int to,
                                  @Query("workplace") String workPlace, @Query("position") String position,
                                  @Query("city") String city, @Query("region") String region, @Query("address") String address,
                                  @Query("mobile_numbers[]") List<String> mobileNumber, @Query("due_date") String dueDate, @Query("salary_negotiable") String negotiable);

    @GET()
    Call<JobsItemsResponse> getFilteredJobs(@Url String url, @QueryMap() Map<String, String> jobsParam);

    @GET
    Call<PromotionsItemsResponse> getAllPromotions(@Url String url);

    @GET("drug_index.json")
    Call<DrugEyeResponse> getDrugEye();

    @GET(PharmaConstants.API + "drug-index-version")
    Call<DrugEyeVersion> getDrugEyeVersion();
}
