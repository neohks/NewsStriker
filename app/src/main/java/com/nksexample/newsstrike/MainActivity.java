package com.nksexample.newsstrike;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

    public static DatabaseHelper databaseHelper;
    
    public boolean isLoggedIn = false;
    private boolean isSearchPage = false;
    TextView tvMainTest;
    Switch sLoginOut;

    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

    void updateUI(){
        String text = isLoggedIn ? "Logged In" : "Logged Out";
        tvMainTest.setText(text);
        invalidateOptionsMenu();
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

    //Menu
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menudots, menu);
//
//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
//
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//        searchView.setQueryHint("Search Latest News...");
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                if (query.length() > 2){
//
//                    Intent intent = new Intent(MainActivity.this, SearchQueryActivity.class); //Showhow show the back buttom
//                    intent.putExtra("query", query);
//                    startActivity(intent);
//                }
//                else {
//                    Toast.makeText(MainActivity.this, "Type more than two letters!", Toast.LENGTH_SHORT).show();
//                }
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//
//                return false;
//            }
//        });
//
////        searchMenuItem.getIcon().setVisible(false, false);
//
//        return true;
//    }









}