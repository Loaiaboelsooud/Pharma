package com.example.loaiaboelsooud.pharma;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class NavMenuInt extends AppCompatActivity {

    public void intNavToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar_nav);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_nav, menu);

        final MenuItem promotionsItem = menu.findItem(R.id.action_offers);
        promotionsItem.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(NavMenuInt.this, ViewPromotionsActivity.class);
                startActivity(intent);
            }
        });

        final MenuItem propertiesItem = menu.findItem(R.id.action_buysell);
        propertiesItem.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(NavMenuInt.this, ViewPropertiesActivity.class);
                intent.putExtra("isFiltered", false);
                startActivity(intent);
            }
        });

        final MenuItem jobItem = menu.findItem(R.id.action_job);
        jobItem.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(NavMenuInt.this, ViewJobActivity.class);
                intent.putExtra("isFiltered", false);
                startActivity(intent);
            }
        });

        final MenuItem presecriptionItem = menu.findItem(R.id.action_presecription);
        presecriptionItem.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(NavMenuInt.this, ViewPrescriptionsActivity.class);
                startActivity(intent);
            }
        });

        final MenuItem druginteractionItem = menu.findItem(R.id.action_druginteraction);
        druginteractionItem.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* if (!NavMenuInt.this.getClass().getSimpleName().equals("WebViewActivity")) {
                    druginteractionItem.setActionView(
                            R.layout.toolbar_druginteraction_image_active);*/
                finish();
                Intent intent = new Intent(NavMenuInt.this, WebViewActivity.class);
                startActivity(intent);
            }/* else
                    Toast.makeText(getApplicationContext(), "unable to go", Toast.LENGTH_SHORT).show();
            }*/
        });

        final MenuItem drugindexItem = menu.findItem(R.id.action_drugindex);
        drugindexItem.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "under construction", Toast.LENGTH_SHORT).show();
            }
        });
        switch (NavMenuInt.this.getClass().getSimpleName()) {
            case "WebViewActivity":
                druginteractionItem.setActionView(
                        R.layout.toolbar_druginteraction_image_active);
                break;
            case "ViewPrescriptionsActivity":
                presecriptionItem.setActionView(R.layout.toolbar_presecription_image_active);
                break;
            case "ViewPropertiesActivity":
                propertiesItem.setActionView(R.layout.toolbar_properties_image_active);
                break;
            case "ViewJobActivity":
                jobItem.setActionView(R.layout.toolbar_job_image_active);
                break;
            case "ViewPromotionsActivity":
                promotionsItem.setActionView(R.layout.toolbar_promotions_image_active);
                break;
        }
        return super.onCreateOptionsMenu(menu);
    }

}
