package com.example.innosynergy.model;

public class Event {
    private String number;
    private String title;
    private String description;
    private String dateEvenement;
    private String place;
    private String partner;
    private String status;

    public Event(String number, String title, String description, String dateEvenement, String place, String partner, String status) {
        this.number = number;
        this.title = title;
        this.description = description;
        this.dateEvenement = dateEvenement;
        this.place = place;
        this.partner = partner;
        this.status = status;
    }

    public String getNumber() {
        return number;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDateEvenement() {
        return dateEvenement;
    }

    public String getPlace() {
        return place;
    }

    public String getPartner() {
        return partner;
    }

    public String getStatus() {
        return status;
    }


    public void setStatus(String bloqué) {
    }
}