package com.example.loaiaboelsooud.pharma;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class NavMenuInt extends AppCompatActivity {
    private ImageButton promotionsImageButton, propertiesImageButton, jobImageButton,
            prescriptionImageButton, drugInteractionsImageButton, drugIndexImageButton;

    public void intNavToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar_nav);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        promotionsImageButton = findViewById(R.id.promotions_image_button);
        propertiesImageButton = findViewById(R.id.properties_image_button);
        jobImageButton = findViewById(R.id.job_image_button);
        prescriptionImageButton = findViewById(R.id.prescription_image_button);
        drugInteractionsImageButton = findViewById(R.id.drug_interactions_image_button);
        drugIndexImageButton = findViewById(R.id.drug_index_image_button);


        promotionsImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(NavMenuInt.this, ViewPromotionsActivity.class);
                startActivity(intent);
            }
        });

        propertiesImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(NavMenuInt.this, ViewPropertiesActivity.class);
                intent.putExtra(PharmaConstants.ISFILTERED, false);
                startActivity(intent);
            }
        });

        jobImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(NavMenuInt.this, ViewJobActivity.class);
                intent.putExtra(PharmaConstants.ISFILTERED, false);
                startActivity(intent);
            }
        });

        prescriptionImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(NavMenuInt.this, ViewPrescriptionsActivity.class);
                startActivity(intent);
            }
        });

        drugInteractionsImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(NavMenuInt.this, WebViewActivity.class);
                startActivity(intent);
            }
        });

        drugIndexImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(NavMenuInt.this, SearchAndViewDrugEyeActivity.class);
                startActivity(intent);
            }
        });

        switch (NavMenuInt.this.getClass().getSimpleName()) {
            case "WebViewActivity":
                drugInteractionsImageButton.setImageResource(R.drawable.drug_interactions_toolbar_active);
                prescriptionImageButton.setEnabled(true);
                promotionsImageButton.setEnabled(true);
                drugInteractionsImageButton.setEnabled(false);
                propertiesImageButton.setEnabled(true);
                jobImageButton.setEnabled(true);
                drugIndexImageButton.setEnabled(true);
                break;
            case "ViewPrescriptionsActivity":
                prescriptionImageButton.setImageResource(R.drawable.prescription_toolbar_active);
                prescriptionImageButton.setEnabled(false);
                promotionsImageButton.setEnabled(true);
                drugInteractionsImageButton.setEnabled(true);
                propertiesImageButton.setEnabled(true);
                jobImageButton.setEnabled(true);
                drugIndexImageButton.setEnabled(true);
                break;
            case "ViewPropertiesActivity":
                propertiesImageButton.setImageResource(R.drawable.properties_toolbar_active);
                prescriptionImageButton.setEnabled(true);
                promotionsImageButton.setEnabled(true);
                drugInteractionsImageButton.setEnabled(true);
                propertiesImageButton.setEnabled(false);
                jobImageButton.setEnabled(true);
                drugIndexImageButton.setEnabled(true);
                break;
            case "ViewJobActivity":
                jobImageButton.setImageResource(R.drawable.job_toolbar_active);
                prescriptionImageButton.setEnabled(true);
                promotionsImageButton.setEnabled(true);
                drugInteractionsImageButton.setEnabled(true);
                propertiesImageButton.setEnabled(true);
                jobImageButton.setEnabled(false);
                drugIndexImageButton.setEnabled(true);
                break;
            case "ViewPromotionsActivity":
                promotionsImageButton.setImageResource(R.drawable.promotions_toolbar_active);
                prescriptionImageButton.setEnabled(true);
                promotionsImageButton.setEnabled(false);
                drugInteractionsImageButton.setEnabled(true);
                propertiesImageButton.setEnabled(true);
                jobImageButton.setEnabled(true);
                drugIndexImageButton.setEnabled(true);
                break;
            case "SearchAndViewDrugEyeActivity":
                drugIndexImageButton.setImageResource(R.drawable.drug_index_toolbar_active);
                prescriptionImageButton.setEnabled(true);
                promotionsImageButton.setEnabled(true);
                drugInteractionsImageButton.setEnabled(true);
                propertiesImageButton.setEnabled(true);
                jobImageButton.setEnabled(true);
                drugIndexImageButton.setEnabled(false);
                break;
        }
        return super.onCreateOptionsMenu(menu);
    }

}
