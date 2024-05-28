package com.example.sleepwell;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sleepwell.BackEnd.DBHelpers;

public class SignUpActivity extends AppCompatActivity {
    Button signUpButton;
    TextView loginRedirect;
    EditText tfUsername;
    EditText tfPassword;
    EditText tfEmail;
    EditText tfPasswordConfirm;
    TextView tvError;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        signUpButton = findViewById(R.id.createAccountBtn);
        loginRedirect = findViewById(R.id.loginRedirect);
        tfUsername = findViewById(R.id.signUp_usernameField);
        tfEmail = findViewById(R.id.signUp_emailField);
        tfPassword = findViewById(R.id.signUp_passwordField);
        tfPasswordConfirm = findViewById(R.id.signUp_confirmPasswordField);
        tvError = findViewById(R.id.signUpServerMessage);
        tvError.setText("");
        signUpButton.setOnClickListener(view -> {
            String Username = String.valueOf(tfUsername.getText());
            String Password = String.valueOf(tfPassword.getText());
            String PasswordC = String.valueOf(tfPasswordConfirm.getText());
            String Email = String.valueOf(tfEmail.getText());
            if(!PasswordC.equals(Password)){
                tvError.setText("Passwords are not the same");
                return;
            }
            if(Username.equals("") || Password.equals("") || PasswordC.equals("") || Email.equals("")){
                tvError.setText("Please Fill out All Forms");
                return;
            }
            DBHelpers.CreateUser(Email,Password,Username);
            Intent redirect = new Intent(SignUpActivity.this, OverviewActivity.class);
            startActivity(redirect);
            System.out.println("REDIRECT TO LOGIN");
            finish();
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