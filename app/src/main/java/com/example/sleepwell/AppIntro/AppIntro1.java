package com.example.sleepwell.AppIntro;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.sleepwell.LoginActivity;
import com.example.sleepwell.R;
import com.example.sleepwell.SignUpActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AppIntro1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AppIntro1 extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public AppIntro1() {
        // Required empty public constructor
    }

    public static AppIntro1 newInstance(String param1, String param2) {
        AppIntro1 fragment = new AppIntro1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_app_intro_1, container, false);

        Button nextButton = view.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(v -> {
            // Create a new instance of AppIntro2
            AppIntro2 appIntro2Fragment = new AppIntro2();

            // Replace the current fragment with AppIntro2
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainerView, appIntro2Fragment);
            transaction.addToBackStack(null);  // Add transaction to the back stack
            transaction.commit();
        });

        Button skipButton = view.findViewById(R.id.skipIntroBtn);
        skipButton.setOnClickListener(v -> {
            // Start LoginActivity
            startActivity(new Intent(getActivity(), LoginActivity.class));
            // Finish the parent activity (MainActivity)
            if (getActivity() != null) {
                getActivity().finish();
            }
        });

        return view;
    }
}
