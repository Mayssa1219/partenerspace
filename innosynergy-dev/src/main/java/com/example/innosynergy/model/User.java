package com.example.innosynergy.model;
public class User {
    private String id;
    private String partner;
    private String phone;
    private String email;
    private String address;
    private String responsible;
    private String status;
    private String action;

    public User(String id, String partner, String phone, String email, String address, String responsible, String status, String action) {
        this.id = id;
        this.partner = partner;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.responsible = responsible;
        this.status = status;
        this.action = action;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
