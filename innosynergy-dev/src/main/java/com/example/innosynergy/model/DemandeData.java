package com.example.innosynergy.model;

import java.time.LocalDateTime;

public class DemandeData {


    private int idDemande ;
    private int idClient;
    private int idPartenaire;
    private String typeAide;
    private String description;
    private double montantDemande;
    private LocalDateTime dateDemande;
    private String status;
    private String preuves;
    private String clientName;

    public String getClientName() {

        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }


    public int getIdDemande() {
        return idDemande;
    }

    public void setIdDemande(int idDemande) {
        this.idDemande = idDemande;
    }
    // Getters et setters
    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getIdPartenaire() {
        return idPartenaire;
    }

    public void setIdPartenaire(int idPartenaire) {
        this.idPartenaire = idPartenaire;
    }

    public String getTypeAide() {
        return typeAide;
    }

    public void setTypeAide(String typeAide) {
        this.typeAide = typeAide;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getMontantDemande() {
        return montantDemande;
    }

    public void setMontantDemande(double montantDemande) {
        this.montantDemande = montantDemande;
    }

    public LocalDateTime getDateDemande() {
        return dateDemande;
    }

    public void setDateDemande(LocalDateTime dateDemande) {
        this.dateDemande = dateDemande;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPreuves() {
        return preuves;
    }

    public void setPreuves(String preuves) {
        this.preuves = preuves;
    }




}