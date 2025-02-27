package com.example.innosynergy.view;

import com.example.innosynergy.controller.PartenaireController;
import com.example.innosynergy.model.PartenaireData;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;

public class GererPartenaire extends StackPane {
    private PartenaireController partenaireController;

    public GererPartenaire() {
        partenaireController = new PartenaireController();

        // Create a FilteredList from the original data list
        FilteredList<PartenaireData> filteredData = new FilteredList<>(partenaireController.getPartenaireDataList(), p -> true);

        // TableView
        TableView<PartenaireData> tableView = new TableView<>();
        tableView.setItems(filteredData);
        tableView.setPrefWidth(1230);  // Augmenter la largeur du tableau
        tableView.setPrefHeight(800); // Ajuster la hauteur du tableau

        // Columns
        TableColumn<PartenaireData, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idColumn.setPrefWidth(120);

        TableColumn<PartenaireData, String> nomPartenaireColumn = new TableColumn<>("Nom du Partenaire");
        nomPartenaireColumn.setCellValueFactory(new PropertyValueFactory<>("nomPartenaire"));
        nomPartenaireColumn.setPrefWidth(180);

        TableColumn<PartenaireData, String> telephoneColumn = new TableColumn<>("Téléphone");
        telephoneColumn.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        telephoneColumn.setPrefWidth(180);

        TableColumn<PartenaireData, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailColumn.setPrefWidth(200);

        TableColumn<PartenaireData, String> adresseColumn = new TableColumn<>("Adresse");
        adresseColumn.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        adresseColumn.setPrefWidth(150);

        TableColumn<PartenaireData, String> nomEntrepriseColumn = new TableColumn<>("Nom Entreprise");
        nomEntrepriseColumn.setCellValueFactory(new PropertyValueFactory<>("nomEntreprise"));
        nomEntrepriseColumn.setPrefWidth(180);

        TableColumn<PartenaireData, String> dateInscriptionColumn = new TableColumn<>("Date d'Inscription");
        dateInscriptionColumn.setCellValueFactory(new PropertyValueFactory<>("dateInscription"));
        dateInscriptionColumn.setPrefWidth(150);

        TableColumn<PartenaireData, String> dateExpirationColumn = new TableColumn<>("Date d'Expiration");
        dateExpirationColumn.setCellValueFactory(new PropertyValueFactory<>("dateExpiration"));
        dateExpirationColumn.setPrefWidth(150);

        TableColumn<PartenaireData, String> typeActiviteColumn = new TableColumn<>("Type d'Activité");
        typeActiviteColumn.setCellValueFactory(new PropertyValueFactory<>("typeActivite"));
        typeActiviteColumn.setPrefWidth(150);

        TableColumn<PartenaireData, String> etatColumn = new TableColumn<>("État");
        etatColumn.setCellValueFactory(new PropertyValueFactory<>("etat"));
        etatColumn.setPrefWidth(120);

        // CellFactory for custom styling based on state
        etatColumn.setCellFactory(param -> new TableCell<PartenaireData, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                // Create a StackPane for custom styling
                StackPane stackPane = new StackPane();

                // Create a label to display the text
                Label label = new Label();
                label.setStyle("-fx-text-fill: black; -fx-font-size: 15px;");  // Smaller text size

                if (item != null && !empty) {
                    label.setText(item);

                    // Apply custom styles based on the state
                    if (item.equals("actif")) {
                        stackPane.setStyle("-fx-background-color: #EBF9F1; -fx-background-radius: 15px; -fx-padding: 2px 4px;");
                        label.setStyle("-fx-text-fill: #1F9254\n; -fx-font-size: 10px;"); // Small text for 'actif'
                    } else if (item.equals("bloqué")) {
                        stackPane.setStyle("-fx-background-color: #FEF2E5; -fx-background-radius: 15px; -fx-padding: 2px 4px;");
                        label.setStyle("-fx-text-fill: #A30D11\n; -fx-font-size: 10px;"); // Small text for 'bloqué'
                    } else {
                        stackPane.setStyle("-fx-background-color: transparent; -fx-background-radius: 15px; -fx-padding: 2px 4px;");
                    }

                    // Add the label to the StackPane
                    stackPane.getChildren().setAll(label);
                    setGraphic(stackPane);
                } else {
                    setGraphic(null);  // If the item is empty, remove the graphic
                }
            }
        });

        // Actions Column
        TableColumn<PartenaireData, Void> actionColumn = new TableColumn<>("Actions");
        actionColumn.setCellFactory(param -> new TableCell<PartenaireData, Void>() {
            private final ImageView modifyIcon = new ImageView(getClass().getResource("/images/modifier_icon.png").toExternalForm());
            private final ImageView deleteIcon = new ImageView(getClass().getResource("/images/bloque_icon.png").toExternalForm());
            private final ImageView unblockIcon = new ImageView(getClass().getResource("/images/debloque.png").toExternalForm());

            private final Button modifyButton = new Button();
            private final Button deleteButton = new Button();
            private final Button unblockButton = new Button();

            {
                modifyIcon.setFitWidth(30);
                modifyIcon.setFitHeight(30);
                deleteIcon.setFitWidth(30);
                deleteIcon.setFitHeight(30);
                unblockIcon.setFitWidth(30);
                unblockIcon.setFitHeight(30);

                modifyButton.setGraphic(modifyIcon);
                modifyButton.setStyle("-fx-background-color: transparent;");
                modifyButton.setPrefSize(5, 5);
                modifyButton.setOnAction(event -> {
                    PartenaireData data = getTableRow().getItem();
                    if (data != null) {
                        showModifyPartenaireModal(data, tableView);
                    }
                });

                deleteButton.setGraphic(deleteIcon);
                deleteButton.setStyle("-fx-background-color: transparent;");
                deleteButton.setPrefSize(5, 5);
                deleteButton.setOnAction(event -> {
                    PartenaireData data = getTableRow().getItem();
                    if (data != null && data.getEtat().equals("actif")) {
                        showDeletePartenaireModal(data);
                    }
                });

                unblockButton.setGraphic(unblockIcon);
                unblockButton.setStyle("-fx-background-color: transparent;");
                unblockButton.setPrefSize(5, 5);
                unblockButton.setOnAction(event -> {
                    PartenaireData data = getTableRow().getItem();
                    if (data != null && data.getEtat().equals("bloqué")) {
                        showUnblockPartenaireModal(data, tableView);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox(10, modifyButton, deleteButton, unblockButton);
                    hbox.setAlignment(Pos.CENTER);
                    setGraphic(hbox);
                }
            }
        });
        actionColumn.setPrefWidth(170);

        tableView.getColumns().addAll(idColumn, nomPartenaireColumn, telephoneColumn, emailColumn, adresseColumn, nomEntrepriseColumn, dateInscriptionColumn, dateExpirationColumn, typeActiviteColumn, etatColumn, actionColumn);

        // Row styling for alternating row colors
        tableView.setRowFactory(tv -> {
            TableRow<PartenaireData> row = new TableRow<>();
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

        // Search Bar and Add Button
        HBox topPanel = new HBox();
        topPanel.setSpacing(10);
        topPanel.setPadding(new Insets(10));
        topPanel.setAlignment(Pos.CENTER_LEFT);

        // Search TextField
        TextField searchField = new TextField();
        searchField.setPromptText("Rechercher partenaire...");
        searchField.setPrefWidth(250);


        // Etat ComboBox for filtering
        ComboBox<String> etatComboBox = new ComboBox<>();
        etatComboBox.getItems().addAll("Tous", "actif", "bloqué");
        etatComboBox.setValue("Tous");
        etatComboBox.setPrefWidth(100);


        // Filter the data as the user types in the search field
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(partenaire -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return partenaire.getNomPartenaire().toLowerCase().contains(lowerCaseFilter) ||
                        partenaire.getTelephone().toLowerCase().contains(lowerCaseFilter) ||
                        partenaire.getEmail().toLowerCase().contains(lowerCaseFilter) ||
                        partenaire.getAdresse().toLowerCase().contains(lowerCaseFilter) ||
                        partenaire.getNomEntreprise().toLowerCase().contains(lowerCaseFilter) ||
                        partenaire.getEtat().toLowerCase().contains(lowerCaseFilter) ||
                        partenaire.getDateInscription().toLowerCase().contains(lowerCaseFilter) ||
                        partenaire.getDateExpiration().toLowerCase().contains(lowerCaseFilter) ||
                        partenaire.getTypeActivite().toLowerCase().contains(lowerCaseFilter);
            });
        });


        // Filter the data as the user selects an etat
        etatComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(partenaire -> {
                if (newValue.equals("Tous")) {
                    return searchField.getText() == null || searchField.getText().isEmpty() ||
                            partenaire.getNomPartenaire().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                            partenaire.getTelephone().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                            partenaire.getEmail().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                            partenaire.getAdresse().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                            partenaire.getNomEntreprise().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                            partenaire.getEtat().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                            partenaire.getDateInscription().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                            partenaire.getDateExpiration().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                            partenaire.getTypeActivite().toLowerCase().contains(searchField.getText().toLowerCase());
                } else {
                    return partenaire.getEtat().equals(newValue) &&
                            (searchField.getText() == null || searchField.getText().isEmpty() ||
                                    partenaire.getNomPartenaire().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                                    partenaire.getTelephone().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                                    partenaire.getEmail().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                                    partenaire.getAdresse().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                                    partenaire.getNomEntreprise().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                                    partenaire.getEtat().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                                    partenaire.getDateInscription().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                                    partenaire.getDateExpiration().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                                    partenaire.getTypeActivite().toLowerCase().contains(searchField.getText().toLowerCase()));
                }
            });
        });

        // Add Button
        Button addButton = new Button("Ajouter Partenaire");
        addButton.setStyle("-fx-background-color: #013A71; -fx-text-fill: white;");
        addButton.setPrefWidth(150);
        addButton.setPrefHeight(30);

        addButton.setOnAction(event -> {
            showAddPartenaireModal();
        });

        HBox.setHgrow(searchField, Priority.ALWAYS);
        HBox.setHgrow(etatComboBox, Priority.NEVER);
        HBox.setHgrow(addButton, Priority.NEVER);

        topPanel.getChildren().addAll(searchField, etatComboBox, addButton);

        // TableView wrapper with padding on left and right
        HBox tableWrapper = new HBox(tableView);
        tableWrapper.setPadding(new Insets(0, 5, 0, 5));  // Padding de 5px à droite et à gauche
        tableWrapper.setAlignment(Pos.CENTER);  // Centrer le tableau si nécessaire

        // Create a Label for the title
        Label titleLabel = new Label("Liste des Partenaires");
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


    private void showDeletePartenaireModal(PartenaireData data) {
        Stage modal = new Stage();
        modal.setTitle("Confirmation de bloquage");

        // Create the labels and input fields
        Label confirmationLabel = new Label("Voulez-vous vraiment bloquer " + data.getNomPartenaire() + " ?");
        confirmationLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // Create the buttons with a more modern look and shadow effect
        Button confirmButton = new Button("Confirmer");
        confirmButton.setStyle("-fx-background-color: #7d8c6d; -fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-padding: 10px; -fx-background-radius: 5px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 5);");

        Button cancelButton = new Button("Annuler");
        cancelButton.setStyle("-fx-background-color: #003073; -fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-padding: 10px; -fx-background-radius: 5px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 5);");

        // Confirm action
        confirmButton.setOnAction(e -> {
            partenaireController.removePartenaire(data);
            modal.close();
        });

        // Cancel action
        cancelButton.setOnAction(e -> modal.close());

        // Layout for the buttons (side by side)
        HBox buttonLayout = new HBox(20, confirmButton, cancelButton);
        buttonLayout.setAlignment(Pos.CENTER);

        // Title label centered
        Label titleLabel = new Label("Bloquer Partenaire");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        titleLabel.setAlignment(Pos.CENTER);

        // Create a region to add spacing between the title and the first label
        Region spacing = new Region();
        spacing.setPrefHeight(5); // Adjust this value to control the spacing

        // Create layout for the modal with spacing added
        VBox modalLayout = new VBox(10, titleLabel, spacing, confirmationLabel, buttonLayout);
        modalLayout.setPadding(new Insets(20));
        modalLayout.setAlignment(Pos.CENTER);
        modalLayout.setStyle("-fx-background-color: #f4f4f4; -fx-border-radius: 10px; -fx-border-color: #003073; -fx-border-width: 2px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 15, 0, 0, 10);");

        // Wrap the VBox in a ScrollPane to make it scrollable
        ScrollPane scrollPane = new ScrollPane(modalLayout);
        scrollPane.setFitToWidth(true);  // Make sure the content is fit to the width
        scrollPane.setStyle("-fx-background-color: transparent;");

        // Set up the scene for the modal
        Scene scene = new Scene(scrollPane, 450, 180);
        modal.setScene(scene);
        modal.initModality(Modality.APPLICATION_MODAL); // Make the modal block interaction with other windows
        modal.show();
    }
    private void showAddPartenaireModal() {
        // Create a new Stage (window) for the modal
        Stage modal = new Stage();
        modal.setTitle("Ajouter un Partenaire");

        // Create the labels and input fields
        Label nomPartenaireLabel = new Label("Nom du Partenaire :");
        TextField nomPartenaireField = new TextField();
        nomPartenaireField.setPromptText("Nom du Partenaire");
        nomPartenaireField.setStyle("-fx-padding: 10px; -fx-background-radius: 5px;");

        Label telephoneLabel = new Label("Téléphone :");
        TextField telephoneField = new TextField();
        telephoneField.setPromptText("Téléphone");
        telephoneField.setStyle("-fx-padding: 10px; -fx-background-radius: 5px;");

        Label emailLabel = new Label("Email :");
        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setStyle("-fx-padding: 10px; -fx-background-radius: 5px;");

        Label adresseLabel = new Label("Adresse :");
        TextField adresseField = new TextField();
        adresseField.setPromptText("Adresse");
        adresseField.setStyle("-fx-padding: 10px; -fx-background-radius: 5px;");

        Label nomEntrepriseLabel = new Label("Nom Entreprise :");
        TextField nomEntrepriseField = new TextField();
        nomEntrepriseField.setPromptText("Nom Entreprise");
        nomEntrepriseField.setStyle("-fx-padding: 10px; -fx-background-radius: 5px;");

        Label typeActiviteLabel = new Label("Type d'Activité :");
        TextField typeActiviteField = new TextField();
        typeActiviteField.setPromptText("Type d'Activité");
        typeActiviteField.setStyle("-fx-padding: 10px; -fx-background-radius: 5px;");

        Label dateExpirationLabel = new Label("Date d'Expiration :");
        DatePicker dateExpirationField = new DatePicker();
        dateExpirationField.setPromptText("Date d'Expiration");

        // Create the buttons with a more modern look and shadow effect
        Button saveButton = new Button("Enregistrer");
        saveButton.setStyle("-fx-background-color: #7d8c6d; -fx-text-fill: #e9fbf1; -fx-font-weight: bold; -fx-padding: 10px; -fx-background-radius: 5px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 5);");

        Button cancelButton = new Button("Annuler");
        cancelButton.setStyle("-fx-background-color: #003073; -fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-padding: 10px; -fx-background-radius: 5px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 5);");

        // Action for saving
        saveButton.setOnAction(e -> {
            String id = "#" + (int) (Math.random() * 100000); // Generate a random ID for the new Partenaire
            PartenaireData newPartenaire = new PartenaireData(id, nomPartenaireField.getText(), telephoneField.getText(), emailField.getText(),
                    adresseField.getText(), "actif", nomEntrepriseField.getText(), null, dateExpirationField.getValue().toString(), typeActiviteField.getText());
            partenaireController.addPartenaire(newPartenaire);
            modal.close(); // Close the modal after saving
        });

        // Action for canceling
        cancelButton.setOnAction(e -> modal.close());

        // Layout for the buttons (side by side)
        HBox buttonLayout = new HBox(20, saveButton, cancelButton);
        buttonLayout.setAlignment(Pos.CENTER);

        // Title label centered
        Label titleLabel = new Label("Ajouter un Partenaire");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        titleLabel.setAlignment(Pos.CENTER);

        // Create a region to add spacing between the title and the first label
        Region spacing = new Region();
        spacing.setPrefHeight(10); // Adjust this value to control the spacing

        // Create layout for the modal with spacing added
        VBox modalLayout = new VBox(10, titleLabel, spacing, nomPartenaireLabel, nomPartenaireField, telephoneLabel, telephoneField, emailLabel, emailField,
                adresseLabel, adresseField, nomEntrepriseLabel, nomEntrepriseField, typeActiviteLabel, typeActiviteField, dateExpirationLabel, dateExpirationField, buttonLayout);
        modalLayout.setPadding(new Insets(20));
        modalLayout.setAlignment(Pos.CENTER);
        modalLayout.setStyle("-fx-background-color: #f4f4f4; -fx-border-radius: 10px; -fx-border-color: #003073; -fx-border-width: 2px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 15, 0, 0, 10);");

        // Wrap the VBox in a ScrollPane to make it scrollable
        ScrollPane scrollPane = new ScrollPane(modalLayout);
        scrollPane.setFitToWidth(true);  // Make sure the content is fit to the width
        scrollPane.setStyle("-fx-background-color: transparent;");

        // Set up the scene for the modal
        Scene scene = new Scene(scrollPane, 620, 670); // Increase the size of the modal
        modal.setScene(scene);
        modal.initModality(Modality.APPLICATION_MODAL); // Make the modal block interaction with other windows
        modal.show();
    }

    private void showUnblockPartenaireModal(PartenaireData data, TableView<PartenaireData> tableView) {
        Stage modal = new Stage();
        modal.setTitle("Confirmation de déblocage");

        // Create the labels and input fields
        Label confirmationLabel = new Label("Voulez-vous vraiment débloquer " + data.getNomPartenaire() + " ?");
        confirmationLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // Create the buttons with a more modern look and shadow effect
        Button confirmButton = new Button("Confirmer");
        confirmButton.setStyle("-fx-background-color: #7d8c6d; -fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-padding: 10px; -fx-background-radius: 5px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 5);");

        Button cancelButton = new Button("Annuler");
        cancelButton.setStyle("-fx-background-color: #003073; -fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-padding: 10px; -fx-background-radius: 5px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 5);");

        // Confirm action
        confirmButton.setOnAction(e -> {
            data.setEtat("actif"); // Set the state to active
            partenaireController.updatePartenaire(data, data); // Update the data
            tableView.refresh(); // Refresh TableView
            modal.close();
        });

        // Cancel action
        cancelButton.setOnAction(e -> modal.close());

        // Layout for the buttons (side by side)
        HBox buttonLayout = new HBox(20, confirmButton, cancelButton);
        buttonLayout.setAlignment(Pos.CENTER);

        // Title label centered
        Label titleLabel = new Label("Débloquer Partenaire");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        titleLabel.setAlignment(Pos.CENTER);

        // Create a region to add spacing between the title and the first label
        Region spacing = new Region();
        spacing.setPrefHeight(5); // Adjust this value to control the spacing

        // Create layout for the modal with spacing added
        VBox modalLayout = new VBox(10, titleLabel, spacing, confirmationLabel, buttonLayout);
        modalLayout.setPadding(new Insets(20));
        modalLayout.setAlignment(Pos.CENTER);
        modalLayout.setStyle("-fx-background-color: #f4f4f4; -fx-border-radius: 10px; -fx-border-color: #003073; -fx-border-width: 2px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 15, 0, 0, 10);");

        // Wrap the VBox in a ScrollPane to make it scrollable
        ScrollPane scrollPane = new ScrollPane(modalLayout);
        scrollPane.setFitToWidth(true);  // Make sure the content is fit to the width
        scrollPane.setStyle("-fx-background-color: transparent;");

        // Set up the scene for the modal
        Scene scene = new Scene(scrollPane, 450, 180);
        modal.setScene(scene);
        modal.initModality(Modality.APPLICATION_MODAL); // Make the modal block interaction with other windows
        modal.show();
    }
    private void showModifyPartenaireModal(PartenaireData data, TableView<PartenaireData> tableView) {
        Stage modal = new Stage();
        modal.setTitle("Modifier Partenaire");

        // Create the labels and input fields
        Label nomPartenaireLabel = new Label("Nom du Partenaire :");
        TextField nomPartenaireField = new TextField(data.getNomPartenaire());
        nomPartenaireField.setPromptText("Nom du Partenaire");
        nomPartenaireField.setStyle("-fx-padding: 10px; -fx-background-radius: 5px;");

        Label telephoneLabel = new Label("Téléphone :");
        TextField telephoneField = new TextField(data.getTelephone());
        telephoneField.setPromptText("Téléphone");
        telephoneField.setStyle("-fx-padding: 10px; -fx-background-radius: 5px;");

        Label emailLabel = new Label("Email :");
        TextField emailField = new TextField(data.getEmail());
        emailField.setPromptText("Email");
        emailField.setStyle("-fx-padding: 10px; -fx-background-radius: 5px;");

        Label adresseLabel = new Label("Adresse :");
        TextField adresseField = new TextField(data.getAdresse());
        adresseField.setPromptText("Adresse");
        adresseField.setStyle("-fx-padding: 10px; -fx-background-radius: 5px;");

        Label nomEntrepriseLabel = new Label("Nom Entreprise :");
        TextField nomEntrepriseField = new TextField(data.getNomEntreprise());
        nomEntrepriseField.setPromptText("Nom Entreprise");
        nomEntrepriseField.setStyle("-fx-padding: 10px; -fx-background-radius: 5px;");

        Label dateExpirationLabel = new Label("Date d'Expiration :");
        DatePicker dateExpirationField = new DatePicker(LocalDate.parse(data.getDateExpiration()));
        dateExpirationField.setPromptText("Date d'Expiration");

        Label typeActiviteLabel = new Label("Type d'Activité :");
        TextField typeActiviteField = new TextField(data.getTypeActivite());
        typeActiviteField.setPromptText("Type d'Activité");
        typeActiviteField.setStyle("-fx-padding: 10px; -fx-background-radius: 5px;");

        // Create the buttons with a more modern look and shadow effect
        Button saveButton = new Button("Enregistrer");
        saveButton.setStyle("-fx-background-color: #7d8c6d; -fx-text-fill: #e9fbf1; -fx-font-weight: bold; -fx-padding: 10px; -fx-background-radius: 5px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 5);");

        Button cancelButton = new Button("Annuler");
        cancelButton.setStyle("-fx-background-color: #003073; -fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-padding: 10px; -fx-background-radius: 5px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 5);");

        // Save action
        saveButton.setOnAction(e -> {
            PartenaireData modifiedPartenaire = new PartenaireData(data.getId(), nomPartenaireField.getText(), telephoneField.getText(), emailField.getText(),
                    adresseField.getText(), data.getEtat(), nomEntrepriseField.getText(), data.getDateInscription(), dateExpirationField.getValue().toString(), typeActiviteField.getText());
            partenaireController.updatePartenaire(data, modifiedPartenaire);
            tableView.refresh(); // Refresh TableView
            modal.close();
        });

        cancelButton.setOnAction(e -> modal.close());

        // Layout for the buttons (side by side)
        HBox buttonLayout = new HBox(20, saveButton, cancelButton);
        buttonLayout.setAlignment(Pos.CENTER);

        // Title label centered
        Label titleLabel = new Label("Modifier un Partenaire");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        titleLabel.setAlignment(Pos.CENTER);

        // Create a region to add spacing between the title and the first label
        Region spacing = new Region();
        spacing.setPrefHeight(10); // Adjust this value to control the spacing

        // Create layout for the modal with spacing added
        VBox modalLayout = new VBox(10, titleLabel, spacing, nomPartenaireLabel, nomPartenaireField, telephoneLabel, telephoneField, emailLabel, emailField, adresseLabel, adresseField, nomEntrepriseLabel, nomEntrepriseField, typeActiviteLabel, typeActiviteField, dateExpirationLabel, dateExpirationField, buttonLayout);
        modalLayout.setPadding(new Insets(20));
        modalLayout.setAlignment(Pos.CENTER);
        modalLayout.setStyle("-fx-background-color: #f4f4f4; -fx-border-radius: 10px; -fx-border-color: #003073; -fx-border-width: 2px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 15, 0, 0, 10);");

        // Wrap the VBox in a ScrollPane to make it scrollable
        ScrollPane scrollPane = new ScrollPane(modalLayout);
        scrollPane.setFitToWidth(true);  // Make sure the content is fit to the width
        scrollPane.setStyle("-fx-background-color: transparent;");

        // Set up the scene for the modal
        Scene scene = new Scene(scrollPane, 620, 670); // Increase the size of the modal
        modal.setScene(scene);
        modal.initModality(Modality.APPLICATION_MODAL); // Make the modal block interaction with other windows
        modal.show();
    }}