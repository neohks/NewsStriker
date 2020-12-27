package com.nksexample.newsstrike;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class NewsDetailActivity extends AppCompatActivity {

    TextView tvNewsDetailTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        tvNewsDetailTitle = findViewById(R.id.tvNewsDetailTitle);

        // Get Ectras
        tvNewsDetailTitle.setText("");

    }
}