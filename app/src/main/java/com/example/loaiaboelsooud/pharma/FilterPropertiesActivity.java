package com.example.loaiaboelsooud.pharma;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import java.util.Arrays;
import java.util.HashMap;

import me.srodrigo.androidhintspinner.HintAdapter;
import me.srodrigo.androidhintspinner.HintSpinner;

public class FilterPropertiesActivity extends NavMenuInt {
    private Button searchButton;
    private HashMap<String, String> propertiesParam;
    private Spinner listedForSpinner, typeSpinner, citiesSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_properties);
        intNavToolBar();
        final PharmaConstants pharmaConstants = new PharmaConstants(this);
        listedForSpinner = findViewById(R.id.properties_filter_listed_for);
        typeSpinner = findViewById(R.id.properties_filter_type);
        citiesSpinner = findViewById(R.id.properties_filter_cities);
        searchButton = findViewById(R.id.properties_search_button);
        initCitySpinner();
        initListedForSpinner();
        initTypeSpinner();
        propertiesParam = new HashMap();
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listedForSpinner.getSelectedItemId() < listedForSpinner.getCount() && PharmaConstants.listedForMapAdd.get(listedForSpinner.getSelectedItem().toString()) != null) {
                    propertiesParam.put(PharmaConstants.LISTEDFOR, PharmaConstants.listedForMapAdd.get(listedForSpinner.getSelectedItem().toString()));
                }
                if (typeSpinner.getSelectedItemId() < typeSpinner.getCount() && PharmaConstants.typeMapAdd.get(typeSpinner.getSelectedItem().toString()) != null) {
                    propertiesParam.put(PharmaConstants.TYPE, PharmaConstants.typeMapAdd.get(typeSpinner.getSelectedItem().toString()));
                }
                if (citiesSpinner.getSelectedItemId() < citiesSpinner.getCount() && PharmaConstants.citiesMapAdd.get(citiesSpinner.getSelectedItem().toString()) != null) {
                    propertiesParam.put(PharmaConstants.CITIES, PharmaConstants.citiesMapAdd.get(citiesSpinner.getSelectedItem().toString()));
                }
                finish();
                Intent intent = new Intent(FilterPropertiesActivity.this, ViewPropertiesActivity.class);
                intent.putExtra(PharmaConstants.ISFILTERED, true);
                intent.putExtra("propertiesParam", propertiesParam);
                startActivity(intent);
            }
        });
    }

    private void initCitySpinner() {
        HintSpinner<String> hintSpinner = new HintSpinner<>(
                citiesSpinner,
                new HintAdapter<>(this, R.string.properties_filter_city, Arrays.asList(getResources().getStringArray(R.array.cities_array))),
                new HintSpinner.Callback<String>() {
                    @Override
                    public void onItemSelected(int position, String itemAtPosition) {
                    }
                });
        hintSpinner.init();
    }

    private void initListedForSpinner() {
        HintSpinner<String> hintSpinner = new HintSpinner<>(
                listedForSpinner,
                new HintAdapter<>(this, R.string.properties_filter_listed_for, Arrays.asList(getResources().getStringArray(R.array.properties_filter_listed_for_array))),
                new HintSpinner.Callback<String>() {
                    @Override
                    public void onItemSelected(int position, String itemAtPosition) {
                    }
                });
        hintSpinner.init();

    }

    private void initTypeSpinner() {
        HintSpinner<String> hintSpinner = new HintSpinner<>(
                typeSpinner,
                new HintAdapter<>(this, R.string.properties_filter_type, Arrays.asList(getResources().getStringArray(R.array.properties_filter_type_array))),
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
        Intent intent = new Intent(FilterPropertiesActivity.this, ViewPropertiesActivity.class);
        intent.putExtra(PharmaConstants.ISFILTERED, false);
        startActivity(intent);
    }
}
