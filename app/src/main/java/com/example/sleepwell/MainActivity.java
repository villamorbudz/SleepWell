package com.example.sleepwell;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.credentials.CredentialManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.sleepwell.BackEnd.DBHelpers;
import com.example.sleepwell.BackEnd.GlobalEntities;
import com.example.sleepwell.BackEnd.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

//import com.google.android.gms.auth.api.identity.BeginSignInRequest;
//import com.google.android.gms.auth.api.identity.GetSignInIntentRequest;
//import com.google.android.libraries.identity.googleid.GetGoogleIdOption;
//import com.google.firebase.Firebase;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

//        FirebaseDatabase db = FirebaseDatabase.getInstance();
//        DatabaseReference dbf = db.getReference("GilesGwaps");
//        dbf.setValue("TRUEE");

 //       findViewById(R.id.testButton).setOnClickListener(view -> {
            //Intent redirect = new Intent(MainActivity.this, HomeActivity.class);
            //startActivity(redirect);
            //System.out.println("REDIRECT TO SIGN UP");
            //finish();
//            loginUser("Hello","123");

//      });
    }

    public User loginUser(String username, String password) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = database.child("Users");
        final User[] res = {null};
        usersRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()) {
                    DataSnapshot dataSnapshot = task.getResult();
                    boolean found = false;
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        String userEmail = (String) userSnapshot.child("Username").getValue();
                        String userPassword = (String) userSnapshot.child("Password").getValue();
                        String userUsername = (String) userSnapshot.child("Username").getValue();

                        if (userUsername != null && userUsername.equals(username) && userPassword != null && userPassword.equals(password)) {
                            System.out.println("Login successful for user: " + userEmail);
                            found = true;
                            String index = String.valueOf(userSnapshot.getKey());
                            User u1 = new User(index, userUsername, userPassword);
                            GlobalEntities.currUser = u1;
                            Toast.makeText(getApplicationContext(),GlobalEntities.currUser.getUserID(), Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("Invalid username or password.");
                    }
                }else {

                }
            }
        });
        return res[0];
    }
}