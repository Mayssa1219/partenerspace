package com.example.innosynergy.model;

public class Notification {
    private int id;
    private int utilisateurId;
    private String message;
    private String type;
    private String dateEnvoi;

    public Notification(int id, int utilisateurId, String message, String type, String dateEnvoi) {
        this.id = id;
        this.utilisateurId = utilisateurId;
        this.message = message;
        this.type = type;
        this.dateEnvoi = dateEnvoi;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(int utilisateurId) {
        this.utilisateurId = utilisateurId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDateEnvoi() {
        return dateEnvoi;
    }

    public void setDateEnvoi(String dateEnvoi) {
        this.dateEnvoi = dateEnvoi;
    }
}