package com.nksexample.newsstrike;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends AppCompatActivity {

    WebView webViewURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        String title = getIntent().getStringExtra("title");
        String url = getIntent().getStringExtra("url");

        setTitle(title);

        webViewURL = findViewById(R.id.webViewURL);
        webViewURL.getSettings().setLoadsImagesAutomatically(true);
        webViewURL.getSettings().setJavaScriptEnabled(true);
        webViewURL.getSettings().setDomStorageEnabled(true);
        webViewURL.getSettings().setSupportZoom(true);
        webViewURL.getSettings().setBuiltInZoomControls(true);
        webViewURL.getSettings().setDisplayZoomControls(false);
        webViewURL.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webViewURL.setWebViewClient(new WebViewClient());
        webViewURL.loadUrl(url);

    }
}