package com.nksexample.newsstrike;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    private SwitchCompat switchTheme;
    private RadioGroup rgLanguage;
    private RadioGroup rgCountry;
    private RadioButton rbCountry, rbLanguage;

    public static String language, country;
    public static boolean switchOnOff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Dark Theme:
        // Refer : https://developer.android.com/guide/topics/ui/look-and-feel/darktheme
        // Watch : https://www.youtube.com/watch?v=-qsHE3TpJqw

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Association
        switchTheme = findViewById(R.id.switchTheme);
        rgLanguage = findViewById(R.id.rbgLang);
        rgCountry = findViewById(R.id.rbgCountry);


        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            switchTheme.setChecked(true);
        }


        switchTheme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    AppCompatDelegate.setDefaultNightMode((AppCompatDelegate.MODE_NIGHT_YES));
                }
                else {
                    AppCompatDelegate.setDefaultNightMode((AppCompatDelegate.MODE_NIGHT_NO));
                }
                saveData();
                restarApp();
            }
        });

        loadData();
        updateViews();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!isTaskRoot()){
            MainActivity.isFromSettingPage = true;
        }
        supportFinishAfterTransition();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    // Refer : https://www.youtube.com/watch?v=fJEFZ6EOM9o
    public void saveData() {

        SharedPreferences settingPreferences = getSharedPreferences(MainActivity.SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = settingPreferences.edit();

        editor.putBoolean(MainActivity.SHARED_THEME, switchTheme.isChecked());

        if (rbLanguage.getText().toString().equals("Chinese"))
            editor.putString(MainActivity.SHARED_LANGUAGE, "zh");
        else
            editor.putString(MainActivity.SHARED_LANGUAGE, "en");


        if (rbCountry.getText().toString().equals("USA"))
            editor.putString(MainActivity.SHARED_COUNTRY, "US");
        else
            editor.putString(MainActivity.SHARED_COUNTRY, "MY");

        editor.apply();
        Toast.makeText(this,"Data Saved!", Toast.LENGTH_SHORT).show();

    }

    public void loadData() {

        SharedPreferences settingPreferences = getSharedPreferences(MainActivity.SHARED_PREFS, MODE_PRIVATE);
        language = settingPreferences.getString(MainActivity.SHARED_LANGUAGE, "");
        country = settingPreferences.getString(MainActivity.SHARED_COUNTRY, "");
        switchOnOff = settingPreferences.getBoolean(MainActivity.SHARED_THEME, false);

    }

    public void updateViews() {

        if (language.equals("zh"))
            rbLanguage = findViewById(R.id.rbChinese);
        else
            rbLanguage = findViewById(R.id.rbEng);

        rbLanguage.setChecked(true);

        if (country.equals("US"))
            rbCountry = findViewById(R.id.rbUSA);
        else
            rbCountry = findViewById(R.id.rbMalaysia);

        rbCountry.setChecked(true);

        switchTheme.setChecked(switchOnOff);


    }



    private void restarApp() {
        Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(intent);
        finish();
    }

    public void checkCountryButton(View view) {

        int radioID = rgCountry.getCheckedRadioButtonId();
        rbCountry = findViewById(radioID);

        //Save State
        saveData();
//        Toast.makeText(this, "Hi! " + rbCountry.getText().toString().toLowerCase()  , Toast.LENGTH_SHORT).show();

    }


    public void checkLanguageButton(View v) {

        int radioID = rgLanguage.getCheckedRadioButtonId();
        rbLanguage = findViewById(radioID);

        //Save State
        saveData();
//        Toast.makeText(this, "Hi! " + rbLanguage.getText().toString().toLowerCase()  , Toast.LENGTH_SHORT).show();

    }

}