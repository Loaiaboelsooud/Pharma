package com.example.loaiaboelsooud.pharma;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;


public class MainActivity extends AppCompatActivity {

    GridView gridView;
    ImageView avatar;

    int[] menuImages = {R.drawable.drug_index_menu, R.drawable.drug_interactions_menu, R.drawable.prescription_menu,
            R.drawable.job_menu, R.drawable.properties_menu, R.drawable.offers_menu};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final HTTPRequests httpRequests = new HTTPRequests(this, new HTTPRequests.IResult() {
        });
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //httpRequests.sendFBPutRequest(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LayoutInflater myinflater = getLayoutInflater();

        View view = inflater.inflate(R.layout.toolbar_profile_image, (ViewGroup) findViewById(R.id.layout_profile_picture));
        avatar = view.findViewById(R.id.toolbar_profile_picture);
        final PrefUtil prefUtil = new PrefUtil(this);
        User user = prefUtil.getFacebookUserInfo();
        Glide.with(this).load(user.getAvatar()).into(avatar);
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
                        Intent intent2 = new Intent(getApplicationContext(), ViewPrescriptionsActivity.class);
                        startActivity(intent2);
                        break;
                    case 3:
                        Toast.makeText(getApplicationContext(), "under construction", Toast.LENGTH_LONG).show();
                        break;
                    case 4:
                        Intent intent3 = new Intent(getApplicationContext(), ViewPropertiesActivity.class);
                        intent3.putExtra("isFiltered", false);
                        startActivity(intent3);
                        break;
                    case 5:
                        Toast.makeText(getApplicationContext(), "under construction", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);

        final MenuItem profileItem = menu.findItem(R.id.profile);
        final PrefUtil prefUtil = new PrefUtil(this);

        profileItem.setActionView(R.layout.toolbar_profile_image);
        profileItem.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (prefUtil.isLoggedIn()) {
                    finish();
                    Intent intent = new Intent(MainActivity.this, EditAccActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    finish();
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        if (prefUtil.isLoggedIn()) {
            User user = prefUtil.getFacebookUserInfo();
            LayoutInflater myinflater = getLayoutInflater();

            View view = myinflater.inflate(R.layout.toolbar_profile_image, (ViewGroup) findViewById(R.id.layout_profile_picture));
            avatar = view.findViewById(R.id.toolbar_profile_picture);
            Glide.with(this).load(user.getAvatar()).into(avatar);

        }

        return super.onCreateOptionsMenu(menu);
    }

    /*
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            super.onCreateOptionsMenu(menu);
            PrefUtil prefUtil = new PrefUtil(activity);
            User user = prefUtil.getFacebookUserInfo();
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.toolbar_profile_image, null);
            item = view.findViewById(R.id.layout_profile_picture);
            avatar = item.findViewById(R.id.toolbar_profile_picture);
            Picasso.with(this).load(user.getAvatar()).into(avatar);
            return super.onCreateOptionsMenu(menu);

        }
    */
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