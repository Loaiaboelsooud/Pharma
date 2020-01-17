package com.example.loaiaboelsooud.pharma;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ViewPromotionsActivity extends NavMenuInt implements HTTPRequests.GetPromotionsList {
    public RecyclerView promotionsRecyclerView;
    public List<PromotionsItem> promotionsItems;
    private Boolean isScrolling = false, testbol = false;
    private int currentItems, totalItems, scrollOutItems, actualPage;
    private LinearLayoutManager manager;
    private HTTPRequests httpRequests;
    private PrefUtil prefUtil;
    private RecyclerView.Adapter adapter;
    private ProgressBar progressBar;
    private TextView promotionsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        prefUtil = new PrefUtil(this);
        httpRequests = new HTTPRequests(this, new HTTPRequests.IResult() {
        });

        httpRequests.sendPromotionsGetRequest(this, 1);
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_view_promotions);
        intNavToolBar();
        adapter = new PromotionsAdapter(this, promotionsItems);
        manager = new LinearLayoutManager(this);
        promotionsText=findViewById(R.id.promotions_text);
        promotionsRecyclerView = findViewById(R.id.promotions_recycler);
        promotionsRecyclerView.setLayoutManager(manager);
        progressBar = findViewById(R.id.promotions_get_progress);
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void notifyList(final List<PromotionsItem> promotionsItems, final MetaData metaData) {
        if (!testbol) {
            testbol = true;
            actualPage = metaData.getPagination().getCurrentPage();
            this.promotionsItems = promotionsItems;
            adapter = new PromotionsAdapter(this, this.promotionsItems);
            promotionsRecyclerView.setAdapter(adapter);
        }
        if (actualPage != 1) {
            this.promotionsItems.addAll(promotionsItems);
        }
        actualPage++;
        adapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);
        promotionsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                if (dy > 0) {
                    if ((promotionsText.getTextSize() / getResources().getDisplayMetrics().scaledDensity) > 25) {
                        promotionsText.setTextSize((promotionsText.getTextSize() / getResources().getDisplayMetrics().scaledDensity) - 1);
                    }
                } else if (dy < 0) {
                    if ((promotionsText.getTextSize() / getResources().getDisplayMetrics().scaledDensity) < 35) {
                        promotionsText.setTextSize((promotionsText.getTextSize() / getResources().getDisplayMetrics().scaledDensity) + 1);
                    }
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
            httpRequests.sendPromotionsGetRequest(this, metaData.getPagination().getCurrentPage() + 1);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
