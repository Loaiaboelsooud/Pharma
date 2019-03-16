package com.example.loaiaboelsooud.pharma;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static RetrofitClient mInstance;
    private Retrofit retrofit;
    private String FBURL = "http://drugdealapp.com/api/auth/";

    private RetrofitClient() {
        retrofit = new Retrofit.Builder().baseUrl(FBURL).
                addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static synchronized RetrofitClient getInstance() {

        if (mInstance == null) {
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }

    public JsonPostPlaceHolderApi getApi() {
        return retrofit.create(JsonPostPlaceHolderApi.class);

    }
}
