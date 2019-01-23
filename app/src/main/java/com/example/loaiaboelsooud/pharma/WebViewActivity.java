package com.example.loaiaboelsooud.pharma;

import android.os.Bundle;
import android.webkit.WebView;

public class WebViewActivity extends NavMenuInt {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        intNavToolBar();
        WebView webView = (WebView) findViewById(R.id.webview);
        webView.loadUrl("https://www.drugs.com/drug_interactions.php?drug_list=");
    }
}
