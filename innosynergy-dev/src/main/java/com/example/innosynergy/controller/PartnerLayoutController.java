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
        // Set up initial state or bind properties if needed
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
                case "helpRequestsButton":
                    title = "Demandes d'aide";
                    content = new Label("Contenu des Demandes d'aide");
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
                    content = new Label("Contenu des Paramètres");
                    break;
                case "messagesButton":
                    title = "Messagerie";
                    content = new Label("Contenu de Messagerie");
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        titleLabel.setText(title);
        scrollPane.setContent(content);
    }
}