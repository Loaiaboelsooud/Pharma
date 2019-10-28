package com.example.loaiaboelsooud.pharma;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ViewPrescriptionsActivity extends NavMenuInt implements HTTPRequests.GetPrescriptionsList, PrescriptionsAdapter.OnPrescriptionsClickListener {
    public RecyclerView prescriptionsRecyclerView;
    public List<PrescriptionsItem> prescriptionsItems;
    private Boolean isScrolling = false;
    private int currentItems, totalItems, scrollOutItems;
    private LinearLayoutManager manager;
    private HTTPRequests httpRequests;
    private PrefUtil prefUtil;
    private Adapter adapter;
    private boolean testbol = false;
    private int actualPage;
    private ProgressBar progressBar;
    private FloatingActionButton addPrescriptionButton;
    private TextView prescriptionsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        prefUtil = new PrefUtil(this);
        httpRequests = new HTTPRequests(this, new HTTPRequests.IResult() {
        });
        httpRequests.sendPrescriptionsGetRequest(this, 1);
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_view_prescriptions);
        intNavToolBar();
        adapter = new PrescriptionsAdapter(this, prescriptionsItems, this);
        manager = new LinearLayoutManager(this);
        prescriptionsRecyclerView = findViewById(R.id.presecription_recycler);
        prescriptionsRecyclerView.setLayoutManager(manager);
        addPrescriptionButton = findViewById(R.id.add_prescription_button);
        prescriptionsText = findViewById(R.id.prescriptions_text);
        progressBar = findViewById(R.id.presecription_get_progress);
        progressBar.setVisibility(View.VISIBLE);
        if (!prefUtil.isLoggedIn()) {
            addPrescriptionButton.hide();
            Toast.makeText(this, getString(R.string.Signin_prescriptions_toast), Toast.LENGTH_SHORT).show();
        } else {
            addPrescriptionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ViewPrescriptionsActivity.this, AddPrescriptionsActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public void notifyList(final List<PrescriptionsItem> prescriptionsItems, final MetaData metaData) {
        if (!testbol) {
            testbol = true;
            actualPage = metaData.getPagination().getCurrentPage();
            this.prescriptionsItems = prescriptionsItems;
            adapter = new PrescriptionsAdapter(this, this.prescriptionsItems, this);
            prescriptionsRecyclerView.setAdapter(adapter);
        }
        if (actualPage != 1) {
            this.prescriptionsItems.addAll(prescriptionsItems);
        }
        actualPage++;
        adapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);
        prescriptionsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
                if (prescriptionsItems.size() < 4) {
                    if (prefUtil.isLoggedIn()) {
                        if (newState == 1) {
                            if (addPrescriptionButton.isShown()) {
                                addPrescriptionButton.hide();
                            }
                        } else if (newState == 2) {
                            if (!addPrescriptionButton.isShown()) {
                                addPrescriptionButton.show();
                            }
                        }
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = manager.getChildCount();
                totalItems = manager.getItemCount();
                scrollOutItems = manager.findFirstVisibleItemPosition();
                if (prefUtil.isLoggedIn()) {
                    if (dy > 0) {
                        if (addPrescriptionButton.isShown()) {
                            addPrescriptionButton.hide();
                        }
                        if ((prescriptionsText.getTextSize() / getResources().getDisplayMetrics().scaledDensity) > 25) {
                            prescriptionsText.setTextSize((prescriptionsText.getTextSize() / getResources().getDisplayMetrics().scaledDensity) - 1);
                        }
                    } else if (dy < 0) {
                        if (!addPrescriptionButton.isShown()) {
                            addPrescriptionButton.show();
                        }
                        if ((prescriptionsText.getTextSize() / getResources().getDisplayMetrics().scaledDensity) < 35)
                            prescriptionsText.setTextSize((prescriptionsText.getTextSize() / getResources().getDisplayMetrics().scaledDensity) + 1);
                    }
                } else {
                    if (dy > 0) {
                        if ((prescriptionsText.getTextSize() / getResources().getDisplayMetrics().scaledDensity) > 25) {
                            prescriptionsText.setTextSize((prescriptionsText.getTextSize() / getResources().getDisplayMetrics().scaledDensity) - 1);
                        }
                    } else if (dy < 0) {
                        if ((prescriptionsText.getTextSize() / getResources().getDisplayMetrics().scaledDensity) < 35)
                            prescriptionsText.setTextSize((prescriptionsText.getTextSize() / getResources().getDisplayMetrics().scaledDensity) + 1);
                    }
                }
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
            httpRequests.sendPrescriptionsGetRequest(this, metaData.getPagination().getCurrentPage() + 1);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(ViewPrescriptionsActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onPrescriptionsClick(int prescriptionPosition) {
        Intent intent = new Intent(ViewPrescriptionsActivity.this, PrescriptionsCommentsActivity.class);
        intent.putExtra("postId", prescriptionsItems.get(prescriptionPosition).getId());
        intent.putExtra("picture", prescriptionsItems.get(prescriptionPosition).getImage());
        startActivity(intent);
    }
}
