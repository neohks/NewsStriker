package com.nksexample.newsstrike;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nksexample.newsstrike.bottomNav.NewsHomeFragment;
import com.nksexample.newsstrike.bottomNav.SearchFragment;

public class MainActivity extends AppCompatActivity {

    private int INTERNET_PERMISSION_CODE = 111;
    public static final String API_KEY = "b24fd2dbf4fa4d1c9364b00fe1cfeb82";

    // Setting's shared pref
    public static final String SHARED_PREFS = "setsharedprefs";
    public static final String SHARED_LANGUAGE = "language";
    public static final String SHARED_COUNTRY = "country";
    public static final String SHARED_THEME = "theme";

    public static DatabaseHelper databaseHelper;
    
    public boolean isLoggedIn = false;
    private boolean isSearchPage = false;
    TextView tvMainTest;

    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Load preferences
        loadData();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check Internet Permission
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_DENIED)
            requestInternetPermission();

        //Database Initialize
        databaseHelper = new DatabaseHelper(MainActivity.this);
        databaseHelper.listALLFavItems();


        //Setup BottomNavigation
        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavItemSelectListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new NewsHomeFragment()).commit();

    }

    private void requestInternetPermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.INTERNET)){
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Permission Needed")
                    .setMessage("Need to access network to load news.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.INTERNET}, INTERNET_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.INTERNET}, INTERNET_PERMISSION_CODE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == INTERNET_PERMISSION_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show();
        }
    }

    public void loadData() {

        SharedPreferences settingPreferences = getSharedPreferences(MainActivity.SHARED_PREFS, MODE_PRIVATE);

        SettingsActivity.language = settingPreferences.getString(MainActivity.SHARED_LANGUAGE, "");
        SettingsActivity.country = settingPreferences.getString(MainActivity.SHARED_COUNTRY, "");
        SettingsActivity.switchOnOff = settingPreferences.getBoolean(MainActivity.SHARED_THEME, false);

        if (SettingsActivity.switchOnOff)
            AppCompatDelegate.setDefaultNightMode((AppCompatDelegate.MODE_NIGHT_YES));
        else
            AppCompatDelegate.setDefaultNightMode((AppCompatDelegate.MODE_NIGHT_NO));


    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavItemSelectListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment fragment = null;

            switch (item.getItemId()){
                case R.id.btmNavHome:
                    fragment = new NewsHomeFragment();
                    break;
                case R.id.btmNavSearch:
                    fragment = new SearchFragment();
                    break;
                case R.id.btmNavProfile:
                    fragment = new ProfileFragment();
                    break;
                default:
                    break;

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

            return true;
        }
    };

}