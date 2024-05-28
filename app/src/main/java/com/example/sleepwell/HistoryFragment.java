package com.example.sleepwell;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<Button> baseButtons;
    ArrayList<Button> dayButtons;

    Button prevMonth;
    Button nextMonth;
    int currMonth;
    int currYear;
    int currDay;
    int dayOffset;

    // Year -> Month -> Day -> Status
    HashMap<Integer,HashMap<Integer, HashMap<Integer,Integer>>> yearHash;
    HashMap<Integer,Integer> finalDayHash;

    public HistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);

        baseButtons = new ArrayList<>();
        dayButtons = new ArrayList<>();

        for (int i = 1; i <= 42; i++) {
            int resId = getResources().getIdentifier("D" + i, "id", getActivity().getPackageName());
            baseButtons.add(rootView.findViewById(resId));
        }

        Calendar cd = Calendar.getInstance();
        yearHash = new HashMap<>();
        currYear = cd.get(Calendar.YEAR);
        currMonth = cd.get(Calendar.MONTH);
        currDay = cd.get(Calendar.DATE);
        moveMonth(currYear, currMonth);

        nextMonth = rootView.findViewById(R.id.NextMonthBtn);
        prevMonth = rootView.findViewById(R.id.PrevMonthBtn);

        nextMonth.setOnClickListener(v -> setMonth(+1));
        prevMonth.setOnClickListener(v -> setMonth(-1));

        return rootView;
    }

    private void moveMonth(int year, int month) {
        TextView myText = requireView().findViewById(R.id.MYtext);
        String[] monthsString = {
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        };
        myText.setText(monthsString[month] + " " + year);
        finalDayHash = getStatus(year, month);

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
            btn.setTextColor(Color.BLACK);
            btn.setVisibility(View.VISIBLE);

            int status = finalDayHash.getOrDefault(x + 1, 0);
            switch (status) {
                case 0: // None
                    btn.setBackgroundColor(Color.TRANSPARENT); // No background color
                    break;
                case 1: // Light
                    btn.setBackgroundColor(Color.parseColor("#80FF0000")); // Light-opacity red
                    btn.setTextColor(Color.WHITE);
                    break;
                case 2: // Medium
                    btn.setBackgroundColor(Color.RED); // Medium-opacity red
                    btn.setTextColor(Color.WHITE);
                    break;
                case 3: // Heavy
                    btn.setBackgroundColor(Color.parseColor("#FF8B0000")); // Red
                    btn.setTextColor(Color.WHITE);
                    break;
                default:
                    break;
            }
        }
    }

    private void setMonth(int Month){
        currMonth = Month + currMonth;
        if(currMonth == 0 && Month == -1){          //lazy fix
            currMonth = 11;
            currYear -= 1;
        }
        if(currMonth / 12 > 0){
            currYear += Month;
            currMonth %= 2;
        }
        moveMonth(currYear, currMonth);
    }

    private HashMap<Integer, Integer> getStatus(int year, int month) {
//        HashMap<Integer, HashMap<Integer, HashMap<Integer, Integer>>> yearHash = PeriodStatusManager.getYearHash();
        return yearHash
                .computeIfAbsent(year, k -> new HashMap<>())
                .computeIfAbsent(month, k -> new HashMap<>());
    }
}
