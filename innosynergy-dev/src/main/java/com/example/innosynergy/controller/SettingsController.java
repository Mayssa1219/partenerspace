package com.example.innosynergy.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;  // Utilisation de VBox ici
import javafx.scene.control.Label;

import java.io.IOException;

public class SettingsController {

    @FXML
    private Label editProfileLabel;

    @FXML
    private VBox mainContainer;  // Utilisation de VBox ici

    @FXML
    public void initialize() {
        // Initialisation si nécessaire
    }

    // Méthode appelée lors du clic sur "Edit Profile"
    @FXML
    private void handleEditProfileClick() {
        System.out.println("Edit profile clicked");
        loadProfileView();
    }

    // Méthode appelée lors du clic sur "Security"
    @FXML
    private void handleSecurityClick() {
        System.out.println("Security clicked");
        loadSecurityView();
    }

    private void loadProfileView() {
        try {
            // Ensure the path to the FXML file is correct
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MiraVia/ProfilView.fxml"));
            Parent profileView = loader.load();

            // Replace the content in mainContainer with the profile view
            mainContainer.getChildren().clear();  // Clear the current container
            mainContainer.getChildren().add(profileView);  // Add the new view
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour charger la vue de sécurité
    private void loadSecurityView() {
        try {
            // Charger le fichier FXML pour la vue de sécurité
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MiraVia/SecurityView.fxml"));
            Parent securityView = loader.load();

            // Remplacer le contenu dans mainContainer avec la vue de sécurité
            mainContainer.getChildren().clear();  // Vider le conteneur actuel
            mainContainer.getChildren().add(securityView);  // Ajouter la nouvelle vue
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleNotificationsClick() {
        System.out.println("Notifications clicked");
        loadNotificationView();
    }

    private void loadNotificationView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MiraVia/NotificationView.fxml"));
            Parent notificationView = loader.load();

            // Remplacer le contenu dans mainContainer avec la vue des notifications
            mainContainer.getChildren().clear();
            mainContainer.getChildren().add(notificationView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
