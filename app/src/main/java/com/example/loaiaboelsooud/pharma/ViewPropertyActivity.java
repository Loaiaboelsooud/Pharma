package com.example.loaiaboelsooud.pharma;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class ViewPropertyActivity extends NavMenuInt implements HTTPRequests.GetProperty, PropertiesImagesAdapter.OnPropertiesClickListener {
    private HTTPRequests httpRequests;
    private ProgressBar progressBar;
    private FloatingActionButton callPropertiesButton;
    private String phoneNumber;
    private RecyclerView.Adapter adapter;
    private LinearLayoutManager manager;
    public RecyclerView propertiesImagesRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        PrefUtil prefUtil = new PrefUtil(this);
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
        TextView uploaderName, propertyDescription, propertyAddress, propertyArea, propertyNotes;
        ImageView uploaderAvatar, picture;
        PropertiesImage propertiesImage = propertiesItem.getImages();
        adapter = new PropertiesImagesAdapter(this, propertiesImage, this);
        propertiesImagesRecyclerView.setAdapter(adapter);
        propertyDescription = findViewById(R.id.property_description);
        propertyAddress = findViewById(R.id.property_address);
        propertyArea = findViewById(R.id.property_area);
        propertyNotes = findViewById(R.id.property_notes);
        propertyDescription.setText(propertiesItem.getDescription());
        propertyAddress.setText(propertiesItem.getAddress());
        propertyArea.setText(propertiesItem.getArea());
        propertyNotes.setText(propertiesItem.getNotes());
        uploaderAvatar = findViewById(R.id.property_user_profile_picture);
        uploaderName = findViewById(R.id.property_user_name);
        phoneNumber = propertiesItem.getMobileNumber().get(0);
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

    @Override
    public void onPropertiesClick(int propertiesPosition) {

    }
}
