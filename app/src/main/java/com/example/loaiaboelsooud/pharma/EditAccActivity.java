package com.example.loaiaboelsooud.pharma;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.squareup.picasso.Picasso;

public class EditAccActivity extends NavMenuInt {
    private AccessTokenTracker tokenTracker;
    private HTTPRequests httpRequests;
    private ImageView avatar;


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
        avatar = (ImageView) findViewById(R.id.profile_picture);
        Picasso.with(this).load(user.getAvatar() + "picture?width=250&height=250").into(avatar);
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
        //getHTTPRequestsInstance().sendFBPutRequest(EditAccActivity.this);
        getHTTPRequestsInstance().sendFBGetRequest(EditAccActivity.this);
    }

    private void logoutUser(String fbAccess) {
        getHTTPRequestsInstance().sendFBDelRequest(fbAccess);
    }

    private HTTPRequests getHTTPRequestsInstance() {
        if (httpRequests == null) {
            HTTPRequests httpRequests = new HTTPRequests(this, new HTTPRequests.IResult() {
                @Override
                public void notifySuccess(String response) {
                }

                @Override
                public void notifyError(Exception error) {

                }

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