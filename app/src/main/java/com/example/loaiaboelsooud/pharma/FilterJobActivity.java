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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_job);
        intNavToolBar();
        PharmaConstants pharmaConstants = new PharmaConstants(this);
        final Spinner positionSpinner, workPlaceSpinner;
        positionSpinner = findViewById(R.id.job_filter_position);
        workPlaceSpinner = findViewById(R.id.job_filter_work_place);
        searchButton = findViewById(R.id.job_search_button);
        jobParam = new HashMap();
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PharmaConstants.positionMapAdd.get(positionSpinner.getSelectedItem().toString()) != null) {
                    jobParam.put(PharmaConstants.POSITION, PharmaConstants.positionMapAdd.get(positionSpinner.getSelectedItem().toString()));
                }
                if (PharmaConstants.workPlaceMapAdd.get(workPlaceSpinner.getSelectedItem().toString()) != null) {
                    jobParam.put(PharmaConstants.WORKPLACE, PharmaConstants.workPlaceMapAdd.get(workPlaceSpinner.getSelectedItem().toString()));
                }
                finish();
                Intent intent = new Intent(FilterJobActivity.this, ViewJobActivity.class);
                intent.putExtra(PharmaConstants.ISFILTERED, true);
                intent.putExtra("jobParam", jobParam);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(FilterJobActivity.this, ViewJobActivity.class);
        intent.putExtra(PharmaConstants.ISFILTERED, false);
        startActivity(intent);
    }
}
