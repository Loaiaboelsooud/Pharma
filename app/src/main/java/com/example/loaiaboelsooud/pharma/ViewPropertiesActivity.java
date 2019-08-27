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
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewPropertiesActivity extends NavMenuInt implements HTTPRequests.GetPropertiesList, PropertiesAdapter.OnPropertiesClickListener {
    public RecyclerView propertiesRecyclerView;
    public List<PropertiesItem> propertiesItems;
    private Boolean isScrolling = false, testbol = false, isFiltered;
    private int currentItems, totalItems, scrollOutItems, actualPage;
    private LinearLayoutManager manager;
    private HTTPRequests httpRequests;
    private PrefUtil prefUtil;
    private RecyclerView.Adapter adapter;
    private FloatingActionButton addPropertiesButton, filterPropertiesButton;
    private ProgressBar progressBar;
    private ArrayList<String> listedForList, typeList;
    private HashMap<String, Boolean> propertiesParam;
    private String SELLING = "selling", RENTING = "renting", PHARMACY = "pharmacy", WAREHOUSE = "warehouse", FACTORY = "factory", HOSPITAL = "hospital";

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        prefUtil = new PrefUtil(this);
        httpRequests = new HTTPRequests(this, new HTTPRequests.IResult() {
        });
        isFiltered = getIntent().getExtras().getBoolean("isFiltered");
        if (isFiltered) {
            listedForList = new ArrayList<>();
            typeList = new ArrayList<>();
            propertiesParam = new HashMap<>();
            propertiesParam = (HashMap<String, Boolean>) getIntent().getSerializableExtra("propertiesParam");
            if (!propertiesParam.get(SELLING)) {
                SELLING = null;
            }
            if (!propertiesParam.get(RENTING)) {
                RENTING = null;
            }
            if (!propertiesParam.get(PHARMACY)) {
                PHARMACY = null;
            }
            if (!propertiesParam.get(WAREHOUSE)) {
                WAREHOUSE = null;
            }
            if (!propertiesParam.get(HOSPITAL)) {
                HOSPITAL = null;
            }
            if (!propertiesParam.get(FACTORY)) {
                FACTORY = null;
            }
            httpRequests.sendPropertiesFilterGetRequest(SELLING, RENTING, PHARMACY, WAREHOUSE, HOSPITAL, FACTORY, this, 1);
        } else {
            httpRequests.sendPropertiesGetRequest(this, 1);
        }
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_view_properties);
        intNavToolBar();
        adapter = new PropertiesAdapter(this, propertiesItems, this);
        manager = new LinearLayoutManager(this);
        propertiesRecyclerView = findViewById(R.id.properties_recycler);
        propertiesRecyclerView.setLayoutManager(manager);
        addPropertiesButton = findViewById(R.id.add_properties_button);
        progressBar = findViewById(R.id.properties_get_progress);
        progressBar.setVisibility(View.VISIBLE);
        filterPropertiesButton = findViewById(R.id.filter_properties_button);
        if (!prefUtil.isLoggedIn()) {
            addPropertiesButton.hide();
            //change to properties
            Toast.makeText(this, getString(R.string.Signin_prescriptions_toast), Toast.LENGTH_SHORT).show();
        } else {
            addPropertiesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                    Intent intent = new Intent(ViewPropertiesActivity.this, AddPropertiesActivity.class);
                    startActivity(intent);
                }
            });
        }
        filterPropertiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(ViewPropertiesActivity.this, FilterPropertiesActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void notifyList(final List<PropertiesItem> propertiesItems, final MetaData metaData) {
        if (!testbol) {
            testbol = true;
            actualPage = metaData.getPagination().getCurrentPage();
            this.propertiesItems = propertiesItems;
            adapter = new PropertiesAdapter(this, this.propertiesItems, this);
            propertiesRecyclerView.setAdapter(adapter);
        }
        if (actualPage != 1) {
            this.propertiesItems.addAll(propertiesItems);
        }
        actualPage++;
        adapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);
        propertiesRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

                if (isScrolling && (currentItems + scrollOutItems == totalItems)
                        && (actualPage <= metaData.getPagination().getTotalPages())) {
                    loadMore(metaData);
                    isScrolling = false;
                }
            }
        });
    }

    @Override
    public void failed() {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_LONG).show();
    }

    private void loadMore(MetaData metaData) {
        if (metaData.getPagination().getTotalPages() > metaData.getPagination().getCurrentPage()) {
            progressBar.setVisibility(View.VISIBLE);
            if (isFiltered) {
                httpRequests.sendPropertiesFilterGetRequest(SELLING, RENTING, PHARMACY, WAREHOUSE, HOSPITAL, FACTORY,
                        this, metaData.getPagination().getCurrentPage() + 1);
            } else {
                httpRequests.sendPropertiesGetRequest(this, metaData.getPagination().getCurrentPage() + 1);
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onPropertiesClick(int propertiesPosition) {
        Intent intent = new Intent(ViewPropertiesActivity.this, ViewPropertyActivity.class);
        intent.putExtra("id", propertiesItems.get(propertiesPosition).getId());
        startActivity(intent);
    }
}
