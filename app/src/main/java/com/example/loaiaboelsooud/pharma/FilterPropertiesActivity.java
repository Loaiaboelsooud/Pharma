package com.example.loaiaboelsooud.pharma;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import java.util.HashMap;

public class FilterPropertiesActivity extends NavMenuInt {
    private Button searchButton;
    private CheckBox sellCheckBox, rentCheckBox, pharmacyCheckBox, warehouseCheckBox, factoryCheckBox, hospitalCheckBox;
    private HashMap<String, String> propertiesParam;
    private Spinner listedForSpinner, typeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_properties);
        intNavToolBar();
        final PharmaConstants pharmaConstants = new PharmaConstants(this);
        listedForSpinner = findViewById(R.id.properties_filter_listed_for);
        typeSpinner = findViewById(R.id.properties_filter_type);
        searchButton = findViewById(R.id.properties_search_button);
        propertiesParam = new HashMap();
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PharmaConstants.listedForMapAdd.get(listedForSpinner.getSelectedItem().toString()) != null) {
                    propertiesParam.put(PharmaConstants.LISTEDFOR, PharmaConstants.listedForMapAdd.get(listedForSpinner.getSelectedItem().toString()));
                }
                if (PharmaConstants.typeMapAdd.get(typeSpinner.getSelectedItem().toString()) != null) {
                    propertiesParam.put(PharmaConstants.TYPE, PharmaConstants.typeMapAdd.get(typeSpinner.getSelectedItem().toString()));
                }
                finish();
                Intent intent = new Intent(FilterPropertiesActivity.this, ViewPropertiesActivity.class);
                intent.putExtra(PharmaConstants.ISFILTERED, true);
                intent.putExtra("propertiesParam", propertiesParam);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(FilterPropertiesActivity.this, ViewPropertiesActivity.class);
        intent.putExtra(PharmaConstants.ISFILTERED, false);
        startActivity(intent);
    }
}
