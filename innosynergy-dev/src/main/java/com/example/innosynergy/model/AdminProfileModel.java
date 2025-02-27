package com.example.innosynergy.model;

import java.time.LocalDate;

public class AdminProfileModel {
    private String name;
    private LocalDate birth;
    private String phone;
    private String location;
    private String email;
    private String password;

    // Constructor
    public AdminProfileModel(String name, LocalDate birth, String phone, String location, String email, String password) {
        this.name = name;
        this.birth = birth;
        this.phone = phone;
        this.location = location;
        this.email = email;
        this.password = password;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

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

    // Method to display the profile as a string (optional)
    @Override
    public String toString() {
        return "AdminProfileModel{" +
                "name='" + name + '\'' +
                ", birth=" + birth +
                ", phone='" + phone + '\'' +
                ", location='" + location + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}