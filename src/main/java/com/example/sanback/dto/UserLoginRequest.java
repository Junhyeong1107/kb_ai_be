package com.example.sanback.dto;

public class UserLoginRequest {
    private String userid;
    private String password;

    // Getters and setters
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
