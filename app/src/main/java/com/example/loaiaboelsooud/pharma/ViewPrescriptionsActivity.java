package com.example.loaiaboelsooud.pharma;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;

import java.util.List;

public class ViewPrescriptionsActivity extends NavMenuInt implements HTTPRequests.getPrescriptionsList {
    public RecyclerView prescriptionsRecyclerView;
    public List<PrescriptionsItem> prescriptionsItems;
    private Boolean isScrolling = false;
    private int currentItems, totalItems, scrollOutItems;
    private LinearLayoutManager manager;
    private HTTPRequests httpRequests;
    private PrefUtil prefUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        prefUtil = new PrefUtil(this);
        httpRequests = new HTTPRequests(this, new HTTPRequests.IResult() {
        });

        httpRequests.sendPrescriptionsGetRequest(prefUtil.getToken(), this, 1);
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_view_prescriptions);
        intNavToolBar();
        manager = new LinearLayoutManager(this);
        prescriptionsRecyclerView = findViewById(R.id.presecription_recycler);
        prescriptionsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewPrescriptionsActivity.this, AddPrescriptionsActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    @Override
    public void notifyList(final List<PrescriptionsItem> prescriptionsItems, final MetaData metaData) {
        prescriptionsRecyclerView.setAdapter(new prescriptionsAdapter(this, prescriptionsItems));
        prescriptionsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = manager.getChildCount();
                totalItems = manager.getItemCount();
                scrollOutItems = manager.findFirstVisibleItemPosition();

                if (isScrolling) {
                    loadMore(metaData);
                    isScrolling = false;
                }
            }
        });
    }

    private void loadMore(MetaData metaData) {
        if (metaData.getPagination().getTotalPages() > metaData.getPagination().getCurrentPage()) {
            httpRequests.sendPrescriptionsGetRequest(prefUtil.getToken(), this, metaData.getPagination().getCurrentPage() + 1);
        }
    }

    @Override
    public void onBackPressed() {
        finish();

    }
}
