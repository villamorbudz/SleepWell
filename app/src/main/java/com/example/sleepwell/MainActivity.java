package com.example.sleepwell;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.credentials.CredentialManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.GetSignInIntentRequest;
import com.google.android.libraries.identity.googleid.GetGoogleIdOption;
import com.google.firebase.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        Toast.makeText(getApplicationContext(),"Hello World",Toast.LENGTH_SHORT).show();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dbf = db.getReference("GilesGwaps");
        dbf.setValue("TRUEE");
    }
}