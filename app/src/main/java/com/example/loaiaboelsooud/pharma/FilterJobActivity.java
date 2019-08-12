package com.example.loaiaboelsooud.pharma;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import java.util.HashMap;

public class FilterJobActivity extends NavMenuInt {
    private Button searchButton;
    private HashMap<String, String> jobParam;

    private final String MANAGER = "manager";
    private final String SECONDPHARMACIST = "second_pharmacist";
    private final String OPENCLOSE = "open_close";
    private final String INTERN = "intern";
    private final String ASSISTANT = "assistant";
    private final String PHARMACIST = "pharmacist";
    private final String PHARMACY = "pharmacy";
    private final String COMPANY = "company";
    private final String FACTORY = "factory";
    private final String WAREHOUSE = "warehouse";
    private final String WORKPLACE = "workplace";
    private final String POSITION = "position";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_job);
        intNavToolBar();
        final Spinner position, workPlace;
        position = findViewById(R.id.job_filter_position);
        workPlace = findViewById(R.id.job_filter_work_place);
        searchButton = findViewById(R.id.job_search_button);
        jobParam = new HashMap();
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getWorkPlace((new Long(workPlace.getSelectedItemId())).intValue()) != null) {
                    jobParam.put(WORKPLACE, getWorkPlace((new Long(workPlace.getSelectedItemId())).intValue()));
                }
                if (getPosition((new Long(position.getSelectedItemId())).intValue()) != null) {
                    jobParam.put(POSITION, getPosition((new Long(position.getSelectedItemId())).intValue()));
                }
                finish();
                Intent intent = new Intent(FilterJobActivity.this, ViewJobActivity.class);
                intent.putExtra("isFiltered", true);
                intent.putExtra("jobParam", jobParam);
                startActivity(intent);
            }
        });
    }

    private String getPosition(int id) {
        switch (id) {
            case 0:
                return null;
            case 1:
                return MANAGER;
            case 2:
                return SECONDPHARMACIST;
            case 3:
                return OPENCLOSE;
            case 4:
                return INTERN;
            case 5:
                return ASSISTANT;
            case 6:
                return PHARMACIST;
            default:
                return null;
        }
    }

    private String getWorkPlace(int id) {
        switch (id) {
            case 0:
                return null;
            case 1:
                return PHARMACY;
            case 2:
                return COMPANY;
            case 3:
                return FACTORY;
            case 4:
                return WAREHOUSE;
            default:
                return null;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(FilterJobActivity.this, ViewJobActivity.class);
        intent.putExtra("isFiltered", false);
        startActivity(intent);
    }
}
