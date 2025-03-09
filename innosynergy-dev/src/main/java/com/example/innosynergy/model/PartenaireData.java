
package com.example.innosynergy.model;

public class PartenaireData {
    private String id;
    private String nomPartenaire;
    private String telephone;
    private String email;
    private String adresse;
    private String etat;
    private String nomEntreprise;
    private String dateInscription;
    private String dateExpiration;
    private String typeActivite;
    private String siteWeb;

    public PartenaireData(String id, String nomPartenaire, String telephone, String email, String adresse, String etat, String nomEntreprise, String dateInscription, String dateExpiration, String typeActivite) {
        this.id = id;
        this.nomPartenaire = nomPartenaire;
        this.telephone = telephone;
        this.email = email;
        this.adresse = adresse;
        this.etat = etat;
        this.nomEntreprise = nomEntreprise;
        this.dateInscription = dateInscription;
        this.dateExpiration = dateExpiration;
        this.typeActivite = typeActivite;
    }

    public PartenaireData(String id, String nomPartenaire, String telephone, String email, String adresse, String etat, String nomEntreprise, String dateInscription, String dateExpiration, String typeActivite, String siteWeb) {
        this.id = id;
        this.nomPartenaire = nomPartenaire;
        this.telephone = telephone;
        this.email = email;
        this.adresse = adresse;
        this.etat = etat;
        this.nomEntreprise = nomEntreprise;
        this.dateInscription = dateInscription;
        this.dateExpiration = dateExpiration;
        this.typeActivite = typeActivite;
        this.siteWeb = siteWeb;
    }

    // Getters and Setters
    public String getId() { return id; }
    public String getNomPartenaire() { return nomPartenaire; }
    public String getTelephone() { return telephone; }
    public String getEmail() { return email; }
    public String getAdresse() { return adresse; }
    public String getEtat() { return etat; }
    public String getNomEntreprise() { return nomEntreprise; }
    public String getDateInscription() { return dateInscription; }
    public String getDateExpiration() { return dateExpiration; }
    public String getTypeActivite() { return typeActivite; }

    public void setId(String id) { this.id = id; }
    public void setNomPartenaire(String nomPartenaire) { this.nomPartenaire = nomPartenaire; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    public void setEmail(String email) { this.email = email; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
    public void setEtat(String etat) { this.etat = etat; }
    public void setNomEntreprise(String nomEntreprise) { this.nomEntreprise = nomEntreprise; }
    public void setDateInscription(String dateInscription) { this.dateInscription = dateInscription; }
    public void setDateExpiration(String dateExpiration) { this.dateExpiration = dateExpiration; }
    public void setTypeActivite(String typeActivite) { this.typeActivite = typeActivite; }


}