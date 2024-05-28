package com.example.sleepwell;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class TrackActivity extends AppCompatActivity {

    ArrayList<Button> baseButtons;
    ArrayList<Button> dayButtons;
    Button prevMonth;
    Button nextMonth;
    int currMonth;
    int currYear;
    int currDay;
    int dayOffset;

    ToggleButton trackingbtn;
    long startTime;

    HashMap<Integer,HashMap<Integer, HashMap<Integer,Integer>>> yearHash;
    HashMap<Integer,Integer> finalDayHash;

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

        trackingbtn = findViewById(R.id.toggleTrackingBtn);
        trackingbtn.setOnClickListener(view ->{
            if(trackingbtn.isChecked()){
            }
        });

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
            dayButtons.add(baseButtons.get(startDayInWeek + x - 1));
        }

        for (int x = 0; x < daysInMonth; x++) {
            Button btn = dayButtons.get(x);
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