package com.example.loaiaboelsooud.pharma;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import java.util.HashMap;

public class FilterPropertiesActivity extends NavMenuInt {
    private Button searchButton;
    private CheckBox sellCheckBox, rentCheckBox, pharmacyCheckBox, warehouseCheckBox, factoryCheckBox, hospitalCheckBox;
    private HashMap<String, Boolean> propertiesParam;
    private final String SELLING = "selling", RENTING = "renting", PHARMACY = "pharmacy", WAREHOUSE = "warehouse", FACTORY = "factory", HOSPITAL = "hospital";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_properties);
        intNavToolBar();
        sellCheckBox = findViewById(R.id.properties_filter_sell_check_box);
        rentCheckBox = findViewById(R.id.properties_filter_rent_check_box);
        pharmacyCheckBox = findViewById(R.id.properties_filter_pharmacy_check_box);
        warehouseCheckBox = findViewById(R.id.properties_filter_warehouse_check_box);
        factoryCheckBox = findViewById(R.id.properties_filter_factory_check_box);
        hospitalCheckBox = findViewById(R.id.properties_filter_hospital_check_box);
        searchButton = findViewById(R.id.properties_search_button);
        propertiesParam = new HashMap();
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                propertiesParam.put(SELLING, sellCheckBox.isChecked());
                propertiesParam.put(RENTING, rentCheckBox.isChecked());
                propertiesParam.put(PHARMACY, pharmacyCheckBox.isChecked());
                propertiesParam.put(WAREHOUSE, warehouseCheckBox.isChecked());
                propertiesParam.put(FACTORY, factoryCheckBox.isChecked());
                propertiesParam.put(HOSPITAL, hospitalCheckBox.isChecked());
                finish();
                Intent intent = new Intent(FilterPropertiesActivity.this, ViewPropertiesActivity.class);
                intent.putExtra("isFiltered", true);
                intent.putExtra("propertiesParam", propertiesParam);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(FilterPropertiesActivity.this, ViewPropertiesActivity.class);
        intent.putExtra("isFiltered", false);
        startActivity(intent);
    }
}
