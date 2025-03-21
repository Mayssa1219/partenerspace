package com.example.innosynergy.model;

public class User {
    private int idUtilisateur;
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private String telephone;
    private String dateInscription;
    private String typeUtilisateur;
    private String avatar;
    private String statutVerification;
    private String status;
    // Default constructor
    public User() {}

    // Constructor, getters, and setters
    public User(int idUtilisateur, String nom, String prenom, String email, String motDePasse, String telephone, String avatar, String statutVerification, String status, String typeUtilisateur) {
        this.idUtilisateur = idUtilisateur;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.telephone = telephone;
        this.avatar = avatar;
        this.statutVerification = statutVerification;
        this.status = status;
        this.typeUtilisateur = typeUtilisateur;
    }



    // Getters and setters
    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(String dateInscription) {
        this.dateInscription = dateInscription;
    }

    public String getTypeUtilisateur() {
        return typeUtilisateur;
    }

    public void setTypeUtilisateur(String typeUtilisateur) {
        this.typeUtilisateur = typeUtilisateur;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getStatutVerification() {
        return statutVerification;
    }

    public void setStatutVerification(String statutVerification) {
        this.statutVerification = statutVerification;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    @Override
    public String toString() {
        return "User{idUtilisateur=" + idUtilisateur + ", username='" + nom + "', email='" + email + "'}";
    }
}