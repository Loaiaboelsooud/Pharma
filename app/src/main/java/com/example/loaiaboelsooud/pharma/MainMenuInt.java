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

import de.hdodenhof.circleimageview.CircleImageView;

public class MainMenuInt extends AppCompatActivity {
    private Activity activity;
    private CircleImageView profilePicture;
    private TextView profileName;
    private FrameLayout profileFrame;
    private ImageView settingsIcon;

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
        profilePicture = findViewById(R.id.toolbar_profile_picture);
        profileName = findViewById(R.id.toolbar_profile_name);
        profileFrame=findViewById(R.id.toolbar_profile_frame);
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
            settingsIcon.setVisibility(View.VISIBLE);
            settingsIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    Intent intent = new Intent(MainMenuInt.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
        return super.onCreateOptionsMenu(menu);
    }
}