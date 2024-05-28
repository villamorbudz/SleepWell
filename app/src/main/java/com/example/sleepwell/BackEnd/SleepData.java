package com.example.sleepwell.BackEnd;

public class SleepData {
    private String Year;
    private String Month;
    private String Day;
    private int SleepDurSec;

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getMonth() {
        return Month;
    }

    public void setMonth(String month) {
        Month = month;
    }

    public String getDay() {
        return Day;
    }

    public void setDay(String day) {
        Day = day;
    }

    public int getSleepDurSec() {
        return SleepDurSec;
    }

    public void setSleepDurSec(int sleepDurSec) {
        SleepDurSec = sleepDurSec;
    }

    public SleepData(String year, String month, String day, int sleepDurSec) {
        Year = year;
        Month = month;
        Day = day;
        SleepDurSec = sleepDurSec;
    }
}
