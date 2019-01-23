package com.example.loaiaboelsooud.pharma;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends MainMenuInt {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intMainToolBar();

    }

    public void goToWebViewActivity(View view) {
        Intent intent = new Intent(this, WebViewActivity.class);
        startActivity(intent);
    }
}