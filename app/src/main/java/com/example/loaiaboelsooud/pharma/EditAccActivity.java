package com.example.loaiaboelsooud.pharma;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;

public class EditAccActivity extends NavMenuInt {
    private AccessTokenTracker tokenTracker;
    private HTTPRequests httpRequests;
    private ImageView avatar;
    private TextView userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_acc);
        intNavToolBar();
        final PrefUtil prefUtil = new PrefUtil(EditAccActivity.this);
        loadUserData();
        User user = prefUtil.getFacebookUserInfo();
        viewUserData(user);
        tokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (currentAccessToken == null) {
                    logoutUser(prefUtil.getToken());
                    tokenTracker.stopTracking();
                    onBackPressed();
                }
            }
        };
        tokenTracker.startTracking();
    }

    private void viewUserData(User user) {
        avatar = findViewById(R.id.profile_picture);
        userName = findViewById(R.id.user_name);
        String imageUrl = user.getAvatar() + "picture?width=250&height=250";
        Glide.with(this).load(imageUrl).placeholder(R.drawable.ic_loading).dontAnimate().into(avatar);
        userName.setText(user.getName());
        /*Picasso.with(this)
                .load(user.getAvatar()).resize(250, 250)
                .into(profileAvatar, new Callback() {
                    @Override
                    public void onSuccess() {
                        Bitmap imageBitmap = ((BitmapDrawable) profileAvatar.getDrawable()).getBitmap();
                        RoundedBitmapDrawable imageDrawable = RoundedBitmapDrawableFactory.create(getResources(), imageBitmap);
                        imageDrawable.setCircular(true);
                        imageDrawable.setCornerRadius(Math.max(imageBitmap.getWidth(), imageBitmap.getHeight()) / 2.0f);
                        profileAvatar.setImageDrawable(imageDrawable);
                    }

                    @Override
                    public void onError() {

                    }
                });*/

    }

    private void loadUserData() {
        getHTTPRequestsInstance().sendFBGetRequest(EditAccActivity.this);
    }

    private void logoutUser(String fbAccess) {
        getHTTPRequestsInstance().sendFBDelRequest(fbAccess);
    }


    private HTTPRequests getHTTPRequestsInstance() {
        if (httpRequests == null) {
            HTTPRequests httpRequests = new HTTPRequests(this, new HTTPRequests.IResult() {
            });
            return httpRequests;
        }
        return httpRequests;
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(EditAccActivity.this, MainActivity.class);
        startActivity(intent);
    }
}