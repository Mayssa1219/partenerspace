package com.example.innosynergy.controller;

import com.example.innosynergy.dao.EventDaoImpl;
import com.example.innosynergy.model.Event;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.event.ActionEvent;  // Correct import

import java.time.LocalDateTime;
import java.util.List;

public class EvenementController {

    @FXML
    private DatePicker departureDatePicker;

    @FXML
    private TextField searchField;

    @FXML
    private VBox cardContainer; // Conteneur pour les cartes d'événements

    @FXML
    private Button addButton; // Bouton "Ajouter"

    private EventDaoImpl eventDao;

    @FXML
    public void initialize() {
        eventDao = new EventDaoImpl();
        List<Event> evenements = eventDao.listEvents();
        afficherEvenements(evenements);
    }

    private void afficherEvenements(List<Event> evenements) {
        cardContainer.getChildren().clear(); // Effacer les cartes existantes

        for (Event evenement : evenements) {
            // Créer une carte pour chaque événement
            HBox eventCard = new HBox(10); // Espacement de 10px entre l'image et le texte
            eventCard.getStyleClass().add("event-card");

            // Ajouter une image (si disponible)
            ImageView imageView = new ImageView(new Image("/images/thaîlande.jpg")); // Remplacez par l'image de l'événement
            imageView.setFitWidth(100);
            imageView.setFitHeight(100);
            eventCard.getChildren().add(imageView);

            // Ajouter les détails de l'événement
            VBox detailsBox = new VBox(5); // Espacement de 5px entre les éléments de texte
            detailsBox.getStyleClass().add("event-details");

            // HBox pour aligner le titre et le MenuButton
            HBox titleBox = new HBox(10);
            titleBox.setAlignment(Pos.CENTER_LEFT);

            Label titleLabel = new Label(evenement.getTitre());
            titleLabel.getStyleClass().add("event-title");

            // MenuButton pour gérer l'événement
            MenuButton menuButton = new MenuButton("Gérer");
            menuButton.getStyleClass().add("menu-button");

            MenuItem modifierItem = new MenuItem("Modifier");
            modifierItem.setOnAction(this::handleModifier);

            MenuItem supprimerItem = new MenuItem("Supprimer");
            supprimerItem.setOnAction(this::handleSupprimer);

            menuButton.getItems().addAll(modifierItem, supprimerItem);

            // Ajouter le titre et le MenuButton dans le HBox
            titleBox.getChildren().addAll(titleLabel, new Region(), menuButton); // Utiliser Region pour pousser le MenuButton à droite
            HBox.setHgrow(titleBox.getChildren().get(1), Priority.ALWAYS); // Faire en sorte que le Region prenne tout l'espace disponible

            Label descriptionLabel = new Label(evenement.getDescription());
            descriptionLabel.getStyleClass().add("event-description");
            descriptionLabel.setWrapText(true);

            Label dateLabel = new Label(evenement.getDateEvenement().toString());
            dateLabel.getStyleClass().add("event-date");

            Label lieuLabel = new Label(evenement.getLieu()); // Ajouter le lieu
            lieuLabel.getStyleClass().add("event-lieu");

            Label statusLabel = new Label(evenement.getStatus());
            statusLabel.getStyleClass().add("event-status");

            detailsBox.getChildren().addAll(titleBox, descriptionLabel, dateLabel, lieuLabel, statusLabel);
            eventCard.getChildren().add(detailsBox);

            // Ajouter la carte au conteneur
            cardContainer.getChildren().add(eventCard);
        }
    }

    @FXML
    private void handleSearch() {
        String keyword = searchField.getText();
        List<Event> evenements = eventDao.searchEvents(keyword);
        afficherEvenements(evenements);
    }

    @FXML
    private void handleAjouter() {
        // Créer une nouvelle fenêtre modale
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Ajouter un événement");
        dialogStage.initModality(Modality.APPLICATION_MODAL); // Rendre la fenêtre modale

        // Créer les champs du formulaire
        TextField titreField = new TextField();
        titreField.setPromptText("Entrez le titre de l'événement");

        TextArea descriptionField = new TextArea();
        descriptionField.setPromptText("Entrez la description de l'événement");
        descriptionField.setWrapText(true);

        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("Sélectionnez la date de l'événement");
        datePicker.setPrefWidth(400); // Largeur fixe pour le champ Date

        TextField lieuField = new TextField();
        lieuField.setPromptText("Entrez le lieu de l'événement");

        TextField statutField = new TextField();
        statutField.setPromptText("Entrez le statut de l'événement");

        // Boutons pour valider ou annuler
        Button ajouterButton = new Button("Ajouter");
        Button annulerButton = new Button("Annuler");

        // Appliquer les classes CSS
        ajouterButton.getStyleClass().add("ajouter-button");
        annulerButton.getStyleClass().add("annuler-button");

        // Gérer l'action du bouton "Ajouter"
        ajouterButton.setOnAction(event -> {
            // Récupérer les valeurs des champs
            String titre = titreField.getText();
            String description = descriptionField.getText();
            LocalDateTime date = datePicker.getValue().atStartOfDay();
            String lieu = lieuField.getText();
            String statut = statutField.getText();

            // Créer un nouvel événement
            Event nouvelEvenement = new Event();
            nouvelEvenement.setTitre(titre);
            nouvelEvenement.setDescription(description);
            nouvelEvenement.setDateEvenement(date);
            nouvelEvenement.setLieu(lieu); // Ajouter le lieu
            nouvelEvenement.setStatus(statut);

            // Ajouter l'événement à la base de données
            eventDao.insertEvent(nouvelEvenement);

            // Fermer la fenêtre modale
            dialogStage.close();

            // Rafraîchir l'affichage des événements
            List<Event> evenements = eventDao.listEvents();
            afficherEvenements(evenements);
        });

        // Gérer l'action du bouton "Annuler"
        annulerButton.setOnAction(event -> dialogStage.close());

        // Créer un HBox pour les boutons et les centrer
        HBox buttonBox = new HBox(10); // Espacement de 10px entre les boutons
        buttonBox.setAlignment(javafx.geometry.Pos.CENTER); // Centrer les boutons
        buttonBox.getChildren().addAll(annulerButton, ajouterButton);

        // Titre de la fenêtre modale
        Label titleLabel = new Label("Ajouter un événement");
        titleLabel.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: #013A71");

        // Centrer le titre dans un HBox
        HBox titleBox = new HBox();
        titleBox.setAlignment(javafx.geometry.Pos.CENTER); // Centrer le titre
        titleBox.getChildren().add(titleLabel);

        // Organiser les champs dans un VBox
        VBox vbox = new VBox(10);
        vbox.setPadding(new javafx.geometry.Insets(10));
        vbox.getChildren().addAll(
                titleBox, // Ajouter le HBox contenant le titre centré
                new Label("Titre :"), titreField,
                new Label("Description :"), descriptionField,
                new Label("Date :"), datePicker,
                new Label("Lieu :"), lieuField,
                new Label("Statut :"), statutField,
                buttonBox // Ajouter le HBox des boutons
        );

        // Créer une scène et afficher la fenêtre modale
        Scene scene = new Scene(vbox, 400, 450); // Ajuster la taille de la fenêtre
        scene.getStylesheets().add(getClass().getResource("/MiraVia/styles/style.css").toExternalForm());

        dialogStage.setScene(scene);
        dialogStage.showAndWait();
    }

    @FXML
    private void handleModifier(ActionEvent event) {
        // Récupérer l'événement associé à la carte
        MenuItem menuItem = (MenuItem) event.getSource();
        ContextMenu contextMenu = menuItem.getParentPopup();
        MenuButton menuButton = (MenuButton) contextMenu.getOwnerNode();
        HBox eventCard = (HBox) menuButton.getParent().getParent().getParent();

        // Logique pour modifier l'événement
        System.out.println("Modifier l'événement : " + eventCard);
    }

    @FXML
    private void handleSupprimer(ActionEvent event) {
        // Récupérer l'événement associé à la carte
        MenuItem menuItem = (MenuItem) event.getSource();
        ContextMenu contextMenu = menuItem.getParentPopup();
        MenuButton menuButton = (MenuButton) contextMenu.getOwnerNode();
        HBox eventCard = (HBox) menuButton.getParent().getParent().getParent();

        // Logique pour supprimer l'événement
        System.out.println("Supprimer l'événement : " + eventCard);
    }
}