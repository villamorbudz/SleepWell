package com.example.sleepwell;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class OverviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        findViewById(R.id.sleepHistoryBtn).setOnClickListener(view -> {
            Intent redirect = new Intent(OverviewActivity.this, HistoryActivity.class);
            startActivity(redirect);
            finish();
        });

        findViewById(R.id.trackBtn).setOnClickListener(view -> {
            Intent redirect = new Intent(OverviewActivity.this, TrackActivity.class);
            startActivity(redirect);
            finish();
        });
    }
}
