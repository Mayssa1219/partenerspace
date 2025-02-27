package com.example.innosynergy.model;

import java.util.Date;

public class ClientData {
    private String id;
    private String client;
    private String telephone;
    private String email;
    private String adresse;
    private Date dateInscription;
    private String etat;

    public ClientData(String id, String client, String telephone, String email, String adresse, Date dateInscription, String etat) {
        this.id = id;
        this.client = client;
        this.telephone = telephone;
        this.email = email;
        this.adresse = adresse;
        this.dateInscription = dateInscription;
        this.etat = etat;
    }

    // Getters and Setters
    public String getId() { return id; }
    public String getClient() { return client; }
    public String getTelephone() { return telephone; }
    public String getEmail() { return email; }
    public String getAdresse() { return adresse; }
    public Date getDateInscription() { return dateInscription; }
    public String getEtat() { return etat; }

    public void setId(String id) { this.id = id; }
    public void setClient(String client) { this.client = client; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    public void setEmail(String email) { this.email = email; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
    public void setDateInscription(Date dateInscription) { this.dateInscription = dateInscription; }
    public void setEtat(String etat) { this.etat = etat; }
}