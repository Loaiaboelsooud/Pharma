package com.example.loaiaboelsooud.pharma;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.login.LoginManager;

import java.util.Calendar;


public class MainActivity extends MainMenuInt {

    private GridView gridView;
    private int[] menuImages = {R.drawable.drug_index_menu, R.drawable.drug_interactions_menu, R.drawable.prescription_menu,
            R.drawable.job_menu, R.drawable.properties_menu, R.drawable.promotions_menu};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final HTTPRequests httpRequests = new HTTPRequests(this, new HTTPRequests.IResult() {
        });
        intMainToolBar(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        logoutIfTokenIsExpired(httpRequests);
        final PrefUtil prefUtil = new PrefUtil(this);

        gridView = findViewById(R.id.menugridview);
        CustomAdapter customAdapter = new CustomAdapter();
        gridView.setAdapter(customAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        Intent intent = new Intent(getApplicationContext(), SearchAndViewDrugEyeActivity.class);
                        startActivity(intent);
                        ;
                        break;
                    case 1:
                        Intent intent2 = new Intent(getApplicationContext(), WebViewActivity.class);
                        startActivity(intent2);
                        break;
                    case 2:
                        finish();
                        Intent intent3 = new Intent(getApplicationContext(), ViewPrescriptionsActivity.class);
                        startActivity(intent3);
                        break;
                    case 3:
                        finish();
                        Intent intent4 = new Intent(getApplicationContext(), ViewJobActivity.class);
                        intent4.putExtra(PharmaConstants.ISFILTERED, false);
                        startActivity(intent4);
                        break;
                    case 4:
                        finish();
                        Intent intent5 = new Intent(getApplicationContext(), ViewPropertiesActivity.class);
                        intent5.putExtra(PharmaConstants.ISFILTERED, false);
                        startActivity(intent5);
                        break;
                    case 5:
                        Intent intent6 = new Intent(getApplicationContext(), ViewPromotionsActivity.class);
                        startActivity(intent6);
                        break;
                }
            }
        });
    }

    private class CustomAdapter extends BaseAdapter {
        String[] menuNames = {getResources().getString(R.string.drug_index), getResources().getString(R.string.drug_interactions),
                getResources().getString(R.string.prescription), getResources().getString(R.string.job),
                getResources().getString(R.string.buy_sell), getResources().getString(R.string.promotions)};

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
            View menuView = getLayoutInflater().inflate(R.layout.menu_data, null);
            //getting view in row_data
            //TODO
            TextView name = menuView.findViewById(R.id.menu_text);
            ImageView image = menuView.findViewById(R.id.images);
            name.setText(menuNames[i]);
            image.setImageResource(menuImages[i]);
            return menuView;
        }
    }

    public void logoutIfTokenIsExpired(HTTPRequests httpRequests) {
        final PrefUtil prefUtil = new PrefUtil(this);
        if (prefUtil.isLoggedIn()) {
            Calendar currentTime = Calendar.getInstance();
            Calendar expireDate = prefUtil.getExpireDate();
            currentTime.setTime(currentTime.getTime());
            if (expireDate.before(currentTime)) {
                LoginManager.getInstance().logOut();
                //httpRequests.sendFBPutRequest(this);
            }
        }

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}