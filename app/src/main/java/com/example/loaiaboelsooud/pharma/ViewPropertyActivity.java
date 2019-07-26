package com.example.loaiaboelsooud.pharma;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class ViewPropertyActivity extends NavMenuInt implements HTTPRequests.GetProperty {
    private HTTPRequests httpRequests;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        PrefUtil prefUtil = new PrefUtil(this);
        httpRequests = new HTTPRequests(this, new HTTPRequests.IResult() {
        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_property);
        intNavToolBar();
        progressBar = findViewById(R.id.property_progress);
        progressBar.setVisibility(View.VISIBLE);
        httpRequests.sendPropertyGetRequest(this, prefUtil.getToken(), getIntent().getIntExtra("id", 0));


    }

    @Override
    public void notifyItem(PropertiesItem propertiesItem) {
        TextView uploaderName, propertiesName, listedFor, type;
        ImageView uploaderAvatar, picture;
        uploaderAvatar = findViewById(R.id.property_user_profile_picture);
        uploaderName = findViewById(R.id.property_user_name);
        Glide.with(this).load((propertiesItem.getUserResponse().getUser().getAvatar())).
                placeholder(R.drawable.ic_loading).dontAnimate().
                into(uploaderAvatar);
        uploaderName.setText(propertiesItem.getUserResponse().getUser().getName());
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void failed() {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_LONG).show();
    }
}
