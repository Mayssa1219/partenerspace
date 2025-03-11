package com.example.innosynergy.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DemandeData {
    private StringProperty demandeur;
    private StringProperty telephone;
    private StringProperty email;
    private StringProperty adresse;
    private StringProperty dateDemande;
    private StringProperty preuve; // Nouveau champ pour la preuve
    private StringProperty description; // Champ description
    private StringProperty fileUrl; // Champ pour l'URL du fichier

    // Constructeur
    public DemandeData(String demandeur, String telephone, String email, String adresse, String dateDemande, String preuve, String description, String fileUrl) {
        this.demandeur = new SimpleStringProperty(demandeur);
        this.telephone = new SimpleStringProperty(telephone);
        this.email = new SimpleStringProperty(email);
        this.adresse = new SimpleStringProperty(adresse);
        this.dateDemande = new SimpleStringProperty(dateDemande);
        this.preuve = new SimpleStringProperty(preuve);
        this.description = new SimpleStringProperty(description);
        this.fileUrl = new SimpleStringProperty(fileUrl);
    }

    // Getters et Setters pour les propriétés

    public String getDemandeur() {
        return demandeur.get();
    }

    public void setDemandeur(String demandeur) {
        this.demandeur.set(demandeur);
    }

    public StringProperty demandeurProperty() {
        return demandeur;
    }

    public String getTelephone() {
        return telephone.get();
    }

    public void setTelephone(String telephone) {
        this.telephone.set(telephone);
    }

    public StringProperty telephoneProperty() {
        return telephone;
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public StringProperty emailProperty() {
        return email;
    }

    public String getAdresse() {
        return adresse.get();
    }

    public void setAdresse(String adresse) {
        this.adresse.set(adresse);
    }

    public StringProperty adresseProperty() {
        return adresse;
    }

    public String getDateDemande() {
        return dateDemande.get();
    }

    public void setDateDemande(String dateDemande) {
        this.dateDemande.set(dateDemande);
    }

    public StringProperty dateDemandeProperty() {
        return dateDemande;
    }

    public String getPreuve() {
        return preuve.get();
    }

    public void setPreuve(String preuve) {
        this.preuve.set(preuve);
    }

    public StringProperty preuveProperty() {
        return preuve;
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public String getFileUrl() {
        return fileUrl.get();
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl.set(fileUrl);
    }

    public StringProperty fileUrlProperty() {
        return fileUrl;
}
}