package com.example.loaiaboelsooud.pharma;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

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

    public void sendPrescriptionsPostRequest(MultipartBody.Part imagePart, RequestBody description, String token) {
        Call<PrescriptionsResponse> userCall = RetrofitClient.getInstance().getApi().addPrescriptions(imagePart, description, "Bearer " + token);
        userCall.enqueue(new Callback<PrescriptionsResponse>() {
            @Override
            public void onResponse(Call<PrescriptionsResponse> call, Response<PrescriptionsResponse> response) {
                if (response.body() != null) {

                }
            }

            @Override
            public void onFailure(Call<PrescriptionsResponse> call, Throwable t) {
            }
        });
    }

    public void sendPrescriptionsGetRequest() {
        Call<PrescriptionsResponse> userCall = RetrofitClient.getInstance().getApi().getAllPrescriptions();
        userCall.enqueue(new Callback<PrescriptionsResponse>() {
            @Override
            public void onResponse(Call<PrescriptionsResponse> call, Response<PrescriptionsResponse> response) {
                if (response.body() != null) {

                }
            }

            @Override
            public void onFailure(Call<PrescriptionsResponse> call, Throwable t) {
            }
        });
    }

    public interface IResult {
        public void notifySuccess(String response);

        public void notifyError(Exception error);
    }

}
