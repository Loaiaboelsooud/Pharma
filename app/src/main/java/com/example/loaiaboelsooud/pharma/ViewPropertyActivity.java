package com.example.loaiaboelsooud.pharma;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ViewPropertyActivity extends NavMenuInt implements HTTPRequests.GetProperty, PropertiesImagesAdapter.OnPropertiesClickListener {
    private HTTPRequests httpRequests;
    private ProgressBar progressBar;
    private String phoneNumber;
    private RecyclerView.Adapter adapter;
    private LinearLayoutManager manager;
    public RecyclerView propertiesImagesRecyclerView;
    private PropertiesImage propertiesImage;
    private LinearLayout contactLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new PrefUtil(this);
        httpRequests = new HTTPRequests(this, new HTTPRequests.IResult() {
        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_property);
        intNavToolBar();
        contactLayout = findViewById(R.id.property_contact_layout);
        progressBar = findViewById(R.id.property_progress);
        progressBar.setVisibility(View.VISIBLE);
        httpRequests.sendPropertyGetRequest(this, getIntent().getIntExtra("id", 0));

        contactLayout.setOnClickListener(new View.OnClickListener() {
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
        TextView name, uploaderName, description, address, area, price, listedFor, type, status, averageDailyIncome, city, region, mobile, updatedAt, priceUnit;
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
        updatedAt = findViewById(R.id.property_updated_at);
        priceUnit = findViewById(R.id.property_price_unit);
        updatedAt.setText(PrefUtil.splitDateTime(propertiesItem.getUpdatedAt()));
        description.setText(propertiesItem.getDescription().toUpperCase());
        address.setText(propertiesItem.getAddress().toUpperCase());
        city.setText(PharmaConstants.citiesMapView.get(propertiesItem.getCity()).toUpperCase());
        region.setText(PharmaConstants.regionsMapView.get(propertiesItem.getRegion()).toUpperCase());
        status.setText(PharmaConstants.statusMapView.get(propertiesItem.getStatus()).toUpperCase());
        type.setText(PharmaConstants.typeMapView.get(propertiesItem.getType()).toUpperCase());
        listedFor.setText(PharmaConstants.listedForMapView.get(propertiesItem.getListedFor()).toUpperCase());
        averageDailyIncome.setText(String.valueOf(propertiesItem.getAverageDailyIncome()));
        price.setText(String.valueOf(propertiesItem.getPrice()));
        area.setText(propertiesItem.getArea());
        if (PharmaConstants.statusMapView.get(propertiesItem.getStatus()) != null) {
            if (propertiesItem.getStatus().equals(PharmaConstants.CLOSED)) {
                status.setTextColor(Color.parseColor("#EF0F45"));
            } else if (propertiesItem.getStatus().equals(PharmaConstants.OPENED)) {
                status.setTextColor(Color.parseColor("#a7bd56"));
            }
            status.setText(PharmaConstants.statusMapView.get(propertiesItem.getStatus()).toUpperCase());
        }
        if (PharmaConstants.listedForMapView.get(propertiesItem.getListedFor()) != null) {
            if (PharmaConstants.listedForMapView.get(propertiesItem.getListedFor()).equals(PharmaConstants.listedForArray[0])) {
                listedFor.setTextColor(Color.parseColor("#EF0F45"));
                priceUnit.setText(getResources().getString(R.string.LE));
            } else if (PharmaConstants.listedForMapView.get(propertiesItem.getListedFor()).equals(PharmaConstants.listedForArray[1])) {
                listedFor.setTextColor(Color.parseColor("#ffb300"));
                priceUnit.setText(getResources().getString(R.string.LE_Month));
            }
        }
        if (propertiesItem.getMobileNumber().size() > 0) {
            mobile.setText(String.valueOf(propertiesItem.getMobileNumber().get(0)));
        }
        phoneNumber = propertiesItem.getMobileNumber().get(0);
        name.setText(propertiesItem.getName().toUpperCase());
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
