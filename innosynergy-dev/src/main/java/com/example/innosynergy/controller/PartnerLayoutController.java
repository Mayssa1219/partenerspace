package com.example.innosynergy.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
<<<<<<< HEAD
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
=======
    private Button dashboardButton, helpRequestsButton, consultButton, donButton,
            eventsButton, benevolButton, settingsButton, messagesButton;
>>>>>>> aca7d87dfea214d8b632278f2808c593d67641d8

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private ImageView profileImageView, notificationsImageView, searchImageView, notificationIcon;

    private final Map<Button, String[]> buttonViewMappings = new HashMap<>();

    @FXML
    private void initialize() {
        // Associer les boutons aux vues correspondantes
        buttonViewMappings.put(dashboardButton, new String[]{"Tableau de bord", "/MiraVia/dashboard.fxml"});
        buttonViewMappings.put(helpRequestsButton, new String[]{"Demandes d'aide", "/MiraVia/DemandeAideView.fxml"});
        buttonViewMappings.put(consultButton, new String[]{"Consulter", null});
        buttonViewMappings.put(donButton, new String[]{"Don", "/MiraVia/DonView.fxml"});
        buttonViewMappings.put(eventsButton, new String[]{"Événements", "/MiraVia/EvenementView.fxml"});
        buttonViewMappings.put(benevolButton, new String[]{"Bénévolat", null});
        buttonViewMappings.put(settingsButton, new String[]{"Paramètres", "/MiraVia/SettingsView.fxml"});
        buttonViewMappings.put(messagesButton, new String[]{"Messagerie", "/MiraVia/MessagerieView.fxml"});

        // Attacher les événements à chaque bouton
        buttonViewMappings.forEach((button, viewInfo) -> {
            if (button != null) {
                button.setOnAction(event -> loadView(viewInfo[0], viewInfo[1]));
            } else {
                System.err.println("Bouton non injecté dans FXML !");
            }
        });

<<<<<<< HEAD
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

        // Gestionnaire d'événements pour le bouton "Événements"
        eventsButton.setOnAction(event -> {
            try {
                System.out.println("Loading EvenementView.fxml..."); // Debug message
                Node eventView = FXMLLoader.load(getClass().getResource("/MiraVia/EvenementView.fxml"));
                scrollPane.setContent(eventView);
                titleLabel.setText("Événements");
                System.out.println("EvenementView.fxml loaded successfully."); // Debug message
            } catch (IOException e) {
                e.printStackTrace();
                scrollPane.setContent(new Label("Erreur lors du chargement des événements."));
            }
        });
        // Charger le tableau de bord par défaut
        loadView("Tableau de bord", "/MiraVia/dashboard.fxml");
=======
        // Charger le tableau de bord par défaut
        loadView("Tableau de bord", "/MiraVia/dashboard.fxml");

        // Gestionnaire d'événements pour l'icône des notifications
        notificationIcon.setOnMouseClicked(event -> {
            try {
                showNotificationPopup(notificationIcon); // Afficher la popup de notification
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
>>>>>>> aca7d87dfea214d8b632278f2808c593d67641d8
    }

    private void showNotificationPopup(Node anchor) throws IOException {
        // Charger le fichier FXML pour la barre de notification
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MiraVia/NotificationBarView.fxml"));
        Parent notificationContent = loader.load();

        // Créer un Popup et ajouter le contenu de notification
        Popup popup = new Popup();
        popup.getContent().add(notificationContent);
        popup.setAutoHide(true);

        // Positionner la popup sous l'icône de notification
        popup.show(anchor.getScene().getWindow(), anchor.getScene().getWindow().getX() + anchor.getLayoutX(), anchor.getScene().getWindow().getY() + anchor.getLayoutY() + anchor.getBoundsInParent().getHeight());
    }

    private void loadView(String title, String fxmlPath) {
        titleLabel.setText(title);
        if (fxmlPath == null) {
            scrollPane.setContent(new Label("Contenu à venir pour " + title));
            return;
        }
        try {
            Node view = FXMLLoader.load(getClass().getResource(fxmlPath));
            scrollPane.setContent(view);
        } catch (IOException e) {
            e.printStackTrace();
            scrollPane.setContent(new Label("Erreur lors du chargement de " + title));
        }
    }

    @FXML
    private void toggleSidebarVisibility() {
        sidebarScrollPane.setVisible(!sidebarScrollPane.isVisible());
    }
<<<<<<< HEAD

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
                    content = FXMLLoader.load(getClass().getResource("/MiraVia/EvenementView.fxml"));
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
=======
>>>>>>> aca7d87dfea214d8b632278f2808c593d67641d8
}