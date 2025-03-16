package com.example.innosynergy.controller;

import com.example.innosynergy.dao.DonDao;
import com.example.innosynergy.dao.DonDaoImpl;
import com.example.innosynergy.dao.NotificationDaoImpl;
import com.example.innosynergy.model.Don;
import com.example.innosynergy.utils.SessionManager;
import com.example.innosynergy.model.User;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DonController {

    @FXML
    private TextField searchField;
    @FXML
    private TableView<Don> tableView;
    @FXML
    private TableColumn<Don, Integer> indexColumn;
    @FXML
    private TableColumn<Don, String> clientNameColumn;
    @FXML
    private TableColumn<Don, BigDecimal> montantColumn;
    @FXML
    private TableColumn<Don, LocalDateTime> dateDonColumn;
   NotificationBarController notificationBarController;
    private DonDao donDao;
    private NotificationDaoImpl notificationDao;

    public DonController() {
        this.donDao = new DonDaoImpl();
        this.notificationDao = new NotificationDaoImpl(notificationBarController);
    }

    @FXML
    private void initialize() {
        //Initialisation du champ de recherche
        searchField.textProperty().addListener((observable, oldValue, newValue) -> handleSearch());

        indexColumn.setCellValueFactory(cellData -> {
            // This sets the index for each item in the table
            return new ReadOnlyObjectWrapper<>(tableView.getItems().indexOf(cellData.getValue()) + 1);
        });

        clientNameColumn.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        montantColumn.setCellValueFactory(new PropertyValueFactory<>("montant"));
        dateDonColumn.setCellValueFactory(new PropertyValueFactory<>("dateDon"));

        dateDonColumn.setCellFactory(column -> new TableCell<Don, LocalDateTime>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            @Override
            protected void updateItem(LocalDateTime date, boolean empty) {
                super.updateItem(date, empty);
                setText(empty || date == null ? "" : formatter.format(date));
            }
        });

        // Align columns to center
        alignColumnContentToCenter(indexColumn);
        alignColumnContentToCenter(clientNameColumn);
        alignColumnContentToCenter(montantColumn);
        alignColumnContentToCenter(dateDonColumn);

        loadDons();
    }

    private void loadDons() {
        String sessionId = SessionManager.getCurrentSessionId();
        User currentUser = SessionManager.getUser(sessionId);
        if (currentUser != null) {
            int partenaireId = currentUser.getIdUtilisateur();
            List<Don> dons = donDao.listDonsByPartenaire(partenaireId);
            dons.forEach(don -> don.setClientName(donDao.findClientNameById(don.getIdClient())));
            ObservableList<Don> observableDons = FXCollections.observableArrayList(dons);
            tableView.setItems(observableDons);
        }
    }

    @FXML
    private void handleSearch() {
        String keyword = searchField.getText();
        if (keyword != null && !keyword.isEmpty()) {
            List<Don> dons = donDao.searchDons(keyword);
            ObservableList<Don> observableDons = FXCollections.observableArrayList(dons);
            tableView.setItems(observableDons);
        } else {
            loadDons();
        }
    }

    @FXML
    private void handleAddDon() {
        // Créer une nouvelle fenêtre modale
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Ajouter un don");
        dialogStage.initModality(Modality.APPLICATION_MODAL); // Rendre la fenêtre modale

        // Créer les champs du formulaire
        TextField clientNameField = new TextField();
        clientNameField.setPromptText("Nom Client");

        TextField montantField = new TextField();
        montantField.setPromptText("Montant");

        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("Date de Don");
        datePicker.setPrefWidth(400); // Largeur fixe pour le champ Date

        // Boutons pour valider ou annuler
        Button ajouterButton = new Button("Ajouter");
        Button annulerButton = new Button("Annuler");

        // Appliquer les classes CSS
        ajouterButton.getStyleClass().add("ajouter-button");
        annulerButton.getStyleClass().add("annuler-button");

        // Gérer l'action du bouton "Ajouter"
        ajouterButton.setOnAction(e -> {
            String clientName = clientNameField.getText();
            BigDecimal montant = new BigDecimal(montantField.getText());
            LocalDateTime dateDon = datePicker.getValue().atStartOfDay();

            String sessionId = SessionManager.getCurrentSessionId();
            User currentUser = SessionManager.getUser(sessionId);
            if (currentUser != null) {
                int partenaireId = currentUser.getIdUtilisateur();
                int idClient = donDao.findClientIdByName(clientName);

                Don don = new Don();
                don.setIdClient(idClient);
                don.setClientName(clientName);
                don.setMontant(montant);
                don.setDateDon(dateDon);
                don.setIdPartenaire(partenaireId);

                donDao.insertDon(don);

                // Ajouter une notification pour le don ajouté
                String message = "Un nouveau don a été ajouté d'un montant de " + montant + ".";
                notificationDao.insertNotification(partenaireId, message, "info");

                loadDons();
                dialogStage.close();
            }
        });

        // Gérer l'action du bouton "Annuler"
        annulerButton.setOnAction(e -> dialogStage.close());

        // Créer un HBox pour les boutons et les centrer
        HBox buttonBox = new HBox(10); // Espacement de 10px entre les boutons
        buttonBox.setAlignment(Pos.CENTER); // Centrer les boutons
        buttonBox.getChildren().addAll(annulerButton, ajouterButton);

        // Titre de la fenêtre modale
        Label titleLabel = new Label("Ajouter un don");
        titleLabel.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: #013A71");

        // Centrer le titre dans un HBox
        HBox titleBox = new HBox();
        titleBox.setAlignment(Pos.CENTER); // Centrer le titre
        titleBox.getChildren().add(titleLabel);

        // Organiser les champs dans un VBox
        VBox vbox = new VBox(10);
        vbox.setPadding(new javafx.geometry.Insets(10));
        vbox.getChildren().addAll(
                titleBox, // Ajouter le HBox contenant le titre centré
                new Label("Nom Client :"), clientNameField,
                new Label("Montant :"), montantField,
                new Label("Date de Don :"), datePicker,
                buttonBox // Ajouter le HBox des boutons
        );

        // Créer une scène et afficher la fenêtre modale
        Scene scene = new Scene(vbox, 400, 450); // Ajuster la taille de la fenêtre
        scene.getStylesheets().add(getClass().getResource("/MiraVia/styles/style.css").toExternalForm());

        dialogStage.setScene(scene);
        dialogStage.showAndWait();
    }

    // Méthode pour aligner le contenu d'une colonne au centre
    private <T> void alignColumnContentToCenter(TableColumn<Don, T> column) {
        column.setCellFactory(tc -> new TableCell<Don, T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item.toString());
                    setGraphic(null);
                    setStyle("-fx-alignment: CENTER;"); // Aligner le texte au centre
                }
            }
        });
    }
}