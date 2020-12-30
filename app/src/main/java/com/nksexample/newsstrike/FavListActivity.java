package com.nksexample.newsstrike;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.nksexample.newsstrike.adapters.FavListAdapter;
import com.nksexample.newsstrike.model.FavModel;

import java.util.ArrayList;
import java.util.List;

public class FavListActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    public static FavListAdapter favListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_list);

        //Initialize Database
        ArrayList<FavModel> favModelList = MainActivity.databaseHelper.listALLFavItems();

        ListView lvFavList = findViewById(R.id.lvFavlist);

        //Adapter
        favListAdapter = new FavListAdapter(this, R.layout.fav_item_layout, favModelList);
        lvFavList.setAdapter(favListAdapter);

        lvFavList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                FavModel fav = favModelList.get(i);
//
//                Intent intent = new Intent(FavListActivity.this, WebViewActivity.class);
//                intent.putExtra("title", fav.getPublisherName());
//                intent.putExtra("url", fav.getUrl());
//
//                startActivity(intent);

            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

    }
}