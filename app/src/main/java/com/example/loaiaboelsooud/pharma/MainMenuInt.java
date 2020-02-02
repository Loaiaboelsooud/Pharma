package com.example.loaiaboelsooud.pharma;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainMenuInt extends AppCompatActivity {
    private Activity activity;
    private CircleImageView profilePicture;
    private TextView profileName;
    private FrameLayout profileFrame, facebookFrame;
    private ImageView settingsIcon;
    private CallbackManager callbackManager;
    private LoginButton login_button;

    public void intMainToolBar(Activity activity) {
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.activity = activity;
    }

    private boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null && !accessToken.isExpired()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        facebookFrame = findViewById(R.id.toolbar_profile_facebook_frame);
        profilePicture = findViewById(R.id.toolbar_profile_picture);
        profileName = findViewById(R.id.toolbar_profile_name);
        profileFrame = findViewById(R.id.toolbar_profile_frame);
        settingsIcon = findViewById(R.id.toolbar_settings_icon);
        if (isLoggedIn()) {
            PrefUtil prefUtil = new PrefUtil(activity);
            User user = prefUtil.getFacebookUserInfo();
            profileFrame.setVisibility(View.VISIBLE);
            profileName.setText(user.getName());
            Glide.with(activity).load(user.getAvatar() + "picture?width=100&height=100").into(profilePicture);
            profileFrame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    Intent intent = new Intent(MainMenuInt.this, EditAccActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        } else {
            facebookFrame.setVisibility(View.VISIBLE);
            intFacebook();
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void intFacebook() {
        callbackManager = CallbackManager.Factory.create();
        login_button = findViewById(R.id.facebook_button);
        final HTTPRequests httpRequests = new HTTPRequests(this, new HTTPRequests.IResult() {
        });
        login_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                final String fbToken = loginResult.getAccessToken().getToken();
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject jsonObject,
                                                    GraphResponse response) {
                                httpRequests.sendFBPostRequest(jsonObject, fbToken, MainMenuInt.this);
                                Intent intent = new Intent(MainMenuInt.this, EditAccActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                request.executeAsync();
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException exception) {
            }
        });
    }
}