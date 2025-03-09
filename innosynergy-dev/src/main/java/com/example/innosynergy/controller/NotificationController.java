package com.example.innosynergy.controller;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class NotificationController {

    @FXML
    private CheckBox enableNotifications;

    @FXML
    private CheckBox emailNotifications;

    @FXML
    private CheckBox smsNotifications;

    @FXML
    private CheckBox pushNotifications;

    @FXML
    public void initialize() {
        // Initialisation si nécessaire, par exemple pour récupérer l'état initial des notifications
        System.out.println("Notification settings initialized.");
    }

    // Méthode appelée lors du changement de l'état de la case "Enable Notifications"
    @FXML
    private void handleNotificationToggle() {
        if (enableNotifications.isSelected()) {
            System.out.println("Notifications enabled.");
            // Activez les autres options si les notifications sont activées
            emailNotifications.setDisable(false);
            smsNotifications.setDisable(false);
            pushNotifications.setDisable(false);
        } else {
            System.out.println("Notifications disabled.");
            // Désactivez les autres options si les notifications sont désactivées
            emailNotifications.setDisable(true);
            smsNotifications.setDisable(true);
            pushNotifications.setDisable(true);
        }
    }

    // Méthode pour gérer le changement de l'option "Email Notifications"
    @FXML
    private void handleEmailNotificationToggle() {
        if (emailNotifications.isSelected()) {
            System.out.println("Email notifications enabled.");
        } else {
            System.out.println("Email notifications disabled.");
        }
    }

    // Méthode pour gérer le changement de l'option "SMS Notifications"
    @FXML
    private void handleSmsNotificationToggle() {
        if (smsNotifications.isSelected()) {
            System.out.println("SMS notifications enabled.");
        } else {
            System.out.println("SMS notifications disabled.");
        }
    }

    // Méthode pour gérer le changement de l'option "Push Notifications"
    @FXML
    private void handlePushNotificationToggle() {
        if (pushNotifications.isSelected()) {
            System.out.println("Push notifications enabled.");
        } else {
            System.out.println("Push notifications disabled.");
        }
    }

    // Méthode pour sauvegarder les changements
    @FXML
    private void handleSaveChanges() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Changes Saved");
        alert.setHeaderText("Your notification settings have been saved.");
        alert.showAndWait();
        System.out.println("Notification settings saved.");
    }
}