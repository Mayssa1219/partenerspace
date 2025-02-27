package com.example.innosynergy.view;

import com.example.innosynergy.controller.DemandeController;
import com.example.innosynergy.model.DemandeData;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Demande extends StackPane {
    private DemandeController demandeController;
    private TableView<DemandeData> tableView;

    public Demande() {
        demandeController = new DemandeController();

        // Create a FilteredList from the original data list
        FilteredList<DemandeData> filteredData = new FilteredList<>(demandeController.getDemandeDataList(), p -> true);

        // TableView
        tableView = new TableView<>();
        tableView.setItems(filteredData);
        tableView.setPrefWidth(1230);  // Set the preferred width of the table
        tableView.setPrefHeight(800); // Set the preferred height of the table

        // Columns
        TableColumn<DemandeData, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idColumn.setPrefWidth(120);

        TableColumn<DemandeData, String> demandeurColumn = new TableColumn<>("Demandeur");
        demandeurColumn.setCellValueFactory(new PropertyValueFactory<>("demandeur"));
        demandeurColumn.setPrefWidth(180);

        TableColumn<DemandeData, String> telephoneColumn = new TableColumn<>("Téléphone");
        telephoneColumn.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        telephoneColumn.setPrefWidth(180);

        TableColumn<DemandeData, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailColumn.setPrefWidth(200);

        TableColumn<DemandeData, String> adresseColumn = new TableColumn<>("Adresse");
        adresseColumn.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        adresseColumn.setPrefWidth(150);

        TableColumn<DemandeData, Date> dateDemandeColumn = new TableColumn<>("Date de Demande");
        dateDemandeColumn.setCellValueFactory(new PropertyValueFactory<>("dateDemande"));
        dateDemandeColumn.setPrefWidth(180);
        dateDemandeColumn.setCellFactory(column -> {
            return new TableCell<DemandeData, Date>() {
                private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(format.format(item));
                    }
                }
            };
        });

        TableColumn<DemandeData, String> etatColumn = new TableColumn<>("État");
        etatColumn.setCellValueFactory(new PropertyValueFactory<>("etat"));
        etatColumn.setPrefWidth(120);

        // Actions Column
        TableColumn<DemandeData, Void> actionColumn = new TableColumn<>("Actions");
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
        actionColumn.setPrefWidth(100);

        tableView.getColumns().addAll(idColumn, demandeurColumn, telephoneColumn, emailColumn, adresseColumn, dateDemandeColumn, etatColumn, actionColumn);

        // Row styling for alternating row colors
        tableView.setRowFactory(tv -> {
            TableRow<DemandeData> row = new TableRow<>();
            row.itemProperty().addListener((obs, oldItem, newItem) -> {
                if (newItem == null) {
                    row.setStyle("");
                } else {
                    if (row.getIndex() % 2 == 0) {
                        row.setStyle("-fx-background-color: white; -fx-text-fill: black;");
                    } else {
                        row.setStyle("-fx-background-color: #e7efff; -fx-text-fill: black;");
                    }
                }
            });

            row.selectedProperty().addListener((observable, wasSelected, isNowSelected) -> {
                if (isNowSelected) {
                    row.setStyle("-fx-background-color: #bdd4f3; -fx-text-fill: black;");
                } else {
                    row.setStyle("");
                }
            });

            return row;
        });

        // Search Bar
        HBox topPanel = new HBox();
        topPanel.setSpacing(10);
        topPanel.setPadding(new Insets(10));
        topPanel.setAlignment(Pos.CENTER_LEFT);

        // Search TextField
        TextField searchField = new TextField();
        searchField.setPromptText("Rechercher Demande...");
        searchField.setPrefWidth(250);

        // Filter ComboBox for 'etat'
        ComboBox<String> etatComboBox = new ComboBox<>();
        etatComboBox.getItems().addAll("Tous", "acceptée", "refusée", "en attente");
        etatComboBox.setValue("Tous");

        // Filter the data as the user types in the search field
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
                        demande.getDateDemande().toString().toLowerCase().contains(lowerCaseFilter) ||
                        demande.getEtat().toLowerCase().contains(lowerCaseFilter);
            });
        });

        etatComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(demande -> {
                if (newValue == null || newValue.equals("Tous")) {
                    return true;
                }
                return demande.getEtat().equalsIgnoreCase(newValue);
            });
        });

        HBox.setHgrow(searchField, Priority.ALWAYS);

        topPanel.getChildren().addAll(searchField, etatComboBox);

        // TableView wrapper with padding on left and right
        HBox tableWrapper = new HBox(tableView);
        tableWrapper.setPadding(new Insets(0, 5, 0, 5));  // Padding of 5px on left and right
        tableWrapper.setAlignment(Pos.CENTER);  // Center the table if necessary

        // Create a Label for the title
        Label titleLabel = new Label("Liste des Demandes Partenaires");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-alignment: center; -fx-padding: 10px;");

        // Main layout
        VBox mainLayout = new VBox();
        mainLayout.setStyle("-fx-background-color: white;");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-alignment: center; -fx-text-fill: #003073; -fx-padding: 10px; -fx-font-family: Arial;");
        mainLayout.setAlignment(Pos.TOP_CENTER); // This centers the label horizontally in the VBox

        Region tableSpacer = new Region();
        tableSpacer.setMinHeight(15);
        VBox.setVgrow(tableSpacer, Priority.NEVER);

        mainLayout.getChildren().addAll(titleLabel, topPanel, tableSpacer, tableWrapper);

        this.getChildren().add(mainLayout);
    }

    private void showConsulterDemandesModal(DemandeData data) {
        Stage modal = new Stage();
        modal.setTitle("Détails de la Demande");

        // Create the labels and input fields
        Label clientLabel = new Label("Nom du Demandeur : " + data.getDemandeur());
        clientLabel.setStyle("-fx-padding: 10px; -fx-background-radius: 5px; -fx-font-size: 14px; -fx-font-family: Arial; -fx-font-weight: bold;");

        Label telephoneLabel = new Label("Téléphone : " + data.getTelephone());
        telephoneLabel.setStyle("-fx-padding: 10px; -fx-background-radius: 5px; -fx-font-size: 14px; -fx-font-family: Arial; -fx-font-weight: bold;");

        Label emailLabel = new Label("Email : " + data.getEmail());
        emailLabel.setStyle("-fx-padding: 10px; -fx-background-radius: 5px; -fx-font-size: 14px; -fx-font-family: Arial; -fx-font-weight: bold;");

        Label adresseLabel = new Label("Adresse : " + data.getAdresse());
        adresseLabel.setStyle("-fx-padding: 10px; -fx-background-radius: 5px; -fx-font-size: 14px; -fx-font-family: Arial; -fx-font-weight: bold;");

        Label dateDemandeLabel = new Label("Date de Demande : " + new SimpleDateFormat("dd/MM/yyyy").format(data.getDateDemande()));
        dateDemandeLabel.setStyle("-fx-padding: 10px; -fx-background-radius: 5px; -fx-font-size: 14px; -fx-font-family: Arial; -fx-font-weight: bold;");

        Label etatLabel = new Label("État : " + data.getEtat());
        etatLabel.setStyle("-fx-padding: 10px; -fx-background-radius: 5px; -fx-font-size: 14px; -fx-font-family: Arial; -fx-font-weight: bold;");

        Label descriptionLabel = new Label("Description : " + data.getDescription());
        descriptionLabel.setStyle("-fx-padding: 10px; -fx-background-radius: 5px; -fx-font-size: 14px; -fx-font-family: Arial; -fx-font-weight: bold;");

        Button downloadButton = new Button("Télécharger le dossier");
        downloadButton.setStyle("-fx-background-color: #bdd4f3; -fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-padding: 10px; -fx-background-radius: 5px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 5);");
        downloadButton.setOnAction(event -> {
            // Implement file download logic here
            downloadFile(data.getFileUrl());
        });

        // Accepter button
        Button accepterButton = new Button("Accepter");
        accepterButton.setStyle("-fx-background-color: #7d8c6d; -fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-padding: 10px; -fx-background-radius: 5px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 5);");
        accepterButton.setOnAction(event -> {
            data.setEtat("acceptée");
            tableView.refresh();
            modal.close();
        });

        // Refuser button
        Button refuserButton = new Button("Refuser");
        refuserButton.setStyle("-fx-background-color: #003073; -fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-padding: 10px; -fx-background-radius: 5px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 5);");
        refuserButton.setOnAction(event -> {
            data.setEtat("refusée");
            tableView.refresh();
            modal.close();
        });

        // Layout for the buttons (centered)
        HBox buttonLayout = new HBox(20, accepterButton, refuserButton, downloadButton);
        buttonLayout.setAlignment(Pos.CENTER);

        // Title label centered
        Label titleLabel = new Label("Détails de la Demande");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-font-family: Arial;");
        titleLabel.setAlignment(Pos.CENTER);

        // Create a region to add spacing between the title and the first label
        Region spacing = new Region();
        spacing.setPrefHeight(10); // Adjust this value to control the spacing

        // Create layout for the modal with spacing added
        VBox modalLayout = new VBox(10, titleLabel, spacing, clientLabel, telephoneLabel, emailLabel, adresseLabel, dateDemandeLabel, etatLabel, descriptionLabel, buttonLayout);
        modalLayout.setPadding(new Insets(20));
        modalLayout.setAlignment(Pos.CENTER_LEFT); // Align left
        modalLayout.setStyle("-fx-background-color: #f4f4f4; -fx-border-radius: 10px; -fx-border-color: #003073; -fx-border-width: 2px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 15, 0, 0, 10);");

        // Wrap the VBox in a ScrollPane to make it scrollable
        ScrollPane scrollPane = new ScrollPane(modalLayout);
        scrollPane.setFitToWidth(true);  // Make sure the content is fit to the width
        scrollPane.setStyle("-fx-background-color: transparent;");

        // Set up the scene for the modal
        Scene scene = new Scene(scrollPane, 450, 470);
        modal.setScene(scene);
        modal.initModality(Modality.APPLICATION_MODAL); // Make the modal block interaction with other windows
        modal.show();
    }

    private void downloadFile(String fileUrl) {
        // Implement file download logic here
        // This may involve creating a new thread to handle the file download,
        // showing a progress indicator, and saving the file to the local file system.
    }
}