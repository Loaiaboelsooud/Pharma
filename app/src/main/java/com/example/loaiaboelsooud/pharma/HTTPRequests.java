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
        Call<UserResponse> userCall = RetrofitClient.getInstance().getApi().getUser("Bearer " + prefUtil.getToken());
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
        Call<UserResponse> userCall = RetrofitClient.getInstance().getApi().refreshToken("Bearer " + prefUtil.getToken());
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
        Call<Void> userCall = RetrofitClient.getInstance().getApi().logoutUser("Bearer " + fbToken);
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
        Call<PrescriptionsItemResponse> userCall = RetrofitClient.getInstance().getApi().addPrescriptions(imagePart, description, "Bearer " + token);
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
                "Bearer " + token, "prescriptions/" + prescriptionId + "/comments?page=" + pageNumber);
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
                "Bearer " + token, "prescriptions/" + prescriptionId + "/comments", comment);
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

    public interface IResult {

    }

}

