package com.example.innosynergy.controller;

import com.example.innosynergy.model.DemandeData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class DemandeAideController implements Initializable {

    @FXML
    private TableView<DemandeData> tableView;

    @FXML
    private TableColumn<DemandeData, String> demandeurColumn;

    @FXML
    private TableColumn<DemandeData, String> telephoneColumn;

    @FXML
    private TableColumn<DemandeData, String> emailColumn;

    @FXML
    private TableColumn<DemandeData, String> adresseColumn;

    @FXML
    private TableColumn<DemandeData, String> dateDemandeColumn;

    @FXML
    private TableColumn<DemandeData, String> preuveColumn;

    @FXML
    private TableColumn<DemandeData, Void> actionColumn;

    @FXML
    private TextField searchField;

    @FXML
    private Button addRequestButton;

    private ObservableList<DemandeData> demandeDataList;
    private FilteredList<DemandeData> filteredData;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        filteredData = new FilteredList<>(demandeDataList, p -> true);

        // Lier les colonnes aux données
        demandeurColumn.setCellValueFactory(new PropertyValueFactory<>("idClient"));
        telephoneColumn.setCellValueFactory(new PropertyValueFactory<>("idPartenaire"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("typeAide"));
        adresseColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        dateDemandeColumn.setCellValueFactory(new PropertyValueFactory<>("dateDemande"));
        preuveColumn.setCellValueFactory(new PropertyValueFactory<>("preuves"));

        // Définir une cellule personnalisée pour la colonne "Preuve"
        preuveColumn.setCellFactory(column -> new TableCell<DemandeData, String>() {
            private final Button importButton = new Button("choisir un fichier");
            private final ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/images/upload.png")));

            {
                imageView.setFitWidth(20);
                imageView.setFitHeight(20);
                importButton.setGraphic(imageView);
                importButton.setOnAction(event -> {
                    DemandeData data = getTableRow().getItem();
                    if (data != null) {
                        FileChooser fileChooser = new FileChooser();
                        fileChooser.setTitle("Sélectionner un fichier");
                        fileChooser.getExtensionFilters().addAll(
                                new FileChooser.ExtensionFilter("Tous les fichiers", ".")
                        );
                        File selectedFile = fileChooser.showOpenDialog(null);
                        if (selectedFile != null) {
                            data.setPreuves(selectedFile.getName()); // Mettre à jour le nom du fichier
                            tableView.refresh(); // Rafraîchir la table pour afficher le fichier sélectionné
                            System.out.println("Fichier sélectionné : " + selectedFile.getAbsolutePath());
                        }
                    }
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    setGraphic(importButton); // Afficher le bouton dans chaque ligne
                }
            }
        });

        // Ajouter des boutons d'action à la colonne "Actions"
        actionColumn.setCellFactory(param -> new TableCell<DemandeData, Void>() {
            private final Button consulterButton = new Button("Consulter");

            {
                consulterButton.setStyle("-fx-background-color: #003073; -fx-text-fill: white;");
                consulterButton.setOnAction(event -> {
                    DemandeData data = getTableRow().getItem();
                    if (data != null) {
                        showConsulterDemandesModal(data);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox buttons = new HBox(10, consulterButton);
                    buttons.setAlignment(Pos.CENTER);
                    setGraphic(buttons);
                }
            }
        });

        // Lier les données filtrées au tableau
        tableView.setItems(filteredData);

        // Ajouter un écouteur pour la recherche
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(demande -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return String.valueOf(demande.getIdClient()).toLowerCase().contains(lowerCaseFilter) ||
                        String.valueOf(demande.getIdPartenaire()).toLowerCase().contains(lowerCaseFilter) ||
                        demande.getTypeAide().toLowerCase().contains(lowerCaseFilter) ||
                        demande.getDescription().toLowerCase().contains(lowerCaseFilter) ||
                        demande.getDateDemande().toLowerCase().contains(lowerCaseFilter);
            });
        });

        // Action pour le bouton "Ajouter Demande"
        addRequestButton.setOnAction(event -> showAddRequestModal());

        // Rendre les colonnes responsive
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    // Méthode pour afficher une fenêtre modale avec les détails de la demande
    private void showConsulterDemandesModal(DemandeData data) {
        Stage modal = new Stage();
        modal.setTitle("Détails de la Demande");

        Label clientLabel = new Label("ID Client : " + data.getIdClient());
        Label partenaireLabel = new Label("ID Partenaire : " + data.getIdPartenaire());
        Label typeAideLabel = new Label("Type d'Aide : " + data.getTypeAide());
        Label descriptionLabel = new Label("Description : " + data.getDescription());
        Label montantDemandeLabel = new Label("Montant de la Demande : " + data.getMontantDemande());
        Label dateDemandeLabel = new Label("Date de Demande : " + data.getDateDemande());
        Label statusLabel = new Label("Status : " + data.getStatus());
        Label fichierSelectionneLabel = new Label("Preuves : " + data.getPreuves());

        Button downloadButton = new Button("Télécharger le dossier");
        Button importerButton = new Button("Importer un fichier");

        // Action pour importer un fichier
        importerButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Tous les fichiers", "."));
            File selectedFile = fileChooser.showOpenDialog(modal);

            if (selectedFile != null) {
                fichierSelectionneLabel.setText("Fichier sélectionné : " + selectedFile.getName());
            }
        });

        // Disposition des boutons
        HBox buttonLayout = new HBox(20, downloadButton, importerButton);
        buttonLayout.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Détails de la Demande");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        VBox modalLayout = new VBox(10, titleLabel, clientLabel, partenaireLabel, typeAideLabel, descriptionLabel, montantDemandeLabel, dateDemandeLabel, statusLabel, fichierSelectionneLabel, buttonLayout);
        modalLayout.setPadding(new Insets(20));
        modalLayout.setAlignment(Pos.CENTER_LEFT);

        Scene scene = new Scene(modalLayout, 500, 550);
        modal.setScene(scene);
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.show();

    }

    private void showAddRequestModal() {
        Stage modal = new Stage();
        modal.setTitle("Ajouter une Demande");

        // Message en haut du modal
        Label titleLabel = new Label("Ajouter une demande d'aide");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Champs de formulaire
        TextField idClientField = new TextField();
        idClientField.setPromptText("ID Client");

        TextField idPartenaireField = new TextField();
        idPartenaireField.setPromptText("ID Partenaire");

        TextField typeAideField = new TextField();
        typeAideField.setPromptText("Type d'Aide");

        TextField descriptionField = new TextField();
        descriptionField.setPromptText("Description");

        TextField montantDemandeField = new TextField();
        montantDemandeField.setPromptText("Montant de la Demande");

        DatePicker dateDemandePicker = new DatePicker();
        dateDemandePicker.setPromptText("Date de Demande");
        dateDemandePicker.setPrefWidth(400);

        TextField statusField = new TextField();
        statusField.setPromptText("Status");

        TextField preuvesField = new TextField();
        preuvesField.setPromptText("Preuves");

        // Bouton Enregistrer en vert
        Button saveButton = new Button("Enregistrer");
        saveButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;"); // Vert avec texte blanc
        saveButton.setOnAction(event -> {
            DemandeData newData = new DemandeData(
                    0, // Id Demande will be auto-generated
                    Integer.parseInt(idClientField.getText()),
                    Integer.parseInt(idPartenaireField.getText()),
                    typeAideField.getText(),
                    descriptionField.getText(),
                    Double.parseDouble(montantDemandeField.getText()),
                    dateDemandePicker.getValue().toString(),
                    statusField.getText(),
                    preuvesField.getText()
            );
            demandeDataList.add(newData);
            modal.close();
        });

        // Disposition des éléments
        VBox modalLayout = new VBox(10); // Espacement de 10 entre les éléments
        modalLayout.setPadding(new Insets(20)); // Padding général
        modalLayout.setAlignment(Pos.CENTER); // Centrer tous les éléments dans le VBox

        // Centrer le Label
        HBox titleBox = new HBox(titleLabel);
        titleBox.setAlignment(Pos.CENTER); // Centrer le Label dans un HBox

        // Centrer le bouton
        HBox buttonBox = new HBox(saveButton);
        buttonBox.setAlignment(Pos.CENTER); // Centrer le bouton dans un HBox

        // Ajouter les éléments au VBox
        modalLayout.getChildren().addAll(
                titleBox, // Label centré
                idClientField,
                idPartenaireField,
                typeAideField,
                descriptionField,
                montantDemandeField,
                dateDemandePicker,
                statusField,
                preuvesField,
                buttonBox // Bouton centré
        );

        Scene scene = new Scene(modalLayout, 400, 400);
        modal.setScene(scene);
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.show();
    }
}