package com.example.loaiaboelsooud.pharma;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ablanco.zoomy.Zoomy;
import com.bumptech.glide.Glide;

import java.util.List;

public class PrescriptionsCommentsActivity extends AppCompatActivity implements
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
    private TextView userName, createdAT;
    private ImageView image, profilePicture;
    private String imageUrl, profilePictureUrl, userNameString, createdATString;
    private ImageButton addCommentButton;
    private ProgressBar progressBar;
    private LinearLayout addCommentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        postId = extras.getInt("postId");
        imageUrl = extras.getString("image");
        profilePictureUrl = extras.getString("profilePicture");
        userNameString = extras.getString("userName");
        createdATString = extras.getString("createdAT");
        prefUtil = new PrefUtil(this);
        httpRequests = new HTTPRequests(this, new HTTPRequests.IResult() {
        });
        httpRequests.sendPrescriptionsCommentGetRequest(this, postId, 1);
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_prescriptions_comments);
        adapter = new PrescriptionsCommentsAdapter(this, prescriptionsComments);
        manager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        prescriptionsRecyclerView = findViewById(R.id.presecription_comment_recycler);
        prescriptionsRecyclerView.setLayoutManager(manager);
        image = findViewById(R.id.prescription_image);
        progressBar = findViewById(R.id.presecriptions_comments_progress);
        Zoomy.Builder builder = new Zoomy.Builder(this).target(image);
        builder.register();
        addCommentLayout = findViewById(R.id.add_comment_layout);
        userName = findViewById(R.id.prescription_user_name);
        createdAT = findViewById(R.id.prescription_created_at);
        profilePicture = findViewById(R.id.prescription_user_profile_picture);
        commentText = findViewById(R.id.commentText);
        userName.setText(userNameString);
        createdAT.setText(PrefUtil.splitDateTime(createdATString));
        Glide.with(this).load(profilePictureUrl).into(profilePicture);
        Glide.with(this).load(imageUrl).into(image);
        progressBar.setVisibility(View.VISIBLE);
        if (!prefUtil.isLoggedIn()) {
            addCommentButton = findViewById(R.id.addCommentButton);
            addCommentButton.setVisibility(View.GONE);
            commentText.setVisibility(View.GONE);
            addCommentLayout.setVisibility(View.GONE);
            Toast.makeText(this, getString(R.string.Signin_comment_toast), Toast.LENGTH_SHORT).show();
        }
    }

    public void addComment(View view) {
        if (commentText.getText().toString().length() != 0) {
            progressBar.setVisibility(View.VISIBLE);
            httpRequests.sendPrescriptionsCommentPostRequest(prefUtil.getToken(), this,
                    postId, commentText.getText().toString());
            commentText.setText("");
            prefUtil.hideKeyboard(this);
        } else {
            Toast.makeText(this, getString(R.string.add_comment_toast), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void notifyList(PrescriptionsComments prescriptionsComment) {
        progressBar.setVisibility(View.GONE);
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
        progressBar.setVisibility(View.GONE);
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
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_LONG).show();
    }

    private void loadMore(MetaData metaData) {
        if (metaData.getPagination().getTotalPages() > metaData.getPagination().getCurrentPage()) {
            progressBar.setVisibility(View.VISIBLE);
            httpRequests.sendPrescriptionsCommentGetRequest(this, postId,
                    metaData.getPagination().getCurrentPage() + 1);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onBackPressed() {
        finish();

    }


}
