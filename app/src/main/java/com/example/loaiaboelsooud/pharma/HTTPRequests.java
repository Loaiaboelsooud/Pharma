package com.example.loaiaboelsooud.pharma;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HTTPRequests extends AppCompatActivity {
    IResult result;
    Context context;
    private final String BEARER = "Bearer ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public HTTPRequests(Context context, IResult result) {
        this.context = context;
        this.result = result;
    }

    public void sendFBPostRequest(JSONObject jsonObject, final String fbToken, Activity activity) {
        try {
            User user = new User();
            user.setName(jsonObject.getString("name"));
            user.setFacebookID(jsonObject.getString("id"));
            user.setFacebookToken(fbToken);
            final PrefUtil prefUtil = new PrefUtil(activity);
            Call<UserResponse> userCall = RetrofitClient.getInstance().getApi().createUser(user);
            userCall.enqueue(new Callback<UserResponse>() {
                @Override
                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                    UserResponse userResponse = response.body();
                    String token = userResponse.getUser().getToken();
                    prefUtil.saveAccessToken(token);
                }

                @Override
                public void onFailure(Call<UserResponse> call, Throwable t) {
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void sendFBGetRequest(Activity activity) {
        final PrefUtil prefUtil = new PrefUtil(activity);
        Call<UserResponse> userCall = RetrofitClient.getInstance().getApi().getUser(BEARER + prefUtil.getToken());
        userCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.body() != null) {
                    User user = response.body().getUser();
                    String name = user.getName();
                    String email = user.getEmail();
                    String qualification = user.getQualification();
                    String avatar = user.getAvatar();
                    String job = user.getJob();
                    String city = user.getCity();
                    long expiresIn = user.getExpiresIn();
                    prefUtil.saveFacebookUserInfo(name, email, qualification, avatar, job, city, expiresIn);
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
            }
        });
    }

    public void sendFBPutRequest(Activity activity) {
        final PrefUtil prefUtil = new PrefUtil(activity);
        Call<UserResponse> userCall = RetrofitClient.getInstance().getApi().refreshToken(BEARER + prefUtil.getToken());
        userCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.body() != null) {
                    User user = response.body().getUser();
                    String name = user.getName();
                    String email = user.getEmail();
                    String qualification = user.getQualification();
                    String avatar = user.getAvatar();
                    String job = user.getJob();
                    String city = user.getCity();
                    long expiresIn = user.getExpiresIn();
                    String token = user.getToken();
                    prefUtil.saveAccessToken(token);
                    prefUtil.saveFacebookUserInfo(name, email, qualification, avatar, job, city, expiresIn);
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
            }
        });
    }

    public void sendFBDelRequest(final String fbToken) {
        Call<Void> userCall = RetrofitClient.getInstance().getApi().logoutUser(BEARER + fbToken);
        userCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
    }

    public void sendPrescriptionsPostRequest(MultipartBody.Part imagePart, RequestBody description,
                                             final GetPrescriptionPostResult getPrescriptionPostResult, String token) {
        Call<PrescriptionsItemResponse> userCall = RetrofitClient.getInstance().getApi().addPrescriptions(imagePart, description, BEARER + token);
        userCall.enqueue(new Callback<PrescriptionsItemResponse>() {
            @Override
            public void onResponse(Call<PrescriptionsItemResponse> call, Response<PrescriptionsItemResponse> response) {
                if (response.body() != null) {
                    getPrescriptionPostResult.success();
                } else {
                    getPrescriptionPostResult.failed();
                }
            }

            @Override
            public void onFailure(Call<PrescriptionsItemResponse> call, Throwable t) {
                getPrescriptionPostResult.failed();
            }
        });
    }

    public void sendPrescriptionsGetRequest(final GetPrescriptionsList getPrescriptionsList, int pageNumber) {
        Call<PrescriptionsItemsResponse> userCall = RetrofitClient.getInstance().getApi().getAllPrescriptions("prescriptions?page=" + pageNumber);
        userCall.enqueue(new Callback<PrescriptionsItemsResponse>() {
            @Override
            public void onResponse(Call<PrescriptionsItemsResponse> call, Response<PrescriptionsItemsResponse> response) {
                if (response.body() != null) {
                    getPrescriptionsList.notifyList(response.body().getPrescriptionsItems(), response.body().getMetaData());
                }
            }

            @Override
            public void onFailure(Call<PrescriptionsItemsResponse> call, Throwable t) {
                getPrescriptionsList.failed();
            }
        });
    }

    public void sendPrescriptionsCommentGetRequest(String token, final GetPrescriptionsCommentsList getPrescriptionsCommentsList, int prescriptionId, int pageNumber) {
        Call<PrescriptionsCommentsResponse> userCall = RetrofitClient.getInstance().getApi().getAllPrescriptionsComments(
                BEARER + token, "prescriptions/" + prescriptionId + "/comments?page=" + pageNumber);
        userCall.enqueue(new Callback<PrescriptionsCommentsResponse>() {
            @Override
            public void onResponse(Call<PrescriptionsCommentsResponse> call, Response<PrescriptionsCommentsResponse> response) {
                if (response.body() != null) {
                    getPrescriptionsCommentsList.notifyList(response.body().getPrescriptionsComments(),
                            response.body().getMetaData());
                }
            }

            @Override
            public void onFailure(Call<PrescriptionsCommentsResponse> call, Throwable t) {
                getPrescriptionsCommentsList.failed();
            }
        });
    }

    public void sendPrescriptionsCommentPostRequest(String token, final GetPrescriptionsComment getPrescriptionsComment, int prescriptionId, String comment) {
        Call<PrescriptionsCommentResponse> userCall = RetrofitClient.getInstance().getApi().addPrescriptionsComments(
                BEARER + token, "prescriptions/" + prescriptionId + "/comments", comment);
        userCall.enqueue(new Callback<PrescriptionsCommentResponse>() {
            @Override
            public void onResponse(Call<PrescriptionsCommentResponse> call, Response<PrescriptionsCommentResponse> response) {
                if (response.body() != null) {
                    getPrescriptionsComment.notifyList(response.body().getPrescriptionsComment());
                }
            }

            @Override
            public void onFailure(Call<PrescriptionsCommentResponse> call, Throwable t) {
                getPrescriptionsComment.failed();
            }
        });
    }

    public void sendPropertiesGetRequest(final GetPropertiesList getPropertiesList, int pageNumber) {
        Call<PropertiesItemsResponse> userCall = RetrofitClient.getInstance().getApi().getAllProperties("properties?page=" + pageNumber);
        userCall.enqueue(new Callback<PropertiesItemsResponse>() {
            @Override
            public void onResponse(Call<PropertiesItemsResponse> call, Response<PropertiesItemsResponse> response) {
                if (response.body() != null) {
                    getPropertiesList.notifyList(response.body().getPropertiesItems(), response.body().getMetaData());
                }
            }

            @Override
            public void onFailure(Call<PropertiesItemsResponse> call, Throwable t) {
                getPropertiesList.failed();
            }
        });
    }

    public void sendPropertiesPostRequest(String token, String name,
                                          String city, String region, String address,
                                          String area, String listedFor, String type,
                                          int price, String description, String notes,
                                          List<String> mobileNumbers, List<String> landLineNumbers,
                                          final GetPropertiesPostResult getPropertiesPostResult) {
        Call<PropertiesItemResponse> userCall = RetrofitClient.getInstance().getApi().addProperties
                (BEARER + token, name, city, region, address, area, listedFor,
                        type, price, description, notes, mobileNumbers, landLineNumbers);
        userCall.enqueue(new Callback<PropertiesItemResponse>() {
            @Override
            public void onResponse(Call<PropertiesItemResponse> call, Response<PropertiesItemResponse> response) {
                if (response.body() != null) {
                    getPropertiesPostResult.success();
                } else {
                    getPropertiesPostResult.failed();
                }
            }

            @Override
            public void onFailure(Call<PropertiesItemResponse> call, Throwable t) {
                getPropertiesPostResult.failed();
            }
        });
    }


    public void sendPropertiesFilterGetRequest(String token, String selling, String renting, String pharmacy,
                                               String wareHouse, String factory, String hospital,
                                               final GetPropertiesList getPropertiesList, int pageNumber) {
        Call<PropertiesItemsResponse> userCall = RetrofitClient.getInstance().getApi().getFilteredProperties
                ("properties/filter?page=" + pageNumber, BEARER + token, selling, renting,
                        pharmacy, wareHouse, factory, hospital);
        userCall.enqueue(new Callback<PropertiesItemsResponse>() {
            @Override
            public void onResponse(Call<PropertiesItemsResponse> call, Response<PropertiesItemsResponse> response) {
                if (response.body() != null) {
                    getPropertiesList.notifyList(response.body().getPropertiesItems(), response.body().getMetaData());
                } else {
                    getPropertiesList.failed();
                }
            }

            @Override
            public void onFailure(Call<PropertiesItemsResponse> call, Throwable t) {
                getPropertiesList.failed();
            }
        });
    }

    public void sendPropertyGetRequest(final GetProperty getProperty, String token, int id) {
        Call<PropertiesItemResponse> userCall = RetrofitClient.getInstance().getApi().getPropertyById(
                BEARER + token, "properties/" + id);
        userCall.enqueue(new Callback<PropertiesItemResponse>() {
            @Override
            public void onResponse(Call<PropertiesItemResponse> call, Response<PropertiesItemResponse> response) {
                if (response.body() != null) {
                    getProperty.notifyItem(response.body().getPropertiesItem());
                }
            }

            @Override
            public void onFailure(Call<PropertiesItemResponse> call, Throwable t) {
                getProperty.failed();
            }
        });
    }

    public interface GetPropertiesList {
        void notifyList(List<PropertiesItem> propertiesItems, MetaData metaData);

        void failed();

    }

    public interface GetProperty {
        void notifyItem(PropertiesItem propertiesItems);

        void failed();

    }

    public interface GetPrescriptionsCommentsList {

        void notifyList(List<PrescriptionsComments> prescriptionsComments, MetaData metaData);

        void failed();
    }

    public interface GetPrescriptionsComment {
        void notifyList(PrescriptionsComments prescriptionsComments);

        void failed();
    }

    public interface GetPrescriptionsList {
        void notifyList(List<PrescriptionsItem> prescriptionsItems, MetaData metaData);

        void failed();

    }

    public interface GetPrescriptionPostResult {
        void success();

        void failed();
    }

    public interface GetPropertiesPostResult {
        void success();

        void failed();
    }


    public interface IResult {

    }

}

