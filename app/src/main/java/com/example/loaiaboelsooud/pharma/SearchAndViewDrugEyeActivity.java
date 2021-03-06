package com.example.loaiaboelsooud.pharma;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class SearchAndViewDrugEyeActivity extends NavMenuInt implements SearchAndViewDrugEyeAdapter.OnDrugEyeClickListener, DrugEyeAsyncTasks.OnInsertCompleted, HTTPRequests.GetDrugEyeVersion {
    public RecyclerView drugEyeRecyclerView;
    private HTTPRequests httpRequests;
    private PrefUtil prefUtil;
    private RecyclerView.Adapter adapter;
    private ProgressBar progressBar;
    private Button similaritiesButton, alternativesButton, viewButton;
    private EditText drugEyeSearchKeyWord;
    private List<DrugEyeItem> drugEyeItems;
    private DrugItemDao drugItemDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LinearLayoutManager manager;
        drugEyeItems = new ArrayList<>();
        prefUtil = new PrefUtil(this);

        httpRequests = new HTTPRequests(this, new HTTPRequests.IResult() {
        });
        drugItemDao = RoomDatabaseClient.getInstance().drugItemDao();
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_search_and_view_drug_eye);
        intNavToolBar();
        checkDrugEyeVersion();
        adapter = new SearchAndViewDrugEyeAdapter(this, drugEyeItems, this);
        manager = new LinearLayoutManager(this);
        drugEyeRecyclerView = findViewById(R.id.drug_eye_recycler);
        drugEyeRecyclerView.setLayoutManager(manager);
        progressBar = findViewById(R.id.drug_eye_get_progress);
        similaritiesButton = findViewById(R.id.drug_eye_similarities_button);
        alternativesButton = findViewById(R.id.drug_eye_alternatives_button);
        drugEyeSearchKeyWord = findViewById(R.id.drug_eye_search_key_word);
        viewButton = findViewById(R.id.drug_eye_view_button);
        drugEyeSearchKeyWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                drugEyeItems.clear();
                viewButton.setVisibility(View.GONE);
                similaritiesButton.setVisibility(View.GONE);
                alternativesButton.setVisibility(View.GONE);
                if (drugEyeSearchKeyWord.getText().toString() != null && !drugEyeSearchKeyWord.getText().toString().trim().isEmpty()) {
                    String[] words = drugEyeSearchKeyWord.getText().toString().trim().split(" ");
                    DrugEyeAsyncTasks.getDrugEyeItems(words.length >= 1 ? words[0].toUpperCase().trim() : "", words.length >= 2 ? words[1].toUpperCase() : "",
                            words.length >= 3 ? words[2].toUpperCase() : "", words.length >= 4 ? words[3].toUpperCase() : ""
                            , drugItemDao).observe(SearchAndViewDrugEyeActivity.this, new Observer<List<DrugEyeItem>>() {
                        @Override
                        public void onChanged(@Nullable List<DrugEyeItem> drugEyeItemList) {
                            drugEyeItems.addAll(drugEyeItemList);
                            drugEyeRecyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                } else {
                    drugEyeItems.clear();
                    adapter.notifyDataSetChanged();
                }
            }
        });
        alternativesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (viewButton.getText() != null && !viewButton.getText().toString().isEmpty()) {
                    progressBar.setVisibility(View.VISIBLE);
                    drugEyeItems.clear();
                    DrugEyeAsyncTasks.getAlternatives(viewButton.getText().toString().toUpperCase(), drugItemDao).observe(SearchAndViewDrugEyeActivity.this, new Observer<List<DrugEyeItem>>() {
                        @Override
                        public void onChanged(@Nullable List<DrugEyeItem> drugEyeItemList) {
                            drugEyeItems.addAll(drugEyeItemList);
                            drugEyeRecyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                } else {
                    Toast.makeText(SearchAndViewDrugEyeActivity.this, getString(R.string.drug_eye_select_drug), Toast.LENGTH_LONG).show();
                }
            }
        });

        similaritiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewButton.getText() != null && !viewButton.getText().toString().isEmpty()) {
                    drugEyeItems.clear();
                    progressBar.setVisibility(View.VISIBLE);
                    DrugEyeAsyncTasks.getSimilarities(viewButton.getText().toString(), drugItemDao).observe(SearchAndViewDrugEyeActivity.this, new Observer<List<DrugEyeItem>>() {
                        @Override
                        public void onChanged(@Nullable List<DrugEyeItem> drugEyeItemList) {
                            drugEyeItems.addAll(drugEyeItemList);
                            drugEyeRecyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                } else {
                    Toast.makeText(SearchAndViewDrugEyeActivity.this, getString(R.string.drug_eye_select_drug), Toast.LENGTH_LONG).show();
                }
            }
        });
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drugEyeItems.clear();
                Intent intent = new Intent(SearchAndViewDrugEyeActivity.this, DrugEyeViewActivity.class);
                intent.putExtra("drug_name", viewButton.getText());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewButton.setText("");
        viewButton.setVisibility(View.GONE);
        similaritiesButton.setVisibility(View.GONE);
        alternativesButton.setVisibility(View.GONE);
        drugEyeSearchKeyWord.setText("");
        drugEyeItems.clear();
    }

    private void checkDrugEyeVersion() {
        httpRequests.sendDrugEyeVersionGetRequest(this, this);
    }

    private String loadJSONFromAsset() {
        try {
            InputStream is = this.getAssets().open("drug_index.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

    }

    private void mapDrugEyeJsonFileIntoDrugEyeModel() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject obj = new JSONObject(loadJSONFromAsset());
                    JSONArray drugEyeItemsJsonArray = obj.getJSONArray("data");
                    ArrayList<DrugEyeItem> drugEyeItemList =
                            new Gson().fromJson(drugEyeItemsJsonArray.toString(), new TypeToken<ArrayList<DrugEyeItem>>() {
                            }.getType());
                    DrugEyeAsyncTasks.insertAll(drugEyeItemList, drugItemDao, SearchAndViewDrugEyeActivity.this);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    @Override
    public void success(final Long newDrugEyeVersion) {
        final Long drugEyeVersion = prefUtil.getDrugEyeVersion();

        if (!drugEyeVersion.equals(newDrugEyeVersion)) {
            updateDrugEye(newDrugEyeVersion);
        } else {
            DrugEyeAsyncTasks.countAll(drugItemDao).observe(SearchAndViewDrugEyeActivity.this, new Observer<Integer>() {
                @Override
                public void onChanged(@Nullable Integer count) {
                    if (count < 10000) {
                        updateDrugEye(newDrugEyeVersion);
                    } else {
                        insertSuccess();
                    }
                }
            });
        }
    }

    private void updateDrugEye(Long newDrugEyeVersion) {
        Toast.makeText(this, getString(R.string.drug_eye_updating), Toast.LENGTH_LONG).show();
        progressBar.setVisibility(View.VISIBLE);
        DrugEyeAsyncTasks.deleteAll(drugItemDao);
        mapDrugEyeJsonFileIntoDrugEyeModel();
        prefUtil.saveDrugEyeVersion(newDrugEyeVersion);
        Toast.makeText(this, getString(R.string.drug_eye_updating), Toast.LENGTH_LONG).show();

    }


    public void insertSuccess() {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, getString(R.string.drug_eye_up_to_date), Toast.LENGTH_LONG).show();
    }

    @Override
    public void failed() {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onDrugEyeClick(int drugEyePosition) {
        viewButton.setText(drugEyeItems.get(drugEyePosition).getName());
        viewButton.setVisibility(View.VISIBLE);
        similaritiesButton.setVisibility(View.VISIBLE);
        alternativesButton.setVisibility(View.VISIBLE);
    }
}
