package com.example.sleepwell;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.sleepwell.BackEnd.GlobalEntities;

public class OverviewActivity extends AppCompatActivity {
    TextView tvUname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        findViewById(R.id.sleepHistoryBtn).setOnClickListener(view -> {
            Intent redirect = new Intent(OverviewActivity.this, HistoryActivity.class);
            startActivity(redirect);
            finish();
        });
        tvUname = findViewById(R.id.usernameText);
        tvUname.setText(GlobalEntities.currUser.getUserName());
        findViewById(R.id.trackBtn).setOnClickListener(view -> {
            Intent redirect = new Intent(OverviewActivity.this, TrackActivity.class);
            startActivity(redirect);
            finish();
        });
    }
}
