package com.example.innosynergy.controller;

import com.example.innosynergy.dao.BenevolatDaoImpl;
import com.example.innosynergy.dao.DonDaoImpl;
import com.example.innosynergy.dao.EventDaoImpl;
import com.example.innosynergy.dao.NotificationDaoImpl;
import com.example.innosynergy.model.Benevolat;
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

import static com.example.innosynergy.config.ConnexionBD.getConnection;

public class BenevolatController {

    @FXML
    private DatePicker departureDatePicker;

    @FXML
    private TextField searchField;

    @FXML
    private VBox cardContainer; // Conteneur pour les cartes d'événements

    @FXML
    private Button addButton; // Bouton "Ajouter"

    private BenevolatDaoImpl benevolatDao;
    private File selectedFile;
    private NotificationDaoImpl notificationDao;
    @FXML
    public void initialize() {
        benevolatDao = new BenevolatDaoImpl(); // Assurez-vous de fournir la connexion correcte à votre base de données
        List<Benevolat> benevolats = benevolatDao.listBenevolat(); // Utilisez la méthode correcte pour récupérer les événements
        afficherBenevolat(benevolats);
    }

    private void afficherBenevolat(List<Benevolat> benevolats) {
        cardContainer.getChildren().clear(); // Effacer les cartes existantes

        for (Benevolat benevolat : benevolats) {
            // Créer une carte pour chaque bénévole
            HBox benevolatCard = new HBox(10); // Espacement de 10px entre l'image et le texte
            benevolatCard.getStyleClass().add("benevolat-card");

            // Associer l'objet Benevolat à la carte
            benevolatCard.setUserData(benevolat);

            // Ajouter une image (si disponible)
            String imageName = benevolat.getImageName();
            ImageView benevolatImageView = new ImageView();
            benevolatImageView.setFitWidth(100);
            benevolatImageView.setFitHeight(100);
            if (imageName != null && !imageName.isEmpty()) {
                File imageFile = new File("uploads/" + imageName);
                System.out.println("Chemin de l'image: " + imageFile.getAbsolutePath());
                // Ajout d'un message de journalisation
                if (imageFile.exists()) {
                    Image image = new Image(imageFile.toURI().toString());
                    benevolatImageView.setImage(image);
                    System.out.println("Image affichée pour le bénévolat: " + benevolat.getTitre());
                } else {
                    System.out.println("Le fichier image n'existe pas: " + imageFile.getAbsolutePath());
                }
            } else {
                System.out.println("Aucune image pour le bénévolat: " + benevolat.getTitre());
            }
            benevolatCard.getChildren().add(benevolatImageView);

            // Ajouter les détails du bénévolat
            VBox detailsBox = new VBox(5); // Espacement de 5px entre les éléments de texte
            detailsBox.getStyleClass().add("benevolat-details");

            // HBox pour aligner le titre et le MenuButton
            HBox titleBox = new HBox(10);
            titleBox.setAlignment(Pos.CENTER_LEFT);

            Label titleLabel = new Label(benevolat.getTitre());
            titleLabel.getStyleClass().add("benevolat-title");

            // MenuButton pour gérer le bénévolat
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

            Label descriptionLabel = new Label(benevolat.getDescription());
            descriptionLabel.getStyleClass().add("benevolat-description");
            descriptionLabel.setWrapText(true);

            Label dateLabel = new Label(benevolat.getDateBenevolat().toString());
            dateLabel.getStyleClass().add("benevolat-date");

            Label statutLabel = new Label(benevolat.getStatut());
            statutLabel.getStyleClass().add("benevolat-statut");

            detailsBox.getChildren().addAll(titleBox, descriptionLabel, dateLabel, statutLabel);
            benevolatCard.getChildren().add(detailsBox);

            // Ajouter la carte au conteneur
            cardContainer.getChildren().add(benevolatCard);
        }
    }


