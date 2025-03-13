package com.example.innosynergy.controller;

import com.example.innosynergy.model.Event;
import com.example.innosynergy.model.Event;
import com.example.innosynergy.services.EventService;
import com.example.innosynergy.services.EventService;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class EvenementController {

    @FXML
    private DatePicker departureDatePicker;

    @FXML
    private TextField searchField;

    @FXML
    private VBox cardContainer; // Conteneur pour les cartes d'événements

    private EventService evenementService = new EventService();

    @FXML
    public void initialize() {
        try {
            List<Event> evenements = evenementService.getEvenements();
            afficherEvenements(evenements);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void afficherEvenements(List<Event> evenements) {
        cardContainer.getChildren().clear(); // Effacer les cartes existantes

        for (Event evenement : evenements) {
            // Créer une carte pour chaque événement
            HBox eventCard = new HBox();
            eventCard.getStyleClass().add("event-card");

            // Ajouter une image (si disponible)
            ImageView imageView = new ImageView(new Image(evenement.getImageUrl()));
            imageView.setFitWidth(100);
            imageView.setFitHeight(100);
            eventCard.getChildren().add(imageView);

            // Ajouter les détails de l'événement
            VBox detailsBox = new VBox();
            detailsBox.getStyleClass().add("event-details");

            Label titleLabel = new Label(evenement.getTitre());
            titleLabel.getStyleClass().add("event-title");

            Label descriptionLabel = new Label(evenement.getDescription());
            descriptionLabel.getStyleClass().add("event-description");
            descriptionLabel.setWrapText(true);

            Label dateLabel = new Label(evenement.getDateEvenement().toString());
            dateLabel.getStyleClass().add("event-date");

            Label statusLabel = new Label(evenement.getStatus());
            statusLabel.getStyleClass().add("event-status");

            detailsBox.getChildren().addAll(titleLabel, descriptionLabel, dateLabel, statusLabel);
            eventCard.getChildren().add(detailsBox);

            // Ajouter la carte au conteneur
            cardContainer.getChildren().add(eventCard);
        }
    }

    @FXML
    private void handleSearch() {
        // Implémentez la logique de recherche ici
    }

    @FXML
    private void handleAjouter() {
        // Implémentez la logique d'ajout ici
    }

    @FXML
    private void handleModifier() {
        // Implémentez la logique de modification ici
    }

    @FXML
    private void handleSupprimer() {
        // Implémentez la logique de suppression ici
    }
}