package com.example.innosynergy.model;

import java.util.Date;

public class PartenaireProfileModel  {
    private int idPartenaire;
    private String nomEntreprise;
    private String typeActivite;
    private String siteWeb;
    private String adresse;
    private String telephone;
    private String autresDocuments;
    private int etat;
    private Date dateExpiration;
    private String avatar;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public PartenaireProfileModel(int idPartenaire, String nomEntreprise, String typeActivite, String siteWeb, String adresse, String telephone, String autresDocuments, int etat, Date dateExpiration, String avatar) {
        this.idPartenaire = idPartenaire;
        this.nomEntreprise = nomEntreprise;
        this.typeActivite = typeActivite;
        this.siteWeb = siteWeb;
        this.adresse = adresse;
        this.telephone = telephone;
        this.autresDocuments = autresDocuments;
        this.etat = etat;
        this.dateExpiration = dateExpiration;
        this.avatar=avatar;
    }

    // Getters and setters for each field
    public int getIdPartenaire() {
        return idPartenaire;
    }

    public void setIdPartenaire(int idPartenaire) {
        this.idPartenaire = idPartenaire;
    }

    public String getNomEntreprise() {
        return nomEntreprise;
    }

    public void setNomEntreprise(String nomEntreprise) {
        this.nomEntreprise = nomEntreprise;
    }

    public String getTypeActivite() {
        return typeActivite;
    }

    public void setTypeActivite(String typeActivite) {
        this.typeActivite = typeActivite;
    }

    public String getSiteWeb() {
        return siteWeb;
    }

    public void setSiteWeb(String siteWeb) {
        this.siteWeb = siteWeb;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAutresDocuments() {
        return autresDocuments;
    }

    public void setAutresDocuments(String autresDocuments) {
        this.autresDocuments = autresDocuments;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public Date getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(Date dateExpiration) {
        this.dateExpiration = dateExpiration;
    }
}