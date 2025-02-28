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

    // Méthode pour charger la vue du profil
    private void loadProfileView() {
        try {
            // Charger le fichier FXML pour la vue du profil
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MiraVia/ProfileView.fxml"));
            Parent profileView = loader.load();

            // Remplacer le contenu dans mainContainer avec la vue du profil
            mainContainer.getChildren().clear();  // Vider le conteneur actuel
            mainContainer.getChildren().add(profileView);  // Ajouter la nouvelle vue
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
