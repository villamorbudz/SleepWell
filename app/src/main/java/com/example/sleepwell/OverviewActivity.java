package com.example.sleepwell;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.sleepwell.BackEnd.DBHelpers;
import com.example.sleepwell.BackEnd.GlobalEntities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class OverviewActivity extends AppCompatActivity {
    TextView tvUname;
    Button time;
    Button avg;
    Button status;
    int currYear;
    int currMonth;
    int currDay;

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
        time = findViewById(R.id.totalSleepTime);
        avg = findViewById(R.id.avgSleepDuration);
        status = findViewById(R.id.avgSleepClassification);
        Calendar cd = Calendar.getInstance();
        currYear = cd.get(Calendar.YEAR);
        currMonth = cd.get(Calendar.MONTH)+1;
        currDay = cd.get(Calendar.DATE);

        tvUname = findViewById(R.id.usernameText);
        tvUname.setText(GlobalEntities.currUser.getUserName());
        ArrayList<Integer> slepSec = new ArrayList<>();

        Calendar cal = Calendar.getInstance();
        status.setText("None");
        avg.setText("0");
        //Intent loadingScreen = new Intent(HistoryActivity.this, SignUpActivity.class);
        //startActivity(loadingScreen);
        Runnable run = new Runnable() {
            @Override
            public void run() {
                //loadingScreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                int total = 0;
                for(int num: slepSec){
                    total+=num;
                }
                if(total == 0) return;
                double hour = (total/60)/60.0;
                //Duration.setText(String.format("%.2f",hour));
                time.setText(String.valueOf(total));
                avg.setText(String.valueOf(total/slepSec.size()));
                String stat = "0";
                if(hour > 10){
                    stat = "Amazing";
                }else if(hour >= 7){
                    stat = "Healthy";
                }else if(hour >= 4){
                    stat = "Unhealthy";
                }else{
                    stat = "Deathly";
                }
                status.setText(stat);
                //finish();

            }
        };
        DBHelpers.getSleepData(String.valueOf(currYear),String.valueOf(currMonth+1),String.valueOf(currDay),slepSec,run);

        findViewById(R.id.trackBtn).setOnClickListener(view -> {
            Intent redirect = new Intent(OverviewActivity.this, TrackActivity.class);
            startActivity(redirect);
            finish();
        });
    }
}
