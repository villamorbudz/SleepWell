package com.example.sleepwell;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
    Button signUpButton;
    TextView loginRedirect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        signUpButton = findViewById(R.id.createAccountBtn);
        loginRedirect = findViewById(R.id.loginRedirect);

        signUpButton.setOnClickListener(view -> {
            System.out.println("CREATING ACCOUNT...");
        });

        loginRedirect.setOnClickListener(view -> {
            Intent redirect = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(redirect);
            System.out.println("REDIRECT TO LOGIN");
            finish();
        });
    }
}