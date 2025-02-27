package com.example.innosynergy.view;

import com.example.innosynergy.controller.BenevolController;
import com.example.innosynergy.model.Benevol;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;

public class BenevolView extends StackPane {
    private BenevolController benevolController;

    public BenevolView() {
        benevolController = new BenevolController();

        // Create a FilteredList from the original data list
        FilteredList<Benevol> filteredData = new FilteredList<>(benevolController.getBenevolDataList(), p -> true);

        // TableView
        TableView<Benevol> tableView = new TableView<>();
        tableView.setItems(filteredData);
        tableView.setPrefWidth(1230);
        tableView.setPrefHeight(800);

        // Columns
        TableColumn<Benevol, Integer> idCol = new TableColumn<>("#");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setPrefWidth(120);

        TableColumn<Benevol, String> titreCol = new TableColumn<>("Titre");
        titreCol.setCellValueFactory(new PropertyValueFactory<>("titre"));
        titreCol.setPrefWidth(180);

        TableColumn<Benevol, String> descCol = new TableColumn<>("Description");
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        descCol.setPrefWidth(180);

        TableColumn<Benevol, LocalDate> dateCol = new TableColumn<>("Date de Bénévolat");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("dateBenevolat"));
        dateCol.setPrefWidth(200);

        TableColumn<Benevol, String> statusCol = new TableColumn<>("Statut");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusCol.setPrefWidth(150);

        // Actions Column
        TableColumn<Benevol, Void> actionColumn = new TableColumn<>("Actions");
        actionColumn.setCellFactory(param -> new TableCell<Benevol, Void>() {
            Button consultButton = new Button("Consulter");

            {
                consultButton.setStyle("-fx-background-color: #013A71; -fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-padding: 5px; -fx-background-radius: 5px;");
                consultButton.setOnAction(event -> {
                    Benevol data = getTableRow().getItem();
                    if (data != null) {
                        showConsultBenevolModal(data);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox(10, consultButton);
                    hbox.setAlignment(Pos.CENTER);
                    setGraphic(hbox);
                }
            }
        });
        actionColumn.setPrefWidth(100);

        tableView.getColumns().addAll(idCol, titreCol, descCol, dateCol, statusCol, actionColumn);

        // Row styling for alternating row colors
        tableView.setRowFactory(tv -> {
            TableRow<Benevol> row = new TableRow<>();
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
        searchField.setPromptText("Rechercher bénévole...");
        searchField.setPrefWidth(250);

        // Filter the data as the user types in the search field
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(benevol -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return benevol.getTitre().toLowerCase().contains(lowerCaseFilter) ||
                        benevol.getDescription().toLowerCase().contains(lowerCaseFilter) ||
                        benevol.getDateBenevolat().toString().toLowerCase().contains(lowerCaseFilter) ||
                        benevol.getStatus().toLowerCase().contains(lowerCaseFilter);
            });
        });

        HBox.setHgrow(searchField, Priority.ALWAYS);
        topPanel.getChildren().addAll(searchField);

        // TableView wrapper with padding on left and right
        HBox tableWrapper = new HBox(tableView);
        tableWrapper.setPadding(new Insets(0, 5, 0, 5));
        tableWrapper.setAlignment(Pos.CENTER);

        // Create a Label for the title
        Label titleLabel = new Label("Liste des Bénévolats");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-alignment: center; -fx-text-fill: #003073; -fx-padding: 10px; -fx-font-family: Arial;");

        // Main layout
        VBox mainLayout = new VBox();
        mainLayout.setStyle("-fx-background-color: white;");
        mainLayout.setAlignment(Pos.TOP_CENTER);

        mainLayout.getChildren().addAll(titleLabel, topPanel, tableWrapper);

        this.getChildren().add(mainLayout);
    }

    private void showConsultBenevolModal(Benevol data) {
        Stage modal = new Stage();
        modal.setTitle("Consulter Bénévolat");

        // Create the labels and input fields
        Label titleLabel = new Label("Titre : " + data.getTitre());
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label descLabel = new Label("Description : " + data.getDescription());
        descLabel.setStyle("-fx-font-size: 14px;");

        Label dateLabel = new Label("Date de Bénévolat : " + data.getDateBenevolat().toString());
        dateLabel.setStyle("-fx-font-size: 14px;");

        Label statusLabel = new Label("Statut : " + data.getStatus());
        statusLabel.setStyle("-fx-font-size: 14px;");

        // Create the buttons with a more modern look
        Button closeButton = new Button("Fermer");
        closeButton.setStyle("-fx-background-color: #1F9254; -fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-padding: 10px; -fx-background-radius: 5px;");
        closeButton.setOnAction(e -> modal.close());

        // Layout for the buttons (centered)
        HBox buttonLayout = new HBox(20, closeButton);
        buttonLayout.setAlignment(Pos.CENTER);

        // Main layout for the modal
        VBox modalLayout = new VBox(10, titleLabel, descLabel, dateLabel, statusLabel, buttonLayout);
        modalLayout.setPadding(new Insets(20));
        modalLayout.setAlignment(Pos.CENTER);
        modalLayout.setStyle("-fx-background-color: #f4f4f4; -fx-border-radius: 10px; -fx-border-color: #013A71; -fx-border-width: 2px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 15, 0, 0, 10);");

        // Wrap the VBox in a ScrollPane to make it scrollable
        ScrollPane scrollPane = new ScrollPane(modalLayout);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;");

        // Set up the scene for the modal
        Scene scene = new Scene(scrollPane, 450, 210);
        modal.setScene(scene);
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.show();
    }
}