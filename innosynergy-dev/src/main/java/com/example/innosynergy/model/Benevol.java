package com.example.innosynergy.model;

import java.time.LocalDateTime;

public class Benevol {
    private int id;
    private int idPartenaire;
    private String titre;
    private String description;
    private LocalDateTime dateBenevolat;
    private String status;

    public Benevol(int id, int idPartenaire, String titre, String description, LocalDateTime dateBenevolat, String status) {
        this.id = id;
        this.idPartenaire = idPartenaire;
        this.titre = titre;
        this.description = description;
        this.dateBenevolat = dateBenevolat;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public int getIdPartenaire() {
        return idPartenaire;
    }

    public String getTitre() {
        return titre;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDateBenevolat() {
        return dateBenevolat;
    }

    public String getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdPartenaire(int idPartenaire) {
        this.idPartenaire = idPartenaire;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDateBenevolat(LocalDateTime dateBenevolat) {
        this.dateBenevolat = dateBenevolat;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}