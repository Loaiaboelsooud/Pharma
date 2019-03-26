package com.example.loaiaboelsooud.pharma;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends MainMenuInt {

    GridView gridView;

    int[] menuImages = {R.drawable.drug_index_menu, R.drawable.drug_interactions_menu, R.drawable.prescription_menu,
            R.drawable.job_menu, R.drawable.buy_sell_menu, R.drawable.offers_menu};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intMainToolBar(MainActivity.this);
        gridView = findViewById(R.id.menugridview);

        CustomAdapter customAdapter = new CustomAdapter();
        gridView.setAdapter(customAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        Toast.makeText(getApplicationContext(), "under construction", Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        Toast.makeText(getApplicationContext(), "under construction", Toast.LENGTH_LONG).show();
                        break;
                    case 3:
                        Toast.makeText(getApplicationContext(), "under construction", Toast.LENGTH_LONG).show();
                        break;
                    case 4:
                        Toast.makeText(getApplicationContext(), "under construction", Toast.LENGTH_LONG).show();
                        break;
                    case 5:
                        Toast.makeText(getApplicationContext(), "under construction", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
    }


    private class CustomAdapter extends BaseAdapter {
        String[] menuNames = {getResources().getString(R.string.drug_index), getResources().getString(R.string.drug_interactions),
                getResources().getString(R.string.prescription), getResources().getString(R.string.job),
                getResources().getString(R.string.buy_sell), getResources().getString(R.string.offer)};

        @Override
        public int getCount() {
            return menuImages.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = getLayoutInflater().inflate(R.layout.menu_data, null);
            //getting view in row_data
            TextView name = view1.findViewById(R.id.fruits);
            ImageView image = view1.findViewById(R.id.images);

            name.setText(menuNames[i]);
            image.setImageResource(menuImages[i]);
            return view1;

        }

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}