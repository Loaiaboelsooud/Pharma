package com.example.loaiaboelsooud.pharma;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;

public class EditAccActivity extends NavMenuInt {
    private AccessTokenTracker tokenTracker;
    private TextView txtStat;
    private LoginButton logout_button;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_acc);
        intNavToolBar();
        tokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (currentAccessToken == null) {

                    sendFBDelRequest(oldAccessToken.getToken());
                    tokenTracker.stopTracking();
                }
            }
        };
        tokenTracker.startTracking();
    }

    private void sendFBDelRequest(String fbAccess) {
        final HTTPRequests httpRequests = new HTTPRequests(this, new HTTPRequests.IResult() {
            @Override
            public void notifySuccess(String response) {
                Log.e("responce", response);
                onBackPressed();
            }

            @Override
            public void notifyError(VolleyError error) {
                Log.e("responce", error.toString());
            }
        });
        httpRequests.sendFBDelRequest(fbAccess);

    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(EditAccActivity.this, MainActivity.class);
        startActivity(intent);
    }
}