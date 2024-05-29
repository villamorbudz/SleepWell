package com.example.sleepwell;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sleepwell.BackEnd.GlobalEntities;
import com.example.sleepwell.BackEnd.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    Button loginButton;
    TextView signUpRedirect;
    EditText tfUserName;
    EditText tfPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        loginButton = findViewById(R.id.loginBtn);
        signUpRedirect = findViewById(R.id.signUpRedirect);
        tfUserName = findViewById(R.id.login_usernameField);
        tfPassword = findViewById(R.id.login_passwordField);
        loginButton.setOnClickListener(view -> {
            String Password = String.valueOf(tfPassword.getText());
            String Username = String.valueOf(tfUserName.getText());
            if(Password.equals("") || Username.equals("")) return;
            Intent loadingScreen = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(loadingScreen);
            loginUser(Username, Password, new Runnable() {
                @Override
                public void run() {
                    loadingScreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Intent redirect = new Intent(LoginActivity.this, OverviewActivity.class);
                    startActivity(redirect);
                    System.out.println("REDIRECT TO SIGN UP");
                    finish();
                }
            });
            System.out.println("LOGGING IN");
            finish();
        });

        signUpRedirect.setOnClickListener(view -> {
            Intent redirect = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(redirect);
            System.out.println("REDIRECT TO SIGN UP");
            finish();
        });
    }
    public void loginUser(String username, String password,Runnable CloseFeature) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = database.child("Users");
        usersRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
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
                        User u1= new User(index,userUsername,userPassword);
                        GlobalEntities.currUser = u1;
                        //Toast.makeText(getApplicationContext(),GlobalEntities.currUser.getUserName(), Toast.LENGTH_SHORT).show();
                        break;
                    }else{
                        return;
                    }
                }
                if (!found) {
                    System.out.println("Invalid username or password.");
                }
                CloseFeature.run();
            }
        });
    }
}