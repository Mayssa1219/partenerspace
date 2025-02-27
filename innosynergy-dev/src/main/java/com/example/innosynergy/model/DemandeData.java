package com.example.innosynergy.model;

import java.util.Date;

public class DemandeData {
    private String id;
    private String demandeur;
    private String telephone;
    private String email;
    private String adresse;
    private Date dateDemande;
    private String etat;
    private String description;
    private String fileUrl;

    public DemandeData(String id, String demandeur, String telephone, String email, String adresse, Date dateDemande, String etat, String description, String fileUrl) {
        this.id = id;
        this.demandeur = demandeur;
        this.telephone = telephone;
        this.email = email;
        this.adresse = adresse;
        this.dateDemande = dateDemande;
        this.etat = etat;
        this.description = description;
        this.fileUrl = fileUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDemandeur() {
        return demandeur;
    }

    public void setDemandeur(String demandeur) {
        this.demandeur = demandeur;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Date getDateDemande() {
        return dateDemande;
    }

    public void setDateDemande(Date dateDemande) {
        this.dateDemande = dateDemande;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}