package com.example.innosynergy.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class SecurityController {

    // Méthode pour gérer le clic sur "Change Password"
    @FXML
    private void handleChangePasswordClick(ActionEvent event) {
        // Afficher une alerte ou ouvrir un formulaire pour changer le mot de passe
        showAlert("Change Password", "This feature allows you to change your password.");
    }

    // Méthode pour gérer le clic sur "Two-factor Authentication"
    @FXML
    private void handleTwoFactorAuthClick(ActionEvent event) {
        // Afficher une alerte ou ouvrir un formulaire pour activer/désactiver l'authentification à deux facteurs
        showAlert("Two-factor Authentication", "Enable or disable two-factor authentication for extra security.");
    }

    // Méthode pour gérer le clic sur "View Activity Log"
    @FXML
    private void handleActivityLogClick(ActionEvent event) {
        // Afficher un log des activités de sécurité récentes
        showAlert("Activity Log", "View a log of recent security-related activities on your account.");
    }

    // Méthode pour gérer le clic sur "Security Alerts"
    @FXML
    private void handleSecurityAlertClick(ActionEvent event) {
        // Afficher une alerte de sécurité si des actions suspectes sont détectées
        showAlert("Security Alerts", "View recent security alerts regarding your account.");
    }

    // Méthode pour gérer le clic sur "Manage Devices"
    @FXML
    private void handleManageDevicesClick(ActionEvent event) {
        // Afficher une alerte ou ouvrir un formulaire pour gérer les appareils connectés
        showAlert("Manage Devices", "Manage and review the devices that have accessed your account.");
    }

    // Méthode pour gérer le clic sur "Privacy Settings"
    @FXML
    private void handlePrivacySettingsClick(ActionEvent event) {
        // Afficher une alerte ou ouvrir un formulaire pour configurer les paramètres de confidentialité
        showAlert("Privacy Settings", "Adjust your privacy settings for enhanced control over your data.");
    }

    // Méthode d'alerte générique
    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
