package com.example.loaiaboelsooud.pharma;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ViewPrescriptionsActivity extends NavMenuInt {
    RecyclerView prescriptionsRecyclerView;
    String[] Items = {"item0", "item1", "item2", "item3", "item4", "item5", "item6", "item7"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_view_prescriptions);
        intNavToolBar();
        prescriptionsRecyclerView = findViewById(R.id.presecription_recycler);
        prescriptionsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        prescriptionsRecyclerView.setAdapter(new prescriptionsAdapter(this, Items));
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewPrescriptionsActivity.this, AddPrescriptionsActivity.class);
                startActivity(intent);
            }
        });

    }
}
