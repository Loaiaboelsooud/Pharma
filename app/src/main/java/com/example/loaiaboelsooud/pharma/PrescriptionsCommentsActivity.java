package com.example.loaiaboelsooud.pharma;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.ablanco.zoomy.Zoomy;
import com.bumptech.glide.Glide;

import java.util.List;

public class PrescriptionsCommentsActivity extends NavMenuInt implements
        HTTPRequests.GetPrescriptionsCommentsList, HTTPRequests.GetPrescriptionsComment {
    public RecyclerView prescriptionsRecyclerView;
    public List<PrescriptionsComments> prescriptionsComments;
    private Boolean isScrolling = false;
    private int currentItems, totalItems, scrollOutItems, actualPage, postId;
    private LinearLayoutManager manager;
    private HTTPRequests httpRequests;
    private PrefUtil prefUtil;
    private RecyclerView.Adapter adapter;
    private boolean testbol = false;
    private EditText commentText;
    private ImageView picture;
    private String imageUrl;
    private ImageButton addCommentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        postId = extras.getInt("postId");
        imageUrl = extras.getString("picture");
        prefUtil = new PrefUtil(this);
        httpRequests = new HTTPRequests(this, new HTTPRequests.IResult() {
        });
        httpRequests.sendPrescriptionsCommentGetRequest(prefUtil.getToken(), this, postId, 1);
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_prescriptions_comments);
        intNavToolBar();
        adapter = new PrescriptionsCommentsAdapter(this, prescriptionsComments);
        manager = new LinearLayoutManager(this);
        prescriptionsRecyclerView = findViewById(R.id.presecription_comment_recycler);
        prescriptionsRecyclerView.setLayoutManager(manager);
        picture = findViewById(R.id.presecription_image);
        Zoomy.Builder builder = new Zoomy.Builder(this).target(picture);
        builder.register();
        commentText = findViewById(R.id.commentText);
        Glide.with(this).load(imageUrl).into(picture);
        if (!prefUtil.isLoggedIn()) {
            addCommentButton = findViewById(R.id.addCommentButton);
            addCommentButton.setVisibility(View.GONE);
            commentText.setVisibility(View.GONE);
            Toast.makeText(this, getString(R.string.Signin_comment_toast), Toast.LENGTH_SHORT).show();
        }
    }

    public void addComment(View view) {
        if (commentText.getText().toString().length() != 0) {
            httpRequests.sendPrescriptionsCommentPostRequest(prefUtil.getToken(), this,
                    postId, commentText.getText().toString());
        } else {
            Toast.makeText(this, getString(R.string.add_comment_toast), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void notifyList(PrescriptionsComments prescriptionsComment) {
        commentText.setText("");
        this.prescriptionsComments.add(prescriptionsComment);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void notifyList(final List<PrescriptionsComments> prescriptionsComments, final MetaData metaData) {
        if (!testbol) {
            testbol = true;
            actualPage = metaData.getPagination().getCurrentPage();
            this.prescriptionsComments = prescriptionsComments;
            adapter = new PrescriptionsCommentsAdapter(this, this.prescriptionsComments);
            prescriptionsRecyclerView.setAdapter(adapter);
        }
        if (actualPage != 1) {
            this.prescriptionsComments.addAll(prescriptionsComments);
        }
        actualPage++;
        // this.prescriptionsComments.addAll(prescriptionsComments);
        adapter.notifyDataSetChanged();
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
        Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_LONG).show();
    }

    private void loadMore(MetaData metaData) {
        if (metaData.getPagination().getTotalPages() > metaData.getPagination().getCurrentPage()) {
            httpRequests.sendPrescriptionsCommentGetRequest(prefUtil.getToken(), this, postId,
                    metaData.getPagination().getCurrentPage() + 1);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onBackPressed() {
        finish();

    }


}
