package com.example.sleepwell;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.sleepwell.BackEnd.DBHelpers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class HistoryActivity extends AppCompatActivity {

    ArrayList<Button> baseButtons;
    ArrayList<Button> dayButtons;
    Button prevMonth;
    Button nextMonth;
    int currMonth;
    int currYear;
    int currDay;
    int dayOffset;
    Button status;
    Button Duration;

    HashMap<Integer,HashMap<Integer, HashMap<Integer,Integer>>> yearHash;
    HashMap<Integer,Integer> finalDayHash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        findViewById(R.id.overviewBtn).setOnClickListener(view -> {
            Intent redirect = new Intent(HistoryActivity.this, OverviewActivity.class);
            startActivity(redirect);
            finish();
        });

        findViewById(R.id.trackBtn).setOnClickListener(view -> {
            Intent redirect = new Intent(HistoryActivity.this, TrackActivity.class);
            startActivity(redirect);
            finish();
        });
        status = findViewById(R.id.avgSleepClassification);
        Duration = findViewById(R.id.avgSleepDuration);

        baseButtons = new ArrayList<>();
        dayButtons = new ArrayList<>();
        for (int i = 1; i <= 42; i++) {
            int resId = getResources().getIdentifier("D" + i, "id", getPackageName());
            baseButtons.add(findViewById(resId));
        }

        Calendar cd = Calendar.getInstance();
        yearHash = new HashMap<>();
        currYear = cd.get(Calendar.YEAR);
        currMonth = cd.get(Calendar.MONTH);
        currDay = cd.get(Calendar.DATE);
        moveMonth(currYear, currMonth);

        nextMonth = findViewById(R.id.NextMonthBtn);
        prevMonth = findViewById(R.id.PrevMonthBtn);

        nextMonth.setOnClickListener(v -> setMonth(+1));
        prevMonth.setOnClickListener(v -> setMonth(-1));
    }

    private void moveMonth(int year, int month) {
        TextView myText = findViewById(R.id.MYtext);
        String[] monthsString = {
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        };
        myText.setText(monthsString[month] + " " + year);

        Calendar cal = new GregorianCalendar(year, month, 1);
        int startDayInWeek = cal.get(Calendar.DAY_OF_WEEK);
        dayOffset = startDayInWeek;
        int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (Button btn : baseButtons) {
            btn.setText("");
            btn.setVisibility(View.INVISIBLE);
            btn.setEnabled(false);
        }

        dayButtons = new ArrayList<>();


        for (int x = 0; x < daysInMonth; x++) {
            Button btn = baseButtons.get(startDayInWeek + x - 1);
            int finalX = x;
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(),String.valueOf(finalX),Toast.LENGTH_SHORT);
                    ArrayList<Integer> slepSec = new ArrayList<>();
                    Calendar cal = Calendar.getInstance();
                    String Year = String.valueOf(cal.get(Calendar.YEAR));
                    String Month = String.valueOf(cal.get(Calendar.MONTH)+1);
                    String Day = String.valueOf(finalX+1);
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
                            //if(total == 0) return;
                            double hour = (total/60)/60.0;
                            //Duration.setText(String.format("%.2f",hour));
                            Duration.setText(String.valueOf(total));
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
                    DBHelpers.getSleepData(Year,Month,Day,slepSec,run);
                }
            });

            dayButtons.add(btn);
        }

        for (int x = 0; x < daysInMonth; x++) {
            Button btn = dayButtons.get(x);
            btn.setEnabled(true);
            btn.setText(Integer.toString(x + 1));
            btn.setBackgroundColor(Color.WHITE);
            btn.setTextColor(Color.BLACK);
            btn.setVisibility(View.VISIBLE);
        }
    }

    private void setMonth(int Month){
        currMonth = Month + currMonth;
        if(currMonth == 0 && Month == -1){
            currMonth = 11;
            currYear -= 1;
        }
        if(currMonth / 12 > 0){
            currYear += Month;
            currMonth %= 2;
        }
        moveMonth(currYear, currMonth);
    }
}
