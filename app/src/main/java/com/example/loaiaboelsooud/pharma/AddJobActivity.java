package com.example.loaiaboelsooud.pharma;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormatSymbols;
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
    private Button dateButton,postButton;
    private EditText dueDate, from, to;
    private DatePickerDialog datePickerDialog;
    private int year, month, day, adjustedMonth;
    private String dueDateString = null;
    private CheckBox negotiableCheckBox;

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
        dateButton = findViewById(R.id.job_due_date_button);
        dueDate = findViewById(R.id.job_due_date);
        from = findViewById(R.id.job_from_salary);
        to = findViewById(R.id.job_to_salary);
        postButton=findViewById(R.id.job_post_button);
        negotiableCheckBox = findViewById(R.id.job_negotiable);
        initNegotiableCheckBox();
        initCitySpinner();
        initRegionSpinner();
        initWorkPlaceSpinner();
        initPositionSpinner();
        initDatePicker();
    }

    private void initNegotiableCheckBox() {
        negotiableCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                                          @Override
                                                          public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                              if (isChecked) {
                                                                  from.setEnabled(false);
                                                                  to.setEnabled(false);
                                                                  from.setText("");
                                                                  to.setText("");
                                                              } else {
                                                                  from.setEnabled(true);
                                                                  to.setEnabled(true);
                                                              }
                                                          }
                                                      }
        );
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
                                dueDate.setText(day + "/" + new DateFormatSymbols().getMonths()[month] + "/" + year);
                                adjustedMonth = month + 1;
                                dueDateString = year + "-" + adjustedMonth + "-" + day;
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });
    }

    private void initCitySpinner() {
        HintSpinner<String> hintSpinner = new HintSpinner<>(
                citySpinner,
                new HintAdapter<>(this, R.string.job_city, Arrays.asList(getResources().getStringArray(R.array.cities_array))),
                new HintSpinner.Callback<String>() {
                    @Override
                    public void onItemSelected(int position, String itemAtPosition) {
                        if (PharmaConstants.citesToRegionStringsMap.get(PharmaConstants.citiesMapAdd.get(citySpinner.getSelectedItem()).toString()) != null) {
                            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(AddJobActivity.this,
                                    PharmaConstants.citesToRegionStringsMap.get(PharmaConstants.citiesMapAdd.get(citySpinner.getSelectedItem()).toString()), android.R.layout.simple_spinner_item);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            regionSpinner.setAdapter(adapter);
                            regionSpinner.setEnabled(true);
                        } else {
                            regionSpinner.setAdapter(null);
                            regionSpinner.setEnabled(false);
                        }
                    }
                });
        hintSpinner.init();
    }

    private void initRegionSpinner() {
        regionSpinner.setEnabled(false);
        HintSpinner<String> hintSpinner = new HintSpinner<>(
                regionSpinner,
                new HintAdapter<>(this, R.string.job_region, Arrays.asList(getResources().getStringArray(R.array.regions_array))),
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
                new HintAdapter<>(this, R.string.job_work_place, Arrays.asList(getResources().getStringArray(R.array.job_work_place_array))),
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
                new HintAdapter<>(this, R.string.job_position, Arrays.asList(getResources().getStringArray(R.array.job_position_array))),
                new HintSpinner.Callback<String>() {
                    @Override
                    public void onItemSelected(int position, String itemAtPosition) {

                    }
                });
        hintSpinner.init();
    }


    public void addJob(View view) {
        postButton.setEnabled(false);
        EditText name, address, description, mobileNumbers,email;
        List<String> mobileNumbersList;
        mobileNumbersList = new ArrayList<String>();
        PrefUtil prefUtil = new PrefUtil(this);
        name = findViewById(R.id.job_name);
        address = findViewById(R.id.job_address);
        description = findViewById(R.id.job_description);
        mobileNumbers = findViewById(R.id.job_mobile);
        email=findViewById(R.id.job_email);
        if (name.getText().toString() != null && !name.getText().toString().isEmpty() &&
                description.getText().toString() != null && !description.getText().toString().isEmpty() &&
                address.getText().toString() != null && !address.getText().toString().isEmpty() &&
                email.getText().toString() != null && !email.getText().toString().isEmpty() &&
                mobileNumbers.getText().toString() != null && !mobileNumbers.getText().toString().isEmpty()
                && regionSpinner.getSelectedItemId() < regionSpinner.getCount()
                && citySpinner.getSelectedItemId() < citySpinner.getCount()
                && workPlaceSpinner.getSelectedItemId() < workPlaceSpinner.getCount()
                && positionSpinner.getSelectedItemId() < positionSpinner.getCount()) {
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
            if (address.getText().toString().length() < 11) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(this, getString(R.string.address_properties_fail),
                        Toast.LENGTH_LONG).show();
            } else if (mobileNumbers.getText().toString().length() < 11) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(this, getString(R.string.mobile_properties_fail),
                        Toast.LENGTH_LONG).show();
            } else if (name.getText().toString().length() < 4) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(this, getString(R.string.name_properties_fail),
                        Toast.LENGTH_LONG).show();
            } else {
                httpRequests.sendJobPostRequest(prefUtil.getToken(), name.getText().toString(), description.getText().toString(),
                        paresedFrom, paresedTo, PharmaConstants.workPlaceMapAdd.get(workPlaceSpinner.getSelectedItem().toString()),
                        PharmaConstants.positionMapAdd.get(positionSpinner.getSelectedItem().toString()),
                        PharmaConstants.citiesMapAdd.get(citySpinner.getSelectedItem().toString()), PharmaConstants.regionsMapAdd.get(regionSpinner.getSelectedItem().toString()),
                        address.getText().toString(), mobileNumbersList, this.dueDateString, negotiableCheckBox.isChecked() ? "1" : "0",email.getText().toString() ,this);
            }
        } else {
            postButton.setEnabled(true);
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
        postButton.setEnabled(true);
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_LONG).show();
    }

}