    @FXML
    private void handleSearch() {
        String keyword = searchField.getText();
        List<Benevolat> benevolats = benevolatDao.searchBenevolats(keyword);
        afficherBenevolat(benevolats);
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
        dialogStage.setTitle("Ajouter un bénévole");
        dialogStage.initModality(Modality.APPLICATION_MODAL); // Rendre la fenêtre modale

        // Créer les champs du formulaire
        TextField titreField = new TextField();
        titreField.setPromptText("Entrez le titre du bénévolat");

        TextArea descriptionField = new TextArea();
        descriptionField.setPromptText("Entrez la description du bénévolat");
        descriptionField.setWrapText(true);

        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("Sélectionnez la date du bénévolat");
        datePicker.setPrefWidth(400); // Largeur fixe pour le champ Date

        TextField lieuField = new TextField();
        lieuField.setPromptText("Entrez le lieu du bénévolat");

        TextField statutField = new TextField();
        statutField.setPromptText("Entrez le statut du bénévolat");

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

            // Créer un nouvel objet Benevolat
            Benevolat nouveauBenevolat = new Benevolat();
            nouveauBenevolat.setTitre(titre);
            nouveauBenevolat.setDescription(description);
            nouveauBenevolat.setDateBenevolat(date);
            nouveauBenevolat.setLieu(lieu);
            nouveauBenevolat.setStatut(statut);
            nouveauBenevolat.setIdPartenaire(idPartenaire);
            nouveauBenevolat.setImageName(imageName); // Ajouter le nom de l'image

            // Ajouter le bénévolat à la base de données
            benevolatDao.insertBenevolat(nouveauBenevolat);

            // Fermer la fenêtre modale
            dialogStage.close();
            // Ajouter une notification pour le don ajouté
            String message = "Un nouveau benevolat a été ajouté !";
            notificationDao.insertNotification(idPartenaire, message, "info");

            dialogStage.close();

            // Rafraîchir l'affichage des bénévolats
            List<Benevolat> benevolats = benevolatDao.listBenevolat();
            afficherBenevolat(benevolats);
        });

        // Gérer l'action du bouton "Annuler"
        annulerButton.setOnAction(event -> dialogStage.close());

        // Créer un HBox pour les boutons et les centrer
        HBox buttonBox = new HBox(10); // Espacement de 10px entre les boutons
        buttonBox.setAlignment(Pos.CENTER); // Centrer les boutons
        buttonBox.getChildren().addAll(annulerButton, ajouterButton);

        // Titre de la fenêtre modale
        Label titleLabel = new Label("Ajouter un bénévolat");
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
        // Récupérer la carte associée au bénévolat
        MenuItem menuItem = (MenuItem) event.getSource();
        ContextMenu contextMenu = menuItem.getParentPopup();
        MenuButton menuButton = (MenuButton) contextMenu.getOwnerNode();
        HBox benevolatCard = (HBox) menuButton.getParent().getParent().getParent();

        // Récupérer le bénévolat à partir de la carte
        Benevolat benevolat = getBenevolatFromCard(benevolatCard);

        // Vérifier si le bénévolat n'est pas null
        if (benevolat == null) {
            System.err.println("Erreur : le bénévolat est null.");
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
        dialogStage.setTitle("Modifier le bénévolat");
        dialogStage.initModality(Modality.APPLICATION_MODAL); // Rendre la fenêtre modale

        // Créer les champs du formulaire
        TextField titreField = new TextField(benevolat.getTitre());
        TextArea descriptionField = new TextArea(benevolat.getDescription());
        descriptionField.setWrapText(true);

        DatePicker datePicker = new DatePicker(benevolat.getDateBenevolat().toLocalDate());
        datePicker.setPrefWidth(400); // Largeur fixe pour le champ Date

       TextField lieuField = new TextField(benevolat.getLieu());
        TextField statutField = new TextField(benevolat.getStatut());


        // Champ pour l'importation d'image
        Button importImageButton = new Button("Importer une image");
        ImageView imageView = new ImageView();
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        if (benevolat.getImageName() != null && !benevolat.getImageName().isEmpty()) {
            File imageFile = new File("uploads/" + benevolat.getImageName());
            if (imageFile.exists()) {
                Image image = new Image(imageFile.toURI().toString());
                imageView.setImage(image);
                System.out.println("Image affichée pour le bénévolat à modifier: " + benevolat.getTitre());
            } else {
                System.out.println("Le fichier image à modifier n'existe pas: " + imageFile.getAbsolutePath());
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
                System.out.println("Image sélectionnée pour modification: " + selectedFile.getAbsolutePath());
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
            // Vérification des champs
            if (titreField.getText().isEmpty() || descriptionField.getText().isEmpty() || datePicker.getValue() == null || /*lieuField.getText().isEmpty() || */statutField.getText().isEmpty()) {
                showAlert("Erreur", "Tous les champs doivent être remplis.");
                return;
            }

            // Récupérer les valeurs des champs
            String titre = titreField.getText();
            String description = descriptionField.getText();
            LocalDateTime date = datePicker.getValue().atStartOfDay();
            String lieu = lieuField.getText();
            String statut = statutField.getText();
            Integer nbBenevoles = null;
            String imageName = benevolat.getImageName();

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

            // Modifier le bénévolat existant
            benevolat.setTitre(titre);
            benevolat.setDescription(description);
            benevolat.setDateBenevolat(date);
            //benevolat.setLieu(lieu);
            benevolat.setStatut(statut);
            benevolat.setIdPartenaire(idPartenaire); // Ajouter l'ID du partenaire
            benevolat.setImageName(imageName); // Ajouter le nom de l'image

            // Mettre à jour le bénévolat dans la base de données
            benevolatDao.updateBenevolat(benevolat);

            // Fermer la fenêtre modale
            dialogStage.close();

            // Rafraîchir l'affichage des bénévolats
            List<Benevolat> benevolats = benevolatDao.listBenevolat();
            afficherBenevolat(benevolats);
        });

        // Gérer l'action du bouton "Annuler"
        annulerButton.setOnAction(e -> dialogStage.close());

        // Créer un HBox pour les boutons et les centrer
        HBox buttonBox = new HBox(10); // Espacement de 10px entre les boutons
        buttonBox.setAlignment(Pos.CENTER); // Centrer les boutons
        buttonBox.getChildren().addAll(annulerButton, modifierButton);

        // Titre de la fenêtre modale
        Label titleLabel = new Label("Modifier le bénévolat");
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
               // new Label("Lieu :"), lieuField,
                new Label("Statut :"), statutField,
                new Label("Image :"), imageBox,
                buttonBox // Ajouter le HBox des boutons
        );

        // Créer une scène et afficher la fenêtre modale
        Scene scene = new Scene(vbox, 400, 700); // Ajuster la taille de la fenêtre
        scene.getStylesheets().add(getClass().getResource("/MiraVia/styles/style.css").toExternalForm());

        dialogStage.setScene(scene);
        dialogStage.showAndWait();
    }

    // Méthode pour récupérer le bénévolat à partir de la carte
    private Benevolat getBenevolatFromCard(HBox benevolatCard) {
        // Récupérer l'objet Benevolat associé à la carte via setUserData
        return (Benevolat) benevolatCard.getUserData();
    }



    @FXML
    private void handleSupprimer(ActionEvent event) {
        // Récupérer l'événement (ou bénévolat) associé à la carte
        MenuItem menuItem = (MenuItem) event.getSource();
        ContextMenu contextMenu = menuItem.getParentPopup();
        MenuButton menuButton = (MenuButton) contextMenu.getOwnerNode();
        HBox eventCard = (HBox) menuButton.getParent().getParent().getParent();

        // Récupérer le bénévole à partir de la carte
        Benevolat benevolat = getBenevolatFromCard(eventCard);

        // Vérifier si le bénévolat n'est pas null
        if (benevolat == null) {
            System.err.println("Erreur : le bénévolat est null.");
            return;
        }

        // Afficher un message de confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText(null);
        alert.setContentText("Êtes-vous sûr de vouloir supprimer ce bénévolat ?");

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
                // Logique pour supprimer le bénévolat
                System.out.println("Supprimer le bénévolat : " + benevolat.getIdBenevolat());

                // Supprimer le bénévolat de la base de données
                benevolatDao.deleteBenevolat(benevolat.getIdBenevolat());

                // Rafraîchir l'affichage des bénévolats
                List<Benevolat> benevolats = benevolatDao.listBenevolat();
                afficherBenevolat(benevolats);
            }
        });
    }



}