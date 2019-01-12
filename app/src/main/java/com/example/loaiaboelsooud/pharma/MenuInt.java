package com.example.loaiaboelsooud.pharma;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MenuInt extends AppCompatActivity {


   public void intToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);

        final MenuItem item = menu.findItem(R.id.action_favorite);
        item.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "hobaaa1", Toast.LENGTH_SHORT).show();
            }
        });
        final MenuItem item2 = menu.findItem(R.id.action_favorite2);
        item2.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "hobaaa2", Toast.LENGTH_SHORT).show();
            }
        });
        final MenuItem item3 = menu.findItem(R.id.action_favorite3);
        item3.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "hobaaa3", Toast.LENGTH_SHORT).show();
            }
        });
        final MenuItem item4 = menu.findItem(R.id.action_favorite4);
        item4.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "hobaaa4", Toast.LENGTH_SHORT).show();
            }
        });
        final MenuItem item5 = menu.findItem(R.id.action_favorite5);
        item5.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "hobaaa5", Toast.LENGTH_SHORT).show();
            }
        });
        final MenuItem item6 = menu.findItem(R.id.action_favorite5);
        item6.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "hobaaa5", Toast.LENGTH_SHORT).show();
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

  /*  @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_favorite:
                Toast.makeText(getApplicationContext(), "hobaaa1", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_favorite2:
                Toast.makeText(getApplicationContext(), "hobaaa2", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_favorite3:
                Toast.makeText(getApplicationContext(), "hobaaa3", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_favorite4:
                Toast.makeText(getApplicationContext(), "hobaaa4", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_favorite5:
                Toast.makeText(getApplicationContext(), "hobaaa5", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_favorite6:
                Toast.makeText(getApplicationContext(), "hobaaa6", Toast.LENGTH_LONG).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
}
