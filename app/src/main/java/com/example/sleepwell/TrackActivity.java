package com.example.sleepwell;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;

public class TrackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        findViewById(R.id.overviewBtn).setOnClickListener(view -> {
            Intent redirect = new Intent(TrackActivity.this, OverviewActivity.class);
            startActivity(redirect);
            finish();
        });

        findViewById(R.id.sleepHistoryBtn).setOnClickListener(view -> {
            Intent redirect = new Intent(TrackActivity.this, HistoryActivity.class);
            startActivity(redirect);
            finish();
        });
    }
}