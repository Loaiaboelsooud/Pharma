package com.example.loaiaboelsooud.pharma;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
        TextView name, uploaderName, description, address, area, price, listedFor, type, status, averageDailyIncome, city, region, mobile;
        ImageView uploaderAvatar;
        this.propertiesImage = propertiesItem.getImages();
        adapter = new PropertiesImagesAdapter(this, this.propertiesImage, this);
        propertiesImagesRecyclerView.setAdapter(adapter);
        name = findViewById(R.id.property_name);
        description = findViewById(R.id.property_description);
        address = findViewById(R.id.property_address);
        city = findViewById(R.id.property_city);
        region = findViewById(R.id.property_region);
        area = findViewById(R.id.property_area);
        price = findViewById(R.id.property_price);
        listedFor = findViewById(R.id.property_listed_for);
        type = findViewById(R.id.property_type);
        status = findViewById(R.id.property_status);
        averageDailyIncome = findViewById(R.id.property_average_daily_income);
        uploaderAvatar = findViewById(R.id.property_user_profile_picture);
        uploaderName = findViewById(R.id.property_user_name);
        mobile = findViewById(R.id.property_mobile);
        description.setText(propertiesItem.getDescription());
        address.setText(propertiesItem.getAddress());
        city.setText(PharmaConstants.citiesMapView.get(propertiesItem.getCity()));
        region.setText(PharmaConstants.regionsMapView.get(propertiesItem.getRegion()));
        status.setText(PharmaConstants.statusMapView.get(propertiesItem.getStatus()));
        type.setText(PharmaConstants.typeMapView.get(propertiesItem.getType()));
        listedFor.setText(PharmaConstants.listedForMapView.get(propertiesItem.getListedFor()));
        averageDailyIncome.setText(String.valueOf(propertiesItem.getAverageDailyIncome()));
        price.setText(String.valueOf(propertiesItem.getPrice()));
        area.setText(propertiesItem.getArea());
        if (propertiesItem.getMobileNumber().size() > 0) {
            mobile.setText(String.valueOf(propertiesItem.getMobileNumber().get(0)));
        }
        phoneNumber = propertiesItem.getMobileNumber().get(0);
        name.setText(propertiesItem.getName());
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
