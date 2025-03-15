package com.example.innosynergy.controller;

import com.example.innosynergy.dao.EventDaoImpl;
import com.example.innosynergy.model.Event;
import com.example.innosynergy.model.User;
import com.example.innosynergy.utils.SessionManager;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
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
    private File selectedFile;

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

            // Associer l'objet Event à la carte
            eventCard.setUserData(evenement);

            // Ajouter une image (si disponible)
            String imageName = evenement.getImageName();
            ImageView eventImageView = new ImageView();
            eventImageView.setFitWidth(100);
            eventImageView.setFitHeight(100);
            if (imageName != null && !imageName.isEmpty()) {
                File imageFile = new File("uploads/" + imageName);
                System.out.println("Chemin de l'image: " + imageFile.getAbsolutePath());
                // Ajout d'un message de journalisation
                if (imageFile.exists()) {
                    Image image = new Image(imageFile.toURI().toString());
                    eventImageView.setImage(image);
                    System.out.println("Image affichée pour l'événement: " + evenement.getTitre());
                } else {
                    System.out.println("Le fichier image n'existe pas: " + imageFile.getAbsolutePath());
                }
            } else {
                System.out.println("Aucune image pour l'événement: " + evenement.getTitre());
            }
            eventCard.getChildren().add(eventImageView);

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
            titleBox.getChildren().addAll(titleLabel, new Region(), menuButton);
            HBox.setHgrow(titleBox.getChildren().get(1), Priority.ALWAYS);

            Label descriptionLabel = new Label(evenement.getDescription());
            descriptionLabel.getStyleClass().add("event-description");
            descriptionLabel.setWrapText(true);

            Label dateLabel = new Label(evenement.getDateEvenement().toString());
            dateLabel.getStyleClass().add("event-date");

            Label lieuLabel = new Label(evenement.getLieu());
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

    private String saveImageToUploads(File sourceFile) throws IOException {
        String uploadsDir = "uploads";
        File uploadsDirectory = new File(uploadsDir);
        if (!uploadsDirectory.exists()) {
            uploadsDirectory.mkdirs(); // Crée le dossier s'il n'existe pas
        }

        // Générer un nom de fichier unique pour éviter les conflits
        String fileName = sourceFile.getName();
        File destFile = new File(uploadsDirectory, fileName);

        // Copier le fichier dans le dossier uploads
        Files.copy(sourceFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

        // Retourner uniquement le nom du fichier
        System.out.println("Image enregistrée: " + fileName); // Ajout d'un message de journalisation
        return fileName;
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

        // Champ pour l'importation d'image
        Button importImageButton = new Button("Importer une image");
        importImageButton.setPrefWidth(400); // Définir la largeur du bouton
        importImageButton.getStyleClass().add("import-button"); // Ajouter une classe CSS

        ImageView imageView = new ImageView();
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);

        importImageButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choisir une image");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.gif")
            );
            selectedFile = fileChooser.showOpenDialog(new Stage());
            if (selectedFile != null) {
                Image image = new Image(selectedFile.toURI().toString());
                imageView.setImage(image);
                System.out.println("Nom du fichier sélectionné: " + selectedFile.getName()); // Ajout d'un message de journalisation
            }
        });

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
            String imageName = null;

            if (selectedFile != null) {
                try {
                    imageName = saveImageToUploads(selectedFile);
                } catch (IOException e) {
                    e.printStackTrace();
                    showAlert("Erreur", "Échec de l'enregistrement de l'image.");
                    return;
                }
            }

            int idPartenaire = SessionManager.getUser(SessionManager.getCurrentSessionId()).getIdUtilisateur(); // Récupérer l'ID du partenaire depuis la session

            // Créer un nouvel événement
            Event nouvelEvenement = new Event();
            nouvelEvenement.setTitre(titre);
            nouvelEvenement.setDescription(description);
            nouvelEvenement.setDateEvenement(date);
            nouvelEvenement.setLieu(lieu);
            nouvelEvenement.setStatus(statut);
            nouvelEvenement.setIdPartenaire(idPartenaire);
            nouvelEvenement.setImageName(imageName); // Ajouter le nom de l'image

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
        buttonBox.setAlignment(Pos.CENTER); // Centrer les boutons
        buttonBox.getChildren().addAll(annulerButton, ajouterButton);

        // Titre de la fenêtre modale
        Label titleLabel = new Label("Ajouter un événement");
        titleLabel.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: #013A71");

        // Centrer le titre dans un HBox
        HBox titleBox = new HBox();
        titleBox.setAlignment(Pos.CENTER); // Centrer le titre
        titleBox.getChildren().add(titleLabel);

        // Organiser les champs dans un VBox
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(
                titleBox, // Ajouter le HBox contenant le titre centré
                new Label("Titre :"), titreField,
                new Label("Description :"), descriptionField,
                new Label("Date :"), datePicker,
                new Label("Lieu :"), lieuField,
                new Label("Statut :"), statutField,
                new Label("Image :"), importImageButton, imageView,
                buttonBox // Ajouter le HBox des boutons
        );

        // Créer une scène et afficher la fenêtre modale
        Scene scene = new Scene(vbox, 400, 600); // Ajuster la taille de la fenêtre
        scene.getStylesheets().add(getClass().getResource("/MiraVia/styles/style.css").toExternalForm());

        dialogStage.setScene(scene);
        dialogStage.showAndWait();
    }

    // Méthode pour afficher une alerte
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleModifier(ActionEvent event) {
        // Récupérer l'événement associé à la carte
        MenuItem menuItem = (MenuItem) event.getSource();
        ContextMenu contextMenu = menuItem.getParentPopup();
        MenuButton menuButton = (MenuButton) contextMenu.getOwnerNode();
        HBox eventCard = (HBox) menuButton.getParent().getParent().getParent();

        // Récupérer l'événement à partir de la carte
        Event evenement = getEventFromCard(eventCard);

        // Vérifier si l'événement n'est pas null
        if (evenement == null) {
            System.err.println("Erreur : l'événement est null.");
            return;
        }

        // Récupérer l'utilisateur courant depuis la session
        User currentUser = SessionManager.getUser(SessionManager.getCurrentSessionId());

        // Vérifier si l'utilisateur courant est null
        if (currentUser == null) {
            System.err.println("Erreur : l'utilisateur courant est null.");
            return;
        }

        // Récupérer l'ID du partenaire depuis l'utilisateur courant
        Integer idPartenaire = currentUser.getIdUtilisateur();

        // Vérifier si l'ID du partenaire est null
        if (idPartenaire == null) {
            System.err.println("Erreur : l'ID du partenaire est null.");
            return;
        }

        // Créer une nouvelle fenêtre modale
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Modifier l'événement");
        dialogStage.initModality(Modality.APPLICATION_MODAL); // Rendre la fenêtre modale

        // Créer les champs du formulaire
        TextField titreField = new TextField(evenement.getTitre());
        TextArea descriptionField = new TextArea(evenement.getDescription());
        descriptionField.setWrapText(true);

        DatePicker datePicker = new DatePicker(evenement.getDateEvenement().toLocalDate());
        datePicker.setPrefWidth(400); // Largeur fixe pour le champ Date

        TextField lieuField = new TextField(evenement.getLieu());
        TextField statutField = new TextField(evenement.getStatus());

        // Champ pour l'importation d'image
        Button importImageButton = new Button("Importer une image");
        ImageView imageView = new ImageView();
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        if (evenement.getImageName() != null && !evenement.getImageName().isEmpty()) {
            File imageFile = new File("uploads/" + evenement.getImageName());
            if (imageFile.exists()) {
                Image image = new Image(imageFile.toURI().toString());
                imageView.setImage(image);
                System.out.println("Image affichée pour l'événement à modifier: " + evenement.getTitre()); // Ajout d'un message de journalisation
            } else {
                System.out.println("Le fichier image à modifier n'existe pas: " + imageFile.getAbsolutePath()); // Ajout d'un message de journalisation
            }
        }

        ImageView editIcon = new ImageView(new Image(getClass().getResourceAsStream("/images/crayon.png")));

        editIcon.setFitWidth(20);
        editIcon.setFitHeight(20);
        editIcon.setOnMouseClicked(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choisir une image");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.gif")
            );
            selectedFile = fileChooser.showOpenDialog(dialogStage);
            if (selectedFile != null) {
                Image image = new Image(selectedFile.toURI().toString());
                imageView.setImage(image);
                System.out.println("Image sélectionnée pour modification: " + selectedFile.getAbsolutePath()); // Ajout d'un message de journalisation
            }
        });

        HBox imageBox = new HBox(10, imageView, editIcon);
        imageBox.setAlignment(Pos.CENTER_LEFT);

        // Boutons pour valider ou annuler
        Button modifierButton = new Button("Modifier");
        Button annulerButton = new Button("Annuler");

        // Appliquer les classes CSS
        modifierButton.getStyleClass().add("modifier-Button");
        annulerButton.getStyleClass().add("annuler-button");

        // Gérer l'action du bouton "Modifier"
        modifierButton.setOnAction(e -> {
            // Récupérer les valeurs des champs
            String titre = titreField.getText();
            String description = descriptionField.getText();
            LocalDateTime date = datePicker.getValue().atStartOfDay();
            String lieu = lieuField.getText();
            String statut = statutField.getText();
            String imageName = evenement.getImageName();

            if (selectedFile != null) {
                try {
                    imageName = saveImageToUploads(selectedFile);
                    System.out.println("Image enregistrée: " + imageName);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    showAlert("Erreur", "Échec de l'enregistrement de l'image.");
                    return;
                }
            }

            // Modifier l'événement existant
            evenement.setTitre(titre);
            evenement.setDescription(description);
            evenement.setDateEvenement(date);
            evenement.setLieu(lieu);
            evenement.setStatus(statut);
            evenement.setIdPartenaire(idPartenaire); // Ajouter l'ID du partenaire
            evenement.setImageName(imageName); // Ajouter le nom de l'image

            // Mettre à jour l'événement dans la base de données
            eventDao.updateEvent(evenement);

            // Fermer la fenêtre modale
            dialogStage.close();

            // Rafraîchir l'affichage des événements
            List<Event> evenements = eventDao.listEvents();
            afficherEvenements(evenements);
        });

        // Gérer l'action du bouton "Annuler"
        annulerButton.setOnAction(e -> dialogStage.close());

        // Créer un HBox pour les boutons et les centrer
        HBox buttonBox = new HBox(10); // Espacement de 10px entre les boutons
        buttonBox.setAlignment(Pos.CENTER); // Centrer les boutons
        buttonBox.getChildren().addAll(annulerButton, modifierButton);

        // Titre de la fenêtre modale
        Label titleLabel = new Label("Modifier l'événement");
        titleLabel.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: #013A71");

        // Centrer le titre dans un HBox
        HBox titleBox = new HBox();
        titleBox.setAlignment(Pos.CENTER); // Centrer le titre
        titleBox.getChildren().add(titleLabel);

        // Organiser les champs dans un VBox
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(
                titleBox, // Ajouter le HBox contenant le titre centré
                new Label("Titre :"), titreField,
                new Label("Description :"), descriptionField,
                new Label("Date :"), datePicker,
                new Label("Lieu :"), lieuField,
                new Label("Statut :"), statutField,
                new Label("Image :"), imageBox,
                buttonBox // Ajouter le HBox des boutons
        );

        // Créer une scène et afficher la fenêtre modale
        Scene scene = new Scene(vbox, 400, 600); // Ajuster la taille de la fenêtre
        scene.getStylesheets().add(getClass().getResource("/MiraVia/styles/style.css").toExternalForm());

        dialogStage.setScene(scene);
        dialogStage.showAndWait();
    }
    // Méthode pour récupérer l'événement à partir de la carte
    private Event getEventFromCard(HBox eventCard) {
        // Récupérer l'objet Event associé à la carte via setUserData
        return (Event) eventCard.getUserData();
    }

    @FXML
    private void handleSupprimer(ActionEvent event) {
        // Récupérer l'événement associé à la carte
        MenuItem menuItem = (MenuItem) event.getSource();
        ContextMenu contextMenu = menuItem.getParentPopup();
        MenuButton menuButton = (MenuButton) contextMenu.getOwnerNode();
        HBox eventCard = (HBox) menuButton.getParent().getParent().getParent();

        // Récupérer l'événement à partir de la carte
        Event evenement = getEventFromCard(eventCard);

        // Vérifier si l'événement n'est pas null
        if (evenement == null) {
            System.err.println("Erreur : l'événement est null.");
            return;
        }

        // Afficher un message de confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText(null);
        alert.setContentText("Êtes-vous sûr de vouloir supprimer cet événement ?");

        // Ajouter des boutons Oui et Non
        ButtonType buttonTypeOui = new ButtonType("Oui", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeNon = new ButtonType("Non", ButtonBar.ButtonData.NO);

        alert.getButtonTypes().setAll(buttonTypeOui, buttonTypeNon);

        // Customiser le DialogPane pour centrer les boutons et ajouter des styles
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getButtonTypes().forEach(buttonType -> {
            Node button = dialogPane.lookupButton(buttonType);
            ((Button) button).setMinWidth(75); // Largeur minimale pour chaque bouton

            // Ajouter des classes CSS pour les boutons "Oui" et "Non"
            if (buttonType == buttonTypeOui) {
                button.getStyleClass().add("button-oui");
            } else if (buttonType == buttonTypeNon) {
                button.getStyleClass().add("button-non");
            }
        });

        // Centrer les boutons dans la barre de boutons
        ButtonBar buttonBar = (ButtonBar) dialogPane.lookup(".button-bar");
        buttonBar.setButtonOrder(ButtonBar.BUTTON_ORDER_NONE); // Désactiver l'ordre par défaut des boutons
        buttonBar.setStyle("-fx-alignment: center;"); // Centrer les boutons

        // Attendre la réponse de l'utilisateur
        alert.showAndWait().ifPresent(response -> {
            if (response == buttonTypeOui) {
                // Logique pour supprimer l'événement
                System.out.println("Supprimer l'événement : " + evenement.getIdEvenement());

                // Supprimer l'événement de la base de données
                eventDao.deleteEvent(evenement.getIdEvenement());

                // Rafraîchir l'affichage des événements
                List<Event> evenements = eventDao.listEvents();
                afficherEvenements(evenements);
            }
        });
    }
}