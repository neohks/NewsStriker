package com.nksexample.newsstrike;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    boolean isLoggedIn = false;
    TextView tvMainTest;
    Switch sLoginOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvMainTest = findViewById(R.id.tvMainTest);
        sLoginOut = findViewById(R.id.sLoginOut);

        sLoginOut.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                Toast.makeText(MainActivity.this, String.valueOf(isChecked), Toast.LENGTH_SHORT).show();
                isLoggedIn = isChecked;
                updateUI();
            }
        });

        updateUI();

    }

    void updateUI(){
        String text = isLoggedIn ? "Logged In" : "Logged Out";
        tvMainTest.setText(text);
        invalidateOptionsMenu();
    }

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