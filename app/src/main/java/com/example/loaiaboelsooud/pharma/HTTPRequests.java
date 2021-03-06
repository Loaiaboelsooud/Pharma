package com.example.loaiaboelsooud.pharma;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HTTPRequests extends AppCompatActivity {
    private IResult result;
    private Context context;
    //response.errorBody().string()

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
            user.setName(jsonObject.getString(PharmaConstants.NAME));
            user.setFacebookID(jsonObject.getString(PharmaConstants.ID));
            user.setFacebookToken(fbToken);
            final PrefUtil prefUtil = new PrefUtil(activity);
            Call<UserResponse> userCall = RetrofitClient.getInstance().getApi().createUser(user);
            userCall.enqueue(new Callback<UserResponse>() {
                @Override
                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                    if (response.body() != null) {
                        User user = response.body().getUser();
                        user.setExpireDate();
                        prefUtil.saveFacebookUserInfo(user.getName(), user.getEmail(), user.getQualification(), user.getAvatar(), user.getJob()
                                , user.getCity(), user.getExpiresIn());
                        prefUtil.saveAccessToken(user.getToken());
                        prefUtil.saveExpireDate(user.getExpireDate());
                    }
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
        Call<UserResponse> userCall = RetrofitClient.getInstance().getApi().getUser(PharmaConstants.BEARER + prefUtil.getToken());
        userCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.body() != null) {
                    User user = response.body().getUser();
                    prefUtil.saveFacebookUserInfo(user.getName(), user.getEmail(), user.getQualification(), user.getAvatar(), user.getJob()
                            , user.getCity(), user.getExpiresIn());
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
            }
        });
    }

    public void sendFBPutRequest(Activity activity) {
        final PrefUtil prefUtil = new PrefUtil(activity);
        Call<UserResponse> userCall = RetrofitClient.getInstance().getApi().refreshToken(PharmaConstants.BEARER + prefUtil.getToken());
        userCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.body() != null) {
                    User user = response.body().getUser();
                    user.setExpireDate();
                    prefUtil.saveExpireDate(user.getExpireDate());
                    prefUtil.saveAccessToken(user.getToken());
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
            }
        });
    }

    public void sendFBDelRequest(final String fbToken) {
        Call<Void> userCall = RetrofitClient.getInstance().getApi().logoutUser(PharmaConstants.BEARER + fbToken);
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
        Call<PrescriptionsItemResponse> userCall = RetrofitClient.getInstance().getApi().addPrescriptions(imagePart, description, PharmaConstants.BEARER + token);
        userCall.enqueue(new Callback<PrescriptionsItemResponse>() {
            @Override
            public void onResponse(Call<PrescriptionsItemResponse> call, Response<PrescriptionsItemResponse> response) {
                if (response.body() != null) {
                    getPrescriptionPostResult.success(response.body().getPrescriptionsItem());
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
        Call<PrescriptionsItemsResponse> userCall = RetrofitClient.getInstance().getApi().getAllPrescriptions(PharmaConstants.API + "prescriptions?page=" + pageNumber);
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

    public void sendPrescriptionsCommentGetRequest(final GetPrescriptionsCommentsList getPrescriptionsCommentsList, int prescriptionId, final int pageNumber) {
        Call<PrescriptionsCommentsResponse> userCall = RetrofitClient.getInstance().getApi().getAllPrescriptionsComments(
                PharmaConstants.API + PharmaConstants.PRESCRIPTIONS + prescriptionId + "/comments?page=" + pageNumber);
        userCall.enqueue(new Callback<PrescriptionsCommentsResponse>() {
            @Override
            public void onResponse(Call<PrescriptionsCommentsResponse> call, Response<PrescriptionsCommentsResponse> response) {
                if (response.body() != null) {
                    List<PrescriptionsComments> prescriptionsCommentsList = new ArrayList<PrescriptionsComments>();
                    //to handle the 1st element in the prescriptionsCommentsAdapter which is the image
                    if (pageNumber == 1) {
                        prescriptionsCommentsList.add(null);
                    }
                    prescriptionsCommentsList.addAll(response.body().getPrescriptionsComments());
                    getPrescriptionsCommentsList.notifyList(prescriptionsCommentsList,
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
                PharmaConstants.BEARER + token, PharmaConstants.API + PharmaConstants.PRESCRIPTIONS + prescriptionId + "/comments", comment);
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
        Call<PropertiesItemsResponse> userCall = RetrofitClient.getInstance().getApi().getAllProperties(PharmaConstants.API + "properties?page=" + pageNumber);
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

    public void sendPropertiesPostRequest(final String token, String name,
                                          String city, String region, String address,
                                          String area, String listedFor, String type,
                                          int price, String description, int averageDailyIncome,
                                          List<String> mobileNumbers, String status, final List<MultipartBody.Part> images,
                                          final GetPropertiesPostResult getPropertiesPostResult, final DeleteProperty deleteProperty) {
        Call<PropertiesItemResponse> userCall = RetrofitClient.getInstance().getApi().addProperties
                (PharmaConstants.BEARER + token, name, city, region, address, area, listedFor,
                        type, price, description, averageDailyIncome, mobileNumbers, status);
        userCall.enqueue(new Callback<PropertiesItemResponse>() {
            @Override
            public void onResponse(Call<PropertiesItemResponse> call, Response<PropertiesItemResponse> response) {
                if (response.body() != null) {
                    sendPropertiesImagePostRequest(images, token, response.body().getPropertiesItem().getId(), getPropertiesPostResult, deleteProperty);
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

    public void sendPropertyDeleteRequest(final DeleteProperty deleteProperty, final String token, final int id) {
        Call<PropertiesItemResponse> userCall = RetrofitClient.getInstance().getApi().deletePropertyById(PharmaConstants.BEARER + token,
                PharmaConstants.API + PharmaConstants.PROPERTIES + id);
        userCall.enqueue(new Callback<PropertiesItemResponse>() {
            @Override
            public void onResponse(Call<PropertiesItemResponse> call, Response<PropertiesItemResponse> response) {
                if (response.body() != null) {
                }
            }

            @Override
            public void onFailure(Call<PropertiesItemResponse> call, Throwable t) {
                sendPropertyDeleteRequest(deleteProperty, token, id);
                deleteProperty.failed();
            }
        });
    }

    public void sendPropertiesFilterGetRequest(Map propertiesParam,
                                               final GetPropertiesList getPropertiesList, int pageNumber) {
        Call<PropertiesItemsResponse> userCall = RetrofitClient.getInstance().getApi().getFilteredProperties
                (PharmaConstants.API + PharmaConstants.PROPERTIES + "filter?page=" + pageNumber, propertiesParam);
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

    public void sendPropertyGetRequest(final GetProperty getProperty, int id) {
        Call<PropertiesItemResponse> userCall = RetrofitClient.getInstance().getApi().getPropertyById(
                PharmaConstants.API + PharmaConstants.PROPERTIES + id);
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

    public void sendPropertiesImagePostRequest(List<MultipartBody.Part> images, final String token, final int id,
                                               final GetPropertiesPostResult getPropertiesPostResult, final DeleteProperty deleteProperty) {
        Call<PropertiesImageResponse> userCall = RetrofitClient.getInstance().getApi().addPropertiesImages
                (images, PharmaConstants.BEARER + token, PharmaConstants.API + PharmaConstants.PROPERTIES + id + PharmaConstants.IMAGES);
        userCall.enqueue(new Callback<PropertiesImageResponse>() {
            @Override
            public void onResponse(Call<PropertiesImageResponse> call, Response<PropertiesImageResponse> response) {
                if (response.body() != null) {
                    getPropertiesPostResult.success();
                } else {
                    sendPropertyDeleteRequest(deleteProperty, token, id);
                    getPropertiesPostResult.failed();
                }
            }

            @Override
            public void onFailure(Call<PropertiesImageResponse> call, Throwable t) {
                getPropertiesPostResult.failed();
                sendPropertyDeleteRequest(deleteProperty, token, id);
            }
        });
    }

    public void sendJobsGetRequest(final GetJobsList getJobsList, int pageNumber) {
        Call<JobsItemsResponse> userCall = RetrofitClient.getInstance().getApi().getAllJobs(PharmaConstants.API + "job-ads?page=" + pageNumber);
        userCall.enqueue(new Callback<JobsItemsResponse>() {
            @Override
            public void onResponse(Call<JobsItemsResponse> call, Response<JobsItemsResponse> response) {
                if (response.body() != null) {
                    getJobsList.notifyList(response.body().getJobsItems(), response.body().getMetaData());
                }
            }

            @Override
            public void onFailure(Call<JobsItemsResponse> call, Throwable t) {
                getJobsList.failed();
            }
        });
    }

    public void sendJobPostRequest(final String token, String name, String description, int from, int to,
                                   String workPlace, String position, String city, String region, String address,
                                   List<String> mobileNumbers, String dueDate, String negotiable, String email, final GetJobPostResult getJobPostResult) {
        Call<JobsItemResponse> userCall = RetrofitClient.getInstance().getApi().addJob
                (PharmaConstants.BEARER + token, name, description, from, to,
                        workPlace, position, city, region, address, mobileNumbers, dueDate, negotiable, email);
        userCall.enqueue(new Callback<JobsItemResponse>() {
            @Override
            public void onResponse(Call<JobsItemResponse> call, Response<JobsItemResponse> response) {
                if (response.body() != null) {
                    getJobPostResult.success();
                } else {
                    getJobPostResult.failed();
                }
            }

            @Override
            public void onFailure(Call<JobsItemResponse> call, Throwable t) {
                getJobPostResult.failed();
            }
        });
    }

    public void sendJobFilterGetRequest(Map jobsParam, final GetJobsList getJobsList, int pageNumber) {
        Call<JobsItemsResponse> userCall = RetrofitClient.getInstance().getApi().getFilteredJobs
                (PharmaConstants.API + PharmaConstants.JOBADS + "filter?page=" + pageNumber, jobsParam);
        userCall.enqueue(new Callback<JobsItemsResponse>() {
            @Override
            public void onResponse(Call<JobsItemsResponse> call, Response<JobsItemsResponse> response) {
                if (response.body() != null) {
                    getJobsList.notifyList(response.body().getJobsItems(), response.body().getMetaData());
                } else {
                    getJobsList.failed();
                }
            }

            @Override
            public void onFailure(Call<JobsItemsResponse> call, Throwable t) {
                getJobsList.failed();
            }
        });
    }

    public void sendPromotionsGetRequest(final GetPromotionsList getPromotionsList, int pageNumber) {
        Call<PromotionsItemsResponse> userCall = RetrofitClient.getInstance().getApi().getAllPromotions(PharmaConstants.API + "promotions?page=" + pageNumber);
        userCall.enqueue(new Callback<PromotionsItemsResponse>() {
            @Override
            public void onResponse(Call<PromotionsItemsResponse> call, Response<PromotionsItemsResponse> response) {
                if (response.body() != null) {
                    getPromotionsList.notifyList(response.body().getPromotionsItem(), response.body().getMetaData());
                }
            }

            @Override
            public void onFailure(Call<PromotionsItemsResponse> call, Throwable t) {
                getPromotionsList.failed();
            }
        });
    }

    public void sendDrugEyeVersionGetRequest(Activity activity, final GetDrugEyeVersion getDrugEyeVersion) {
        final PrefUtil prefUtil = new PrefUtil(activity);
        Call<DrugEyeVersion> userCall = RetrofitClient.getInstance().getApi().getDrugEyeVersion();
        userCall.enqueue(new Callback<DrugEyeVersion>() {
            @Override
            public void onResponse(Call<DrugEyeVersion> call, Response<DrugEyeVersion> response) {
                if (response.body() != null) {
                    Long drugEyeVersion = response.body().getDrugEyeVersion();
                    getDrugEyeVersion.success(drugEyeVersion);
                }
            }

            @Override
            public void onFailure(Call<DrugEyeVersion> call, Throwable t) {
                getDrugEyeVersion.failed();
            }
        });
    }

    public void sendDrugEyeGetRequest(final GetDrugEyeList getDrugEyeList) {
        Call<DrugEyeResponse> userCall = RetrofitClient.getInstance().getApi().getDrugEye();
        userCall.enqueue(new Callback<DrugEyeResponse>() {
            @Override
            public void onResponse(Call<DrugEyeResponse> call, Response<DrugEyeResponse> response) {
                if (response.body() != null) {
                    getDrugEyeList.insertAll(response.body().getDrugEyeItems());
                }
            }

            @Override
            public void onFailure(Call<DrugEyeResponse> call, Throwable t) {
                getDrugEyeList.failed();
            }
        });
    }

    public interface GetPropertiesList {
        void notifyList(List<PropertiesItem> propertiesItems, MetaData metaData);

        void failed();
    }

    public interface GetPromotionsList {
        void notifyList(List<PromotionsItem> promotionsItems, MetaData metaData);

        void failed();
    }

    public interface GetProperty {
        void notifyItem(PropertiesItem propertiesItems);

        void failed();
    }

    public interface DeleteProperty {
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
        void success(PrescriptionsItem prescriptionsItem);

        void failed();
    }

    public interface GetPropertiesPostResult {
        void success();

        void failed();
    }

    public interface GetJobsList {
        void notifyList(List<JobsItem> jobsItems, MetaData metaData);

        void failed();
    }

    public interface GetJobPostResult {
        void success();

        void failed();
    }

    public interface GetDrugEyeList {
        void insertAll(List<DrugEyeItem> drugEyeItems);

        void failed();
    }

    public interface GetDrugEyeVersion {
        void success(Long drugEyeVersion);

        void failed();
    }

    public interface IResult {
    }
}

