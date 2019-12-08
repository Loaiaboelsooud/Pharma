package com.example.loaiaboelsooud.pharma;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainMenuInt extends AppCompatActivity {
    private Activity activity;
    private CircleImageView profilePicture;

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
        if (isLoggedIn()) {
            PrefUtil prefUtil = new PrefUtil(activity);
            User user = prefUtil.getFacebookUserInfo();
            profilePicture = findViewById(R.id.toolbar_profile_picture);
            Glide.with(activity).load(user.getAvatar() + "picture?width=250&height=250").into(profilePicture);
        }
        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLoggedIn()) {
                    finish();
                    Intent intent = new Intent(MainMenuInt.this, EditAccActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    finish();
                    Intent intent = new Intent(MainMenuInt.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}