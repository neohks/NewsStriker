package com.nksexample.newsstrike;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;

public class SettingsActivity extends AppCompatActivity {

    private SwitchCompat switchTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkTheme);
        } else {
            setTheme(R.style.LightTheme);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        switchTheme = findViewById(R.id.switchTheme);
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            switchTheme.setChecked(true);
        }

        switchTheme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    AppCompatDelegate.setDefaultNightMode((AppCompatDelegate.MODE_NIGHT_YES));
                    restarApp();
                }
                else {
                    AppCompatDelegate.setDefaultNightMode((AppCompatDelegate.MODE_NIGHT_NO));
                    restarApp();
                }
            }
        });

    }

    private void restarApp() {
        Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(intent);
        finish();
    }

}