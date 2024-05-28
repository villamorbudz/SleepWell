package com.example.sleepwell.BackEnd;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class DBHelpers {
    public static User CreateUser(String Email, String Password, String Username) {
        FirebaseDatabase fdb = FirebaseDatabase.getInstance();
        DatabaseReference dbf = fdb.getReference().child("Users");
        final User[] res = {null};
        dbf.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    int maxindex = (int) task.getResult().getChildrenCount();
                    DatabaseReference newpath = dbf.child(String.valueOf(maxindex));
                    newpath.child("Email").setValue(Email);
                    newpath.child("Password").setValue(Password);
                    newpath.child("Username").setValue(Username);
                    User currUser = new User(String.valueOf(maxindex), Username, Email);
                    res[0] = currUser;
                }
            }
        });
        return res[0];
    }
    /*
    public static User loginUser(String username, String password) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = database.child("Users");
        DataSnapshot dataSnapshot =  usersRef.get().getResult();

        for()

        try {
            // Synchronously wait for the task to complete
            Task<DataSnapshot> task = usersRef.get();
            DataSnapshot dataSnapshot = Tasks.await(task, 30, TimeUnit.SECONDS);

            for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                String userEmail = (String) userSnapshot.child("Email").getValue();
                String userPassword = (String) userSnapshot.child("Password").getValue();
                String userUsername = (String) userSnapshot.child("Username").getValue();

                if (userUsername != null && userUsername.equals(username) && userPassword != null && userPassword.equals(password)) {
                    String index = String.valueOf(userSnapshot.getKey());
                    return new User(index, userUsername, userEmail);
                }
            }
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            System.out.println("Error accessing the database: " + e.getMessage());
        }

        return null;
    }*/


    public static User loginUser(String username, String password) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = database.child("Users");
        final User[] res = {null};
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
                        break;
                    }
                }
                if (!found) {
                    System.out.println("Invalid username or password.");
                }
            }
        });
        return res[0];
    }
    public static boolean SendSleepData(SleepData sd){
        final boolean[] res = {false};
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = database.child("Users").child("0").child("SleepData");
        usersRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    int index =(int) task.getResult().getChildrenCount();
                    usersRef.child(sd.getYear()).child(sd.getMonth()).child(sd.getDay()).child(String.valueOf(index)).setValue(String.valueOf(sd.getSleepDurSec()));
                }
            }
        });
        res[0] = true; // get listener Here, your choice
        return res[0];
    }
}
