package com.example.loaiaboelsooud.pharma;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ViewPropertyActivity extends NavMenuInt implements HTTPRequests.GetProperty, PropertiesImagesAdapter.OnPropertiesClickListener {
    private HTTPRequests httpRequests;
    private ProgressBar progressBar;
    private ImageButton callPropertiesButton;
    private String phoneNumber;
    private RecyclerView.Adapter adapter;
    private LinearLayoutManager manager;
    public RecyclerView propertiesImagesRecyclerView;
    private PropertiesImage propertiesImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new PrefUtil(this);
        httpRequests = new HTTPRequests(this, new HTTPRequests.IResult() {
        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_property);
        intNavToolBar();
        callPropertiesButton = findViewById(R.id.call_properties_button);
        progressBar = findViewById(R.id.property_progress);
        progressBar.setVisibility(View.VISIBLE);
        httpRequests.sendPropertyGetRequest(this, getIntent().getIntExtra("id", 0));
        callPropertiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(callIntent);
            }
        });
        manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        propertiesImagesRecyclerView = findViewById(R.id.properties_image_recycler);
        propertiesImagesRecyclerView.setLayoutManager(manager);

    }

    @Override
    public void notifyItem(PropertiesItem propertiesItem) {
        TextView propertyName, uploaderName, propertyDescription, propertyAddress, propertyArea, propertyNotes, propertyCreatedAt;
        ImageView uploaderAvatar;
        this.propertiesImage = propertiesItem.getImages();
        adapter = new PropertiesImagesAdapter(this, this.propertiesImage, this);
        propertiesImagesRecyclerView.setAdapter(adapter);
        propertyName = findViewById(R.id.property_name);
        propertyDescription = findViewById(R.id.property_description);
        propertyAddress = findViewById(R.id.property_address);
        propertyArea = findViewById(R.id.property_area);
        propertyNotes = findViewById(R.id.property_notes);
        propertyCreatedAt = findViewById(R.id.property_created_at);
        uploaderAvatar = findViewById(R.id.property_user_profile_picture);
        uploaderName = findViewById(R.id.property_user_name);
        propertyDescription.setText(propertiesItem.getDescription());
        propertyAddress.setText(propertiesItem.getAddress());
        propertyArea.setText(propertiesItem.getArea());
        propertyNotes.setText(propertiesItem.getNotes());
        propertyCreatedAt.setText(PrefUtil.splitDateTime(propertiesItem.getCreatedAt()));
        phoneNumber = propertiesItem.getMobileNumber().get(0);
        propertyName.setText(propertiesItem.getName());
        uploaderName.setText(propertiesItem.getUserResponse().getUser().getName());
        Glide.with(this).load((propertiesItem.getUserResponse().getUser().getAvatar() + "picture?width=250&height=250")).
                placeholder(R.drawable.ic_loading).dontAnimate().
                into(uploaderAvatar);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void failed() {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPropertiesClick(int propertiesPosition) {
        Intent intent = new Intent(this, FullScreenImageActivity.class);
        ArrayList<String> imageURIS = new ArrayList<String>();
        for (int i = 0; i < this.propertiesImage.getData().size(); i++) {
            imageURIS.add(this.propertiesImage.getData().get(i).getUrl());
        }
        intent.putStringArrayListExtra("imageURIS", imageURIS);
        startActivity(intent);
    }
}
