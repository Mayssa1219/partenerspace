package com.example.innosynergy.model;

public class DemandeData {
    private int idDemande;
    private int idClient;
    private int idPartenaire;
    private String typeAide;
    private String description;
    private double montantDemande;
    private String dateDemande;
    private String status;
    private String preuves;

    public DemandeData(int idDemande, int idClient, int idPartenaire, String typeAide,
                       String description, double montantDemande, String dateDemande,
                       String status, String preuves) {
        this.idDemande = idDemande;
        this.idClient = idClient;
        this.idPartenaire = idPartenaire;
        this.typeAide = typeAide;
        this.description = description;
        this.montantDemande = montantDemande;
        this.dateDemande = dateDemande;
        this.status = status;
        this.preuves = preuves;
    }

    // Getters
    public int getIdDemande() { return idDemande; }
    public int getIdClient() { return idClient; }
    public int getIdPartenaire() { return idPartenaire; }
    public String getTypeAide() { return typeAide; }
    public String getDescription() { return description; }
    public double getMontantDemande() { return montantDemande; }
    public String getDateDemande() { return dateDemande; }
    public String getStatus() { return status; }
    public String getPreuves() { return preuves; }

    // Setters
    public void setIdDemande(int idDemande) { this.idDemande = idDemande; }
    public void setIdClient(int idClient) { this.idClient = idClient; }
    public void setIdPartenaire(int idPartenaire) { this.idPartenaire = idPartenaire; }
    public void setTypeAide(String typeAide) { this.typeAide = typeAide; }
    public void setDescription(String description) { this.description = description; }
    public void setMontantDemande(double montantDemande) { this.montantDemande = montantDemande; }
    public void setDateDemande(String dateDemande) { this.dateDemande = dateDemande; }
    public void setStatus(String status) { this.status = status; }
    public void setPreuves(String preuves) { this.preuves = preuves; }
}