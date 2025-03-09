package com.example.innosynergy.model;

public class LoginModel {
    private String email;
    private String password;

    // Default constructor
    public LoginModel() {}

    // Constructor with parameters
    public LoginModel(String email, String password) {
        this.email = email;
        this.password = password;
    }


    // Getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}