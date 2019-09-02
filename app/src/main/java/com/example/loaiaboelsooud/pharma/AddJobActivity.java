package com.example.loaiaboelsooud.pharma;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddJobActivity extends NavMenuInt implements HTTPRequests.GetJobPostResult {


    private HTTPRequests httpRequests;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job);
        intNavToolBar();
        PharmaConstants pharmaConstants = new PharmaConstants(this);
        progressBar = findViewById(R.id.job_post_progress);

    }

    public void addJob(View view) {
        EditText name, city, region, address, description, mobileNumbers, from, to;
        Spinner positionSpinner, workPlaceSpinner;
        List<String> mobileNumbersList;
        JobsItem jobsItem;
        jobsItem = new JobsItem();
        mobileNumbersList = new ArrayList<String>();
        PrefUtil prefUtil = new PrefUtil(this);
        name = findViewById(R.id.job_name);
        city = findViewById(R.id.job_city);
        region = findViewById(R.id.job_region);
        address = findViewById(R.id.job_address);
        positionSpinner = findViewById(R.id.job_position);
        workPlaceSpinner = findViewById(R.id.job_work_place);
        from = findViewById(R.id.job_from_salary);
        to = findViewById(R.id.job_to_salary);
        description = findViewById(R.id.job_description);
        mobileNumbers = findViewById(R.id.job_mobile);
        if (city.getText().toString() != null && !city.getText().toString().isEmpty() &&
                region.getText().toString() != null && !region.getText().toString().isEmpty() &&
                name.getText().toString() != null && !name.getText().toString().isEmpty() &&
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
                    city.getText().toString(), region.getText().toString(), address.getText().toString(), mobileNumbersList,
                    this);
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
