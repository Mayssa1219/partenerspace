package com.example.innosynergy.model;
import java.time.LocalDateTime;
public class Event {

    private int idEvenement;
    private String titre;
    private String description;
    private LocalDateTime dateEvenement;
    private String lieu;
    private Integer idPartenaire;
    private String status;

    // Getters and Setters
    public int getIdEvenement() {
        return idEvenement;
    }

    public void setIdEvenement(int idEvenement) {
        this.idEvenement = idEvenement;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateEvenement() {
        return dateEvenement;
    }

    public void setDateEvenement(LocalDateTime dateEvenement) {
        this.dateEvenement = dateEvenement;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public Integer getIdPartenaire() {
        return idPartenaire;
    }

    public void setIdPartenaire(Integer idPartenaire) {
        this.idPartenaire = idPartenaire;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}