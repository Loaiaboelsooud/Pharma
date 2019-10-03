package com.example.loaiaboelsooud.pharma;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddJobActivity extends NavMenuInt implements HTTPRequests.GetJobPostResult {

    private HTTPRequests httpRequests;
    private ProgressBar progressBar;
    private Spinner citySpinner, regionSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job);
        intNavToolBar();
        PharmaConstants pharmaConstants = new PharmaConstants(this);
        progressBar = findViewById(R.id.job_post_progress);
        citySpinner = findViewById(R.id.job_city);
        regionSpinner = findViewById(R.id.job_region);

        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (PharmaConstants.citesToRegionStringsMap.get(PharmaConstants.citiesMapAdd.get(citySpinner.getSelectedItem()).toString()) != null) {
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(AddJobActivity.this,
                            PharmaConstants.citesToRegionStringsMap.get(PharmaConstants.citiesMapAdd.get(citySpinner.getSelectedItem()).toString()), android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    regionSpinner.setAdapter(adapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });
    }

    public void addJob(View view) {
        EditText name, address, description, mobileNumbers, from, to;
        Spinner positionSpinner, workPlaceSpinner;
        List<String> mobileNumbersList;
        mobileNumbersList = new ArrayList<String>();
        PrefUtil prefUtil = new PrefUtil(this);
        name = findViewById(R.id.job_name);
        address = findViewById(R.id.job_address);
        positionSpinner = findViewById(R.id.job_position);
        workPlaceSpinner = findViewById(R.id.job_work_place);
        from = findViewById(R.id.job_from_salary);
        to = findViewById(R.id.job_to_salary);
        description = findViewById(R.id.job_description);
        mobileNumbers = findViewById(R.id.job_mobile);
        if (name.getText().toString() != null && !name.getText().toString().isEmpty() &&
                address.getText().toString() != null && !address.getText().toString().isEmpty() &&
                mobileNumbers.getText().toString() != null && !mobileNumbers.getText().toString().isEmpty()) {
            mobileNumbersList.clear();
            mobileNumbersList.add(mobileNumbers.getText().toString());
            progressBar.setVisibility(View.VISIBLE);
            final HTTPRequests httpRequests = new HTTPRequests(this, new HTTPRequests.IResult() {
            });
            int paresedFrom = 0, paresedTo = 0;
            if (!from.getText().toString().equals("")) {
                paresedFrom = Integer.parseInt(from.getText().toString());
            }

            if (!to.getText().toString().equals("")) {
                paresedTo = Integer.parseInt(to.getText().toString());
            }

            httpRequests.sendJobPostRequest(prefUtil.getToken(), name.getText().toString(), description.getText().toString(),
                    paresedFrom, paresedTo, PharmaConstants.workPlaceMapAdd.get(workPlaceSpinner.getSelectedItem().toString()),
                    PharmaConstants.positionMapAdd.get(positionSpinner.getSelectedItem().toString()),
                    PharmaConstants.citiesMapAdd.get(citySpinner.getSelectedItem().toString()), PharmaConstants.regionsMapAdd.get(regionSpinner.getSelectedItem().toString()),
                    address.getText().toString(), mobileNumbersList, this);
        } else {
            Toast.makeText(this, getString(R.string.post_properties_fail),
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(AddJobActivity.this, ViewJobActivity.class);
        intent.putExtra(PharmaConstants.ISFILTERED, false);
        startActivity(intent);
    }

    @Override
    public void success() {
        finish();
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, getString(R.string.post_job_success), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(AddJobActivity.this, ViewJobActivity.class);
        intent.putExtra(PharmaConstants.ISFILTERED, false);
        startActivity(intent);
    }

    @Override
    public void failed() {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_LONG).show();
    }

}
