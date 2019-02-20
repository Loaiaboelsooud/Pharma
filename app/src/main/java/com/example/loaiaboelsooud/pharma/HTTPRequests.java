package com.example.loaiaboelsooud.pharma;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

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

public class HTTPRequests extends AppCompatActivity {
    private RequestQueue mRequestQueue;
    private StringRequest stringRequest;
    private String url = "http://www.mocky.io/v2/5c6b023d330000a5387f4e4b";
    private String FBURL = "http://drugdeal.test/api/auth/facebook";
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

    public void sendFBPostRequest(JSONObject jsonObject) {
        try {
            String URL = "http://drugdeal.test/api/auth/facebook";
            JSONObject jsonBody = new JSONObject();

            jsonBody.put("facebook_token", jsonObject.getString("email"));
            jsonBody.put("name", jsonObject.getString("first_name ") + jsonObject.getString("last_name "));
            jsonBody.put("email", jsonObject.getString("email"));

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    Toast.makeText(getApplicationContext(), "Response:  " + response.toString(), Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    onBackPressed();

                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    final Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "Basic " + "c2FnYXJAa2FydHBheS5jb206cnMwM2UxQUp5RnQzNkQ5NDBxbjNmUDgzNVE3STAyNzI=");//put your token here
                    return headers;
                }
            };
            mRequestQueue.add(jsonObjectRequest);
            //HTTPRequests.getInstance().addToRequestQueue(jsonOblect);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_LONG).show();

    }


    public void sendRequest() {

        stringRequest = new StringRequest(Request.Method.POST, FBURL, new Response.Listener<String>() {
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
