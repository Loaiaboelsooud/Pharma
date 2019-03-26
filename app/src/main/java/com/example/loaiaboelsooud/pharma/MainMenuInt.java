package com.example.loaiaboelsooud.pharma;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;

public class MainMenuInt extends AppCompatActivity {
    ImageView avatar;
    Activity activity;

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
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);

        final MenuItem profileItem = menu.findItem(R.id.profile);

        if (isLoggedIn()) {
            PrefUtil prefUtil = new PrefUtil(activity);
            User user = prefUtil.getFacebookUserInfo();

            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.toolbar_profile_image, null);
            //FrameLayout item = (FrameLayout) view.findViewById(R.id.layout_profile_picture);

            avatar = view.findViewById(R.id.toolbar_profile_picture);
            //  Picasso.with(activity).setLoggingEnabled(true);
            // Picasso.with(activity).load(user.getAvatar() + "picture?width=250&height=250").into(avatar);
            Glide.with(activity)
                    .load(user.getAvatar() + "picture?width=250&height=250")
                    .into(avatar);
            //profileItem.setActionView(R.layout.toolbar_profile_image);
        }

        profileItem.getActionView().setOnClickListener(new View.OnClickListener() {
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

  /*  @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.profile:
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
           case R.id.settings_icon:
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
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
*/
}
