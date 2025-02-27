package com.example.innosynergy.view;

import com.example.innosynergy.controller.EventController;
import com.example.innosynergy.model.Event;
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

public class EventView extends StackPane {
    private EventController eventController;

    public EventView() {
        eventController = new EventController();

        // Create a FilteredList from the original data list
        FilteredList<Event> filteredData = new FilteredList<>(eventController.getEventDataList(), p -> true);

        // TableView
        TableView<Event> tableView = new TableView<>();
        tableView.setItems(filteredData);
        tableView.setPrefWidth(1230);
        tableView.setPrefHeight(800);

        // Columns
        TableColumn<Event, String> numberCol = new TableColumn<>("#");
        numberCol.setCellValueFactory(new PropertyValueFactory<>("number"));
        numberCol.setPrefWidth(120);

        TableColumn<Event, String> titleCol = new TableColumn<>("Titre");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleCol.setPrefWidth(180);

        TableColumn<Event, String> descCol = new TableColumn<>("Description");
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        descCol.setPrefWidth(180);

        TableColumn<Event, String> dateCol = new TableColumn<>("Date de l'événement");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("dateEvenement"));
        dateCol.setPrefWidth(200);

        TableColumn<Event, String> placeCol = new TableColumn<>("Lieu");
        placeCol.setCellValueFactory(new PropertyValueFactory<>("place"));
        placeCol.setPrefWidth(150);

        TableColumn<Event, String> partnerCol = new TableColumn<>("Partenaire");
        partnerCol.setCellValueFactory(new PropertyValueFactory<>("partner"));
        partnerCol.setPrefWidth(180);

        // Actions Column
        TableColumn<Event, Void> actionColumn = new TableColumn<>("Actions");
        actionColumn.setCellFactory(param -> new TableCell<Event, Void>() {
            Button blockButton = new Button("Bloquer");

            {
                blockButton.setStyle("-fx-background-color: #013A71; -fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-padding: 5px; -fx-background-radius: 5px;");
                blockButton.setOnAction(event -> {
                    Event data = getTableRow().getItem();
                    if (data != null) {
                        showBlockEventModal(data, tableView);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox(10, blockButton);
                    hbox.setAlignment(Pos.CENTER);
                    setGraphic(hbox);
                }
            }
        });
        actionColumn.setPrefWidth(100);

        tableView.getColumns().addAll(numberCol, titleCol, descCol, dateCol, placeCol, partnerCol, actionColumn);

        // Row styling for alternating row colors
        tableView.setRowFactory(tv -> {
            TableRow<Event> row = new TableRow<>();
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
        searchField.setPromptText("Rechercher événement...");
        searchField.setPrefWidth(250);

        // Filter the data as the user types in the search field
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(event -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return event.getTitle().toLowerCase().contains(lowerCaseFilter) ||
                        event.getDescription().toLowerCase().contains(lowerCaseFilter) ||
                        event.getDateEvenement().toLowerCase().contains(lowerCaseFilter) ||
                        event.getPlace().toLowerCase().contains(lowerCaseFilter) ||
                        event.getPartner().toLowerCase().contains(lowerCaseFilter) ||
                        event.getStatus().toLowerCase().contains(lowerCaseFilter);
            });
        });

        HBox.setHgrow(searchField, Priority.ALWAYS);
        topPanel.getChildren().addAll(searchField);

        // TableView wrapper with padding on left and right
        HBox tableWrapper = new HBox(tableView);
        tableWrapper.setPadding(new Insets(0, 5, 0, 5));
        tableWrapper.setAlignment(Pos.CENTER);

        // Create a Label for the title
        Label titleLabel = new Label("Liste des Événements");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-alignment: center; -fx-text-fill: #003073; -fx-padding: 10px; -fx-font-family: Arial;");

        // Main layout
        VBox mainLayout = new VBox();
        mainLayout.setStyle("-fx-background-color: white;");
        mainLayout.setAlignment(Pos.TOP_CENTER);

        mainLayout.getChildren().addAll(titleLabel, topPanel, tableWrapper);

        this.getChildren().add(mainLayout);
    }

