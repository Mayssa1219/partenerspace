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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

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

    private ObservableList<DemandeData> demandeDataList;
    private FilteredList<DemandeData> filteredData;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialiser les données
        demandeDataList = FXCollections.observableArrayList(
                new DemandeData(
                        "John Doe",         // demandeur
                        "123456789",        // telephone
                        "john@example.com", // email
                        "123 Rue Exemple",  // adresse
                        "2023-10-01",       // dateDemande
                        "preuve1.pdf",      // preuve
                        "Description de la demande", // description
                        "http://example.com/preuve1.pdf" // fileUrl
                ),
                new DemandeData(
                        "Jane Doe",         // demandeur
                        "987654321",        // telephone
                        "jane@example.com", // email
                        "456 Rue Exemple",  // adresse
                        "2023-10-02",       // dateDemande
                        "preuve2.pdf",      // preuve
                        "Description de la demande 2", // description
                        "http://example.com/preuve2.pdf" // fileUrl
                )
        );

        filteredData = new FilteredList<>(demandeDataList, p -> true);

        // Lier les colonnes aux données
        demandeurColumn.setCellValueFactory(new PropertyValueFactory<>("demandeur"));
        telephoneColumn.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        adresseColumn.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        dateDemandeColumn.setCellValueFactory(new PropertyValueFactory<>("dateDemande"));
        preuveColumn.setCellValueFactory(new PropertyValueFactory<>("preuve"));

        // Aligner le contenu des colonnes au centre
        alignColumnContentToCenter(demandeurColumn);
        alignColumnContentToCenter(telephoneColumn);
        alignColumnContentToCenter(emailColumn);
        alignColumnContentToCenter(adresseColumn);
        alignColumnContentToCenter(dateDemandeColumn);
        alignColumnContentToCenter(preuveColumn);

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

        // Ajouter des écouteurs pour la recherche
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(demande -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return demande.getDemandeur().toLowerCase().contains(lowerCaseFilter) ||
                        demande.getTelephone().toLowerCase().contains(lowerCaseFilter) ||
                        demande.getEmail().toLowerCase().contains(lowerCaseFilter) ||
                        demande.getAdresse().toLowerCase().contains(lowerCaseFilter) ||
                        demande.getDateDemande().toLowerCase().contains(lowerCaseFilter);
            });
        });
    }

    // Méthode pour aligner le contenu d'une colonne au centre
    private <T> void alignColumnContentToCenter(TableColumn<DemandeData, T> column) {
        column.setCellFactory(tc -> new TableCell<DemandeData, T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item.toString());
                    setAlignment(Pos.CENTER); // Aligner le texte au centre
                }
            }
        });
    }

    // Méthode pour afficher une fenêtre modale avec les détails de la demande
    private void showConsulterDemandesModal(DemandeData data) {
        Stage modal = new Stage();
        modal.setTitle("Détails de la Demande");

        // Créer les labels et les champs
        Label clientLabel = new Label("Nom du Demandeur : " + data.getDemandeur());
        Label telephoneLabel = new Label("Téléphone : " + data.getTelephone());
        Label emailLabel = new Label("Email : " + data.getEmail());
        Label adresseLabel = new Label("Adresse : " + data.getAdresse());
        Label dateDemandeLabel = new Label("Date de Demande : " + data.getDateDemande());
        Label descriptionLabel = new Label("Description : " + data.getDescription());

        Button downloadButton = new Button("Télécharger le dossier");
        Button accepterButton = new Button("Accepter");
        Button refuserButton = new Button("Refuser");

        // Disposition des boutons
        HBox buttonLayout = new HBox(20, accepterButton, refuserButton, downloadButton);
        buttonLayout.setAlignment(Pos.CENTER);

        // Titre de la fenêtre modale
        Label titleLabel = new Label("Détails de la Demande");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // Disposition de la fenêtre modale
        VBox modalLayout = new VBox(10, titleLabel, clientLabel, telephoneLabel, emailLabel, adresseLabel, dateDemandeLabel, descriptionLabel, buttonLayout);
        modalLayout.setPadding(new Insets(20));
        modalLayout.setAlignment(Pos.CENTER_LEFT);

        // Configurer la scène de la fenêtre modale
        Scene scene = new Scene(modalLayout, 450, 500);
        modal.setScene(scene);
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.show();

}

}