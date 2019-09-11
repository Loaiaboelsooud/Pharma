package com.example.loaiaboelsooud.pharma;

import android.arch.lifecycle.Observer;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SearchAndViewDrugEyeActivity extends NavMenuInt implements HTTPRequests.GetDrugEyeList, SearchAndViewDrugEyeAdapter.OnDrugEyeClickListener {
    public RecyclerView drugEyeRecyclerView;
    private Boolean isScrolling = false, testbol = false;
    private int currentItems, totalItems, scrollOutItems, actualPage;
    private LinearLayoutManager manager;
    private HTTPRequests httpRequests;
    private PrefUtil prefUtil;
    private RecyclerView.Adapter adapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final List<DrugEyeItem> drugEyeItems = new ArrayList<>();
        prefUtil = new PrefUtil(this);
        httpRequests = new HTTPRequests(this, new HTTPRequests.IResult() {
        });
        DrugItemDao drugItemDao = RoomDatabaseClient.getInstance().drugItemDao();
        List<DrugEyeItem> drugEyeItemss = DrugEyeAsyncTasks.getAlternatives("panadol", drugItemDao).getValue();


        //httpRequests.sendDrugEyeGetRequest(this, this);
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_search_and_view_drug_eye);
        intNavToolBar();
        adapter = new SearchAndViewDrugEyeAdapter(this, drugEyeItems, this);
        manager = new LinearLayoutManager(this);
        drugEyeRecyclerView = findViewById(R.id.drug_eye_recycler);
        drugEyeRecyclerView.setLayoutManager(manager);
        progressBar = findViewById(R.id.drug_eye_get_progress);
        progressBar.setVisibility(View.VISIBLE);
        DrugEyeAsyncTasks.getAll(drugItemDao).observe(this, new Observer<List<DrugEyeItem>>() {
            @Override
            public void onChanged(@Nullable List<DrugEyeItem> drugEyeItemList) {
                drugEyeItems.addAll(drugEyeItemList);
                drugEyeRecyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void notifyList(final List<DrugEyeItem> drugEyeItems) {
      /*  if (!testbol) {
            testbol = true;
            actualPage = 0;
            this.drugEyeItems = drugEyeItems;
            adapter = new SearchAndViewDrugEyeAdapter(this, this.drugEyeItems.subList(0, 15), this);
            drugEyeRecyclerView.setAdapter(adapter);
        }
        if (actualPage != 0) {
            this.drugEyeItems.subList(actualPage + 15, actualPage + 30);
        }
        actualPage += 15;
        adapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);*/

    }

    @Override
    public void insertAll(List<DrugEyeItem> drugEyeItemList) {
        DrugItemDao drugItemDao = RoomDatabaseClient.getInstance().drugItemDao();
        for (DrugEyeItem drugEyeItem : drugEyeItemList) {
            DrugEyeAsyncTasks.insert(drugEyeItem, drugItemDao);
        }
        progressBar.setVisibility(View.GONE);

    }

    @Override
    public void failed() {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_LONG).show();
    }

    private void loadMore(MetaData metaData) {
        if (metaData.getPagination().getTotalPages() > metaData.getPagination().getCurrentPage()) {
            progressBar.setVisibility(View.VISIBLE);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onDrugEyeClick(int druEyePosition) {
     /*   Intent intent = new Intent(ViewPropertiesActivity.this, ViewPropertyActivity.class);
        intent.putExtra("id", drugEyeItems.get(druEyePosition).getId());
        startActivity(intent);*/
    }
}
