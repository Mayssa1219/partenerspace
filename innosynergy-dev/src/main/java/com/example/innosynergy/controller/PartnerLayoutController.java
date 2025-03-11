package com.example.innosynergy.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Node;

import java.io.IOException;

public class PartnerLayoutController {

    @FXML
    private BorderPane mainLayout;

    @FXML
    private HBox logoBox;

    @FXML
    private Label titleLabel;

    @FXML
    private ScrollPane sidebarScrollPane;

    @FXML
    private VBox sidebar;

    @FXML
    private Button dashboardButton;

    @FXML
    private Button helpRequestsButton;

    @FXML
    private Button consultButton;

    @FXML
    private Button donButton;

    @FXML
    private Button eventsButton;

    @FXML
    private Button benevolButton;

    @FXML
    private Button settingsButton;

    @FXML
    private Button messagesButton;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private ImageView profileImageView;

    @FXML
    private ImageView notificationsImageView;

    @FXML
    private ImageView searchImageView;

    @FXML
    private void initialize() {
        // Gestionnaire d'événements pour le bouton "Paramètres"
        settingsButton.setOnAction(event -> {
            try {
                Node settingsView = FXMLLoader.load(getClass().getResource("/MiraVia/SettingsView.fxml"));
                scrollPane.setContent(settingsView);
                titleLabel.setText("Paramètres");
            } catch (IOException e) {
                e.printStackTrace();
                scrollPane.setContent(new Label("Erreur lors du chargement des paramètres."));
            }
        });

        // Gestionnaire d'événements pour le bouton "Messagerie"
        messagesButton.setOnAction(event -> {
            try {
                Node messengerView = FXMLLoader.load(getClass().getResource("/MiraVia/MessagerieView.fxml"));
                scrollPane.setContent(messengerView);
                titleLabel.setText("Messagerie");
            } catch (IOException e) {
                e.printStackTrace();
                scrollPane.setContent(new Label("Erreur lors du chargement de la messagerie."));
            }
        });

        // Gestionnaire d'événements pour le bouton "Demande d'aide"
        if (helpRequestsButton == null) {
            System.err.println("helpRequestsButton est null !");
        } else {
            System.out.println("helpRequestsButton est correctement injecté.");
        }

        // Gestionnaire d'événements pour le bouton "Demande d'aide"
        helpRequestsButton.setOnAction(event -> {
            try {
                Node demandeAideView = FXMLLoader.load(getClass().getResource("/MiraVia/DemandeAideView.fxml"));
                scrollPane.setContent(demandeAideView);
                titleLabel.setText("Demandes d'aide");
            } catch (IOException e) {
                e.printStackTrace();
                scrollPane.setContent(new Label("Erreur lors du chargement des demandes d'aide."));
            }
        });
    }

    @FXML
    private void toggleSidebarVisibility() {
        sidebarScrollPane.setVisible(!sidebarScrollPane.isVisible());
    }

    @FXML
    private void updateContentBasedOnButton() {
        Button sourceButton = (Button) mainLayout.getScene().getFocusOwner();
        if (sourceButton != null) {
            setTitleAndContent(sourceButton);
        }
    }

    private void setTitleAndContent(Button sourceButton) {
        String title = "";
        Node content = null;

        try {
            switch (sourceButton.getId()) {
                case "dashboardButton":
                    title = "Tableau de bord";
                    content = FXMLLoader.load(getClass().getResource("/MiraVia/dashboard.fxml"));
                    break;
                case "helpRequestButton":
                    title = "Demandes d'aide";
                    content = FXMLLoader.load(getClass().getResource("/MiraVia/DemandeAideView.fxml"));
                    break;
                case "consultButton":
                    title = "Consulter";
                    content = new Label("Contenu de Consulter");
                    break;
                case "donButton":
                    title = "Don";
                    content = new Label("Contenu de Don");
                    break;
                case "eventsButton":
                    title = "Événements";
                    content = new Label("Contenu des Événements");
                    break;
                case "benevolButton":
                    title = "Bénévolat";
                    content = new Label("Contenu de Bénévolat");
                    break;
                case "settingsButton":
                    title = "Paramètres";
                    content = FXMLLoader.load(getClass().getResource("/MiraVia/SettingsView.fxml"));
                    break;
                case "messagesButton":
                    title = "Messagerie";
                    content = FXMLLoader.load(getClass().getResource("/MiraVia/MessagerieView.fxml"));
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
            content = new Label("Erreur lors du chargement de la vue : " + e.getMessage());
        }

        titleLabel.setText(title);
        scrollPane.setContent(content);
}
}