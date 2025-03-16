package com.example.innosynergy.model;

import java.time.LocalDateTime;

public class Benevolat {

    private int idBenevolat; // Utilisation d'un nom plus explicite
    private int idPartenaire;
    private String titre;
    private String description;
    private LocalDateTime dateBenevolat;
    private String statut; // Modification du nom de "status" à "statut" pour correspondre à la table
    private String imageName; // Utilisation d'un nom explicite pour l'image
    private String lieu; // Nouveau champ pour le lieu
public Benevolat() {
    }
    // Constructeur sans idBenevolat, si vous voulez qu'il soit auto-généré
    public Benevolat(int idPartenaire, String titre, String description, LocalDateTime dateBenevolat, String statut, String imageName, String lieu) {
        this.idPartenaire = idPartenaire;
        this.titre = titre;
        this.description = description;
        this.dateBenevolat = dateBenevolat;
        this.statut = statut;
        this.imageName = imageName;
        this.lieu = lieu; // Initialisation du champ lieu
    }

    // Constructeur avec idBenevolat pour la récupération d'un bénévole déjà existant
    public Benevolat(int idBenevolat, int idPartenaire, String titre, String description, LocalDateTime dateBenevolat, String statut, String imageName, String lieu) {
        this.idBenevolat = idBenevolat;
        this.idPartenaire = idPartenaire;
        this.titre = titre;
        this.description = description;
        this.dateBenevolat = dateBenevolat;
        this.statut = statut;
        this.imageName = imageName;
        this.lieu = lieu; // Initialisation du champ lieu
    }

    // Getters et setters
    public int getIdBenevolat() {
        return idBenevolat;
    }

    public void setIdBenevolat(int idBenevolat) {
        this.idBenevolat = idBenevolat;
    }

    public int getIdPartenaire() {
        return idPartenaire;
    }

    public void setIdPartenaire(int idPartenaire) {
        this.idPartenaire = idPartenaire;
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

    public LocalDateTime getDateBenevolat() {
        return dateBenevolat;
    }

    public void setDateBenevolat(LocalDateTime dateBenevolat) {
        this.dateBenevolat = dateBenevolat;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getLieu() {
        return lieu; // Getter pour le champ lieu
    }

    public void setLieu(String lieu) {
        this.lieu = lieu; // Setter pour le champ lieu
    }
}
