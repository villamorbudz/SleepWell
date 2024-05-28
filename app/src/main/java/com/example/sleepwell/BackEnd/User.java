package com.example.sleepwell.BackEnd;

public class User {
    String UserID;
    String UserName;
    String UserEmail;

    public User(String userID, String userName, String userEmail) {
        UserID = userID;
        UserName = userName;
        UserEmail = userEmail;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }
}
