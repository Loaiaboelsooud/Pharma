package com.example.loaiaboelsooud.pharma;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HTTPRequests extends AppCompatActivity {
    private RequestQueue mRequestQueue;
    private StringRequest stringRequest;
    private String url = "http://www.mocky.io/v2/5c6b023d330000a5387f4e4b";
    private String FBURL = "http://drugdeal.com/api/auth/facebook";
    private final String TAG = HTTPRequests.class.getName();
    IResult result;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public HTTPRequests(Context context, IResult result) {

        this.context = context;
        mRequestQueue = Volley.newRequestQueue(context);
        this.result = result;
    }

    public void sendPostRequest(JSONObject jsonObject, final String fbToken) {
        String URL = "http://drugdealapp.com/api/auth/";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(URL).
                addConverterFactory(GsonConverterFactory.create()).build();
        JsonPostPlaceHolderApi jsonPostPlaceHolderApi = retrofit.create(JsonPostPlaceHolderApi.class);

        try {
            User user = new User();
            user.setName(jsonObject.getString("name"));
            user.setFacebook_id(jsonObject.getInt("id"));
            user.setFacebook_token(fbToken);
            //user.setEmail("looy__24@hotmail.com");

            Call<User> userCall = jsonPostPlaceHolderApi.createUser(user);
            userCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, retrofit2.Response<User> response) {
                    response.raw().body();
                    response.body();


                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void sendFBPostRequest(JSONObject jsonObject, final String fbToken) {
        try {
            String URL = "http://drugdealapp.com/api/auth/facebook";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("facebook_id", jsonObject.getString("id"));
            jsonBody.put("facebook_token", fbToken);
            jsonBody.put("name", jsonObject.getString("name"));

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error.networkResponse != null && error.networkResponse.data != null) {
                        new VolleyError(new String(error.networkResponse.data));
                    }
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    final Map<String, String> headers = new HashMap<>();
                    return headers;
                }
            };
            mRequestQueue.add(jsonObjectRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void sendFBGetRequest(final String fbToken) {
        try {
            String URL = "http://drugdealapp.com/api/auth/user";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("facebook_token", fbToken);


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error.networkResponse != null && error.networkResponse.data != null) {
                        new VolleyError(new String(error.networkResponse.data));
                    }
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    final Map<String, String> headers = new HashMap<>();
                    return headers;
                }
            };
            mRequestQueue.add(jsonObjectRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void sendFBRefreshRequest(final String fbToken) {
        try {
            String URL = "http://drugdealapp.com/api/auth/refresh";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("facebook_token", fbToken);


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error.networkResponse != null && error.networkResponse.data != null) {
                        new VolleyError(new String(error.networkResponse.data));
                    }
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    final Map<String, String> headers = new HashMap<>();
                    return headers;
                }
            };
            mRequestQueue.add(jsonObjectRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void sendFBDelRequest(final String fbToken) {
        try {
            String URL = "http://drugdealapp.com/api/auth/logout";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("facebook_token", fbToken);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    finish();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error.networkResponse != null && error.networkResponse.data != null) {
                        new VolleyError(new String(error.networkResponse.data));
                    }
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    final Map<String, String> headers = new HashMap<>();
                    return headers;
                }
            };
            mRequestQueue.add(jsonObjectRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void sendRequest() {

        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG, "suc:  " + response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "error:  " + error.toString());
            }
        });
        mRequestQueue.add(stringRequest);
    }


    public interface IResult {
        public void notifySuccess(String response);

        public void notifyError(VolleyError error);
    }

}