    private void showDeleteEventModal(Event data, TableView<Event> tableView) {
        Stage modal = new Stage();
        modal.setTitle("Confirmation de suppression");

        // Create the labels and input fields
        Label confirmationLabel = new Label("Voulez-vous vraiment supprimer " + data.getTitle() + " ?");
        confirmationLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // Create the buttons with a more modern look
        Button confirmButton = new Button("Save");
        confirmButton.setStyle("-fx-background-color: #013A71; -fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-padding: 5px; -fx-background-radius: 5px;");

        Button cancelButton = new Button("Annuler");
        cancelButton.setStyle("-fx-background-color: #1F9254; -fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-padding: 5px; -fx-background-radius: 5px;");

        // Confirm action
        confirmButton.setOnAction(e -> {
            eventController.removeEvent(data);
            tableView.getItems().remove(data);
            modal.close();
        });

        // Cancel action
        cancelButton.setOnAction(e -> modal.close());

        // Layout for the buttons (side by side)
        HBox buttonLayout = new HBox(20, confirmButton, cancelButton);
        buttonLayout.setAlignment(Pos.CENTER);

        // Title label centered
        Label titleLabel = new Label("Supprimer Événement");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        titleLabel.setAlignment(Pos.CENTER);

        // Create a region to add spacing between the title and the first label
        Region spacing = new Region();
        spacing.setPrefHeight(5);

        // Create layout for the modal with spacing added
        VBox modalLayout = new VBox(10, titleLabel, spacing, confirmationLabel, buttonLayout);
        modalLayout.setPadding(new Insets(20));
        modalLayout.setAlignment(Pos.CENTER);
        modalLayout.setStyle("-fx-background-color: #f4f4f4; -fx-border-radius: 10px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 15, 0, 0, 10);");

        // Wrap the VBox in a ScrollPane to make it scrollable
        ScrollPane scrollPane = new ScrollPane(modalLayout);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;");

        // Set up the scene for the modal
        Scene scene = new Scene(scrollPane, 450, 300);
        modal.setScene(scene);
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.show();
    }

    private void showBlockEventModal(Event data, TableView<Event> tableView) {
        Stage modal = new Stage();
        modal.setTitle("Bloquer Événement");

        // Create the labels and input fields
        Label confirmationLabel = new Label("Voulez-vous vraiment bloquer " + data.getTitle() + " ?");
        confirmationLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // Reasons for blocking
        Label reasonLabel = new Label("Raison du blocage :");
        CheckBox reason1 = new CheckBox("Contenu indésirable");
        CheckBox reason2 = new CheckBox("Spam");
        CheckBox reason3 = new CheckBox("Violation des règles");
        CheckBox reason4 = new CheckBox("Autre");
        VBox reasonsBox = new VBox(10, reason1, reason2, reason3, reason4);
        reasonsBox.setPadding(new Insets(10, 0, 10, 0));
        reasonsBox.setAlignment(Pos.CENTER_LEFT);

        // Text area for additional explanation
        Label explanationLabel = new Label("Explication:");
        TextArea explanationTextArea = new TextArea();
        explanationTextArea.setPromptText("Ajouter une explication...");
        explanationTextArea.setWrapText(true);
        explanationTextArea.setPrefHeight(100);
        explanationTextArea.setPrefWidth(300);

        // Create the buttons with a more modern look
        Button confirmButton = new Button("Enregistrer");
        confirmButton.setStyle("-fx-background-color: #013A71; -fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-padding: 10px; -fx-background-radius: 5px;");

        Button cancelButton = new Button("Annuler");
        cancelButton.setStyle("-fx-background-color: #1F9254; -fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-padding: 10px; -fx-background-radius: 5px;");

        // Confirm action
        confirmButton.setOnAction(e -> {
            data.setStatus("bloqué");
            eventController.updateEvent(data, data);
            tableView.refresh();
            modal.close();
        });

        // Cancel action
        cancelButton.setOnAction(e -> modal.close());

        // Layout for the buttons (side by side)
        HBox buttonLayout = new HBox(20, confirmButton, cancelButton);
        buttonLayout.setAlignment(Pos.CENTER);

        // Title label centered
        Label titleLabel = new Label("Bloquer Événement");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        titleLabel.setAlignment(Pos.CENTER);

        // Create a region to add spacing between the title and the first label
        Region spacing = new Region();
        spacing.setPrefHeight(5);

        // Create layout for the modal with spacing added
        VBox modalLayout = new VBox(10, titleLabel, spacing, confirmationLabel, reasonLabel, reasonsBox, explanationLabel, explanationTextArea, buttonLayout);
        modalLayout.setPadding(new Insets(20));
        modalLayout.setAlignment(Pos.CENTER);
        modalLayout.setStyle("-fx-background-color: #f4f4f4; -fx-border-radius: 10px; -fx-border-color: #013A71; -fx-border-width: 2px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 15, 0, 0, 10);");

        // Wrap the VBox in a ScrollPane to make it scrollable
        ScrollPane scrollPane = new ScrollPane(modalLayout);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;");

        // Set up the scene for the modal
        Scene scene = new Scene(scrollPane, 500, 470);
        modal.setScene(scene);
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.show();
    }
}
