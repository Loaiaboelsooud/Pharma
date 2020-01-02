package com.example.loaiaboelsooud.pharma;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.Arrays;
import java.util.HashMap;

import me.srodrigo.androidhintspinner.HintAdapter;
import me.srodrigo.androidhintspinner.HintSpinner;

public class FilterJobActivity extends NavMenuInt {
    private Button searchButton;
    private HashMap<String, String> jobParam;
    private Spinner citySpinner, workPlaceSpinner, positionSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_job);
        intNavToolBar();
        PharmaConstants pharmaConstants = new PharmaConstants(this);
        positionSpinner = findViewById(R.id.job_filter_position);
        workPlaceSpinner = findViewById(R.id.job_filter_work_place);
        citySpinner = findViewById(R.id.job_filter_city);
        searchButton = findViewById(R.id.job_search_button);
        initCitySpinner();
        initPositionSpinner();
        initWorkPlaceSpinner();
        jobParam = new HashMap();
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (positionSpinner.getSelectedItemId() < positionSpinner.getCount() && PharmaConstants.positionMapAdd.get(positionSpinner.getSelectedItem().toString()) != null) {
                    jobParam.put(PharmaConstants.POSITION, PharmaConstants.positionMapAdd.get(positionSpinner.getSelectedItem().toString()));
                }
                if (workPlaceSpinner.getSelectedItemId() < workPlaceSpinner.getCount() && PharmaConstants.workPlaceMapAdd.get(workPlaceSpinner.getSelectedItem().toString()) != null) {
                    jobParam.put(PharmaConstants.WORKPLACE, PharmaConstants.workPlaceMapAdd.get(workPlaceSpinner.getSelectedItem().toString()));
                }
                if (citySpinner.getSelectedItemId() < citySpinner.getCount() && PharmaConstants.citiesMapAdd.get(citySpinner.getSelectedItem().toString()) != null) {
                    jobParam.put(PharmaConstants.CITIES, PharmaConstants.citiesMapAdd.get(citySpinner.getSelectedItem().toString()));
                }
                finish();
                Intent intent = new Intent(FilterJobActivity.this, ViewJobActivity.class);
                intent.putExtra(PharmaConstants.ISFILTERED, true);
                intent.putExtra("jobParam", jobParam);
                startActivity(intent);
            }
        });
    }


    private void initCitySpinner() {
        HintSpinner<String> hintSpinner = new HintSpinner<>(
                citySpinner,
                new HintAdapter<>(this, R.string.properties_filter_city, Arrays.asList(getResources().getStringArray(R.array.cities_array))),
                new HintSpinner.Callback<String>() {
                    @Override
                    public void onItemSelected(int position, String itemAtPosition) {
                    }
                });
        hintSpinner.init();
    }

    private void initWorkPlaceSpinner() {
        HintSpinner<String> hintSpinner = new HintSpinner<>(
                workPlaceSpinner,
                new HintAdapter<>(this, R.string.job_filter_work_place, Arrays.asList(getResources().getStringArray(R.array.job_work_place_array))),
                new HintSpinner.Callback<String>() {
                    @Override
                    public void onItemSelected(int position, String itemAtPosition) {
                    }
                });
        hintSpinner.init();

    }

    private void initPositionSpinner() {
        HintSpinner<String> hintSpinner = new HintSpinner<>(
                positionSpinner,
                new HintAdapter<>(this, R.string.job_filter_position, Arrays.asList(getResources().getStringArray(R.array.job_position_array))),
                new HintSpinner.Callback<String>() {
                    @Override
                    public void onItemSelected(int position, String itemAtPosition) {

                    }
                });
        hintSpinner.init();
    }


    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(FilterJobActivity.this, ViewJobActivity.class);
        intent.putExtra(PharmaConstants.ISFILTERED, false);
        startActivity(intent);
    }
}
