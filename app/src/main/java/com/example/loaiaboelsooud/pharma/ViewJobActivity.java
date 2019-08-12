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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewJobActivity extends NavMenuInt implements HTTPRequests.GetJobsList {

    public RecyclerView jobRecyclerView;
    public List<JobsItem> jobsItem;
    private Boolean isScrolling = false, testbol = false, isFiltered;
    private int currentItems, totalItems, scrollOutItems, actualPage;
    private LinearLayoutManager manager;
    private HTTPRequests httpRequests;
    private PrefUtil prefUtil;
    private RecyclerView.Adapter adapter;
    private FloatingActionButton addJobButton, filterJobButton;
    private ProgressBar progressBar;
    private Map<String, String> jobParam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        prefUtil = new PrefUtil(this);
        httpRequests = new HTTPRequests(this, new HTTPRequests.IResult() {
        });
        isFiltered = getIntent().getExtras().getBoolean("isFiltered");
        jobParam = new HashMap();
        jobParam = (HashMap<String, String>) getIntent().getExtras().getSerializable("jobParam");
        if (isFiltered) {
            httpRequests.sendJobFilterGetRequest(jobParam,
                    this, 1);
        } else {
            httpRequests.sendJobsGetRequest(this, 1);
        }
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_view_job);
        intNavToolBar();
        adapter = new JobAdapter(this, jobsItem);
        manager = new LinearLayoutManager(this);
        jobRecyclerView = findViewById(R.id.job_recycler);
        jobRecyclerView.setLayoutManager(manager);
        addJobButton = findViewById(R.id.add_job_button);
        progressBar = findViewById(R.id.job_get_progress);
        progressBar.setVisibility(View.VISIBLE);
        filterJobButton = findViewById(R.id.filter_job_button);
        if (!prefUtil.isLoggedIn()) {
            addJobButton.hide();
            //change to properties
            Toast.makeText(this, getString(R.string.Signin_prescriptions_toast), Toast.LENGTH_SHORT).show();
        } else {
            addJobButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                    Intent intent = new Intent(ViewJobActivity.this, AddJobActivity.class);
                    startActivity(intent);
                }
            });
        }
        filterJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(ViewJobActivity.this, FilterJobActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void notifyList(final List<JobsItem> jobsItem, final MetaData metaData) {
        if (!testbol) {
            testbol = true;
            actualPage = metaData.getPagination().getCurrentPage();
            this.jobsItem = jobsItem;
            adapter = new JobAdapter(this, this.jobsItem);
            jobRecyclerView.setAdapter(adapter);
        }
        if (actualPage != 1) {
            this.jobsItem.addAll(jobsItem);
        }
        actualPage++;
        adapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);
        jobRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                httpRequests.sendJobFilterGetRequest(jobParam,
                        this, metaData.getPagination().getCurrentPage() + 1);

            } else {
                httpRequests.sendJobsGetRequest(this, metaData.getPagination().getCurrentPage() + 1);
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
