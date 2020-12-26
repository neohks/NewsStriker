package com.nksexample.newsstrike;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public boolean isLoggedIn = false;
    TextView tvMainTest;
    Switch sLoginOut;
    
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        tvMainTest = findViewById(R.id.tvMainTest);
//        sLoginOut = findViewById(R.id.sLoginOut);
//
//        sLoginOut.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
////                Toast.makeText(MainActivity.this, String.valueOf(isChecked), Toast.LENGTH_SHORT).show();
//                isLoggedIn = isChecked;
//                updateUI();
//            }
//        });
//        updateUI();


        //Associations
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);

        //Setup Tablayout with ViewPager
        prepareViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

    }

    void updateUI(){
        String text = isLoggedIn ? "Logged In" : "Logged Out";
        tvMainTest.setText(text);
        invalidateOptionsMenu();
    }

    //Pager
    private void prepareViewPager(ViewPager vp){
        //Initialize VPager
        VPagerAdapter adapter = new VPagerAdapter(getSupportFragmentManager());

        //Initialize all fragments
        LocalNewsFragment localNewsFragment = new LocalNewsFragment();
        WorldNewsFragment worldNewsFragment = new WorldNewsFragment();
        TrendNewsFragment trendNewsFragment = new TrendNewsFragment();

        adapter.addFragment(localNewsFragment, "Local");
        adapter.addFragment(worldNewsFragment, "World");
        adapter.addFragment(trendNewsFragment, "Trend");

        viewPager.setAdapter(adapter);

    }

    //Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menudots, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.removeItem(isLoggedIn ? R.id.menuLogin : R.id.menuLogout);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = null;
        int id = item.getItemId();
        String username = "johndoe";

        switch(id){
            case R.id.menuFeedback:
                intent = new Intent(MainActivity.this, FeedbackActivity.class);
                startActivity(intent);
                break;
            case R.id.menuLogout:
                intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
                break;
            case R.id.menuLogin:
                intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.menuSettings:
                intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }

        return true;
    }







}