package com.example.innosynergy.spaces.partner.controller;

import com.example.innosynergy.model.PartenaireData;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PartnerController {

    @FXML
    private BorderPane mainLayout;

    @FXML
    private HBox logoBox;

    @FXML
    private Label titleLabel;

    @FXML
    private VBox sidebar; // Référence à la barre latérale

    @FXML
    private ImageView logoImageView; // Référence au logo

    @FXML
    private void initialize() {
        // Initialisation des éléments de l'interface utilisateur
        titleLabel.setText("Tableau de bord");

        // Ajouter un gestionnaire d'événements pour le clic sur le logo
        logoImageView.setOnMouseClicked(event -> toggleSidebar());

        // Exemple de données pour un partenaire
        PartenaireData partenaire = new PartenaireData(
                "1",
                "Partenaire Exemple",
                "0123456789",
                "example@partner.com",
                "123 Rue Exemples",
                "Actif",
                "Entreprise Exemple",
                "2023-01-01",
                "2024-01-01",
                "Informatique",
                "www.example.com"
        );

        // Affichage des informations du partenaire (à adapter selon vos besoins)
        displayPartnerInfo(partenaire);
    }

    /**
     * Méthode pour basculer (toggle) la barre latérale.
     */
    private void toggleSidebar() {
        if (sidebar.isVisible()) {
            sidebar.setVisible(false); // Masquer la barre latérale
            sidebar.setManaged(false); // Ne pas réserver d'espace pour la barre latérale
        } else {
            sidebar.setVisible(true); // Afficher la barre latérale
            sidebar.setManaged(true); // Réserver de l'espace pour la barre latérale
        }
    }

    private void displayPartnerInfo(PartenaireData partenaire) {
        // Cette méthode peut être utilisée pour afficher les informations du partenaire dans l'interface utilisateur
        System.out.println("Nom du partenaire : " + partenaire.getNomPartenaire());
        System.out.println("Téléphone : " + partenaire.getTelephone());
        System.out.println("Email : " + partenaire.getEmail());
        System.out.println("Adresse : " + partenaire.getAdresse());
        System.out.println("État : " + partenaire.getEtat());
        System.out.println("Nom de l'entreprise : " + partenaire.getNomEntreprise());
        System.out.println("Date d'inscription : " + partenaire.getDateInscription());
        System.out.println("Date d'expiration : " + partenaire.getDateExpiration());
        System.out.println("Type d'activité : " + partenaire.getTypeActivite());
    }
}