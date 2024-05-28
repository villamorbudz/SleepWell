package com.example.sleepwell;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    Button loginButton;
    TextView signUpRedirect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        loginButton = findViewById(R.id.loginBtn);
        signUpRedirect = findViewById(R.id.signUpRedirect);

        loginButton.setOnClickListener(view -> {
            System.out.println("LOGGING IN");
        });

        signUpRedirect.setOnClickListener(view -> {
            Intent redirect = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(redirect);
            System.out.println("REDIRECT TO SIGN UP");
            finish();
        });
    }
}