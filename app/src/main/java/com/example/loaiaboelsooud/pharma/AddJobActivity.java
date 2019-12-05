package com.example.loaiaboelsooud.pharma;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import me.srodrigo.androidhintspinner.HintAdapter;
import me.srodrigo.androidhintspinner.HintSpinner;

public class AddJobActivity extends NavMenuInt implements HTTPRequests.GetJobPostResult {

    private HTTPRequests httpRequests;
    private ProgressBar progressBar;
    private Spinner citySpinner, regionSpinner, workPlaceSpinner, positionSpinner;
    private Calendar calendar;
    private Button dateButton;
    private EditText runThisAdUntilText;
    private DatePickerDialog datePickerDialog;
    int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job);
        PharmaConstants pharmaConstants = new PharmaConstants(this);
        progressBar = findViewById(R.id.job_post_progress);
        citySpinner = findViewById(R.id.job_city);
        regionSpinner = findViewById(R.id.job_region);
        workPlaceSpinner = findViewById(R.id.job_work_place);
        positionSpinner = findViewById(R.id.job_position);
        dateButton = findViewById(R.id.job_run_this_ad_until_button);
        runThisAdUntilText = findViewById(R.id.job_run_this_ad_until_date);
        initCitySpinner();
        initRegionSpinner();
        initWorkPlaceSpinner();
        initPositionSpinner();
        initDatePicker();
    }

    private void initDatePicker() {
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(AddJobActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                runThisAdUntilText.setText(day + "/" + month + "/" + year);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });
    }

    private void initCitySpinner() {
        HintSpinner<String> hintSpinner = new HintSpinner<>(
                citySpinner,
                new HintAdapter<>(this, R.string.job_city, Arrays.asList(String.valueOf(R.array.cities_array))),
                new HintSpinner.Callback<String>() {
                    @Override
                    public void onItemSelected(int position, String itemAtPosition) {
                        if (PharmaConstants.citesToRegionStringsMap.get(PharmaConstants.citiesMapAdd.get(citySpinner.getSelectedItem()).toString()) != null) {
                            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(AddJobActivity.this,
                                    PharmaConstants.citesToRegionStringsMap.get(PharmaConstants.citiesMapAdd.get(citySpinner.getSelectedItem()).toString()), android.R.layout.simple_spinner_item);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            regionSpinner.setAdapter(adapter);
                        }
                    }
                });
        hintSpinner.init();
    }

    private void initRegionSpinner() {
        HintSpinner<String> hintSpinner = new HintSpinner<>(
                regionSpinner,
                new HintAdapter<>(this, R.string.job_region, Arrays.asList(String.valueOf(R.array.regions_array))),
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
                new HintAdapter<>(this, R.string.job_work_place, Arrays.asList(String.valueOf(R.array.job_work_place_array))),
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
                new HintAdapter<>(this, R.string.job_position, Arrays.asList(String.valueOf(R.array.job_position_array))),
                new HintSpinner.Callback<String>() {
                    @Override
                    public void onItemSelected(int position, String itemAtPosition) {

                    }
                });
        hintSpinner.init();
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
