package com.example.innosynergy.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Don {
    private int idDon;
    private int idClient;
    private Integer idPartenaire; // Nullable field
    private BigDecimal montant;
    private LocalDateTime dateDon;
    private String status;
    private String clientName; // Added field for client name

    // Getters and Setters
    public int getIdDon() {
        return idDon;
    }

    public void setIdDon(int idDon) {
        this.idDon = idDon;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public Integer getIdPartenaire() {
        return idPartenaire;
    }

    public void setIdPartenaire(Integer idPartenaire) {
        this.idPartenaire = idPartenaire;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public LocalDateTime getDateDon() {
        return dateDon;
    }

    public void setDateDon(LocalDateTime dateDon) {
        this.dateDon = dateDon;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    // Method to check if the donation is from a partner
    public boolean isFromPartner() {
        return idPartenaire != null;
    }
}