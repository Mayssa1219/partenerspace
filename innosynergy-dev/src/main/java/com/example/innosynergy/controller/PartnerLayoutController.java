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
    private Button dashboardButton, helpRequestsButton, consultButton, donButton,
            eventsButton, benevolButton, settingsButton, messagesButton;

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
        buttonViewMappings.put(eventsButton, new String[]{"Événements", null});
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
}