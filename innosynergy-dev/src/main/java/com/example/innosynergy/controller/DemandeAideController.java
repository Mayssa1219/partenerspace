package com.example.innosynergy.controller;

import com.example.innosynergy.dao.DemandeDao;
import com.example.innosynergy.dao.DemandeDaoImpl;
import com.example.innosynergy.model.DemandeData;
import com.example.innosynergy.model.User;
import com.example.innosynergy.utils.SessionManager;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
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
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class DemandeAideController implements Initializable {

    @FXML
    private TableView<DemandeData> tableView;

    @FXML
    private TableColumn<DemandeData, Integer> demandeurColumn;

    @FXML
    private TableColumn<DemandeData, String> emailColumn;

    @FXML
    private TableColumn<DemandeData, String> adresseColumn;

    @FXML
    private TableColumn<DemandeData, Double> MontantColumn;

    @FXML
    private TableColumn<DemandeData, LocalDateTime> dateDemandeColumn;

    @FXML
    private TableColumn<DemandeData, String> StatusColumn;

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

    private DemandeDao demandeDao;

    private File selectedFile;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        demandeDao = new DemandeDaoImpl();
        User currentUser = SessionManager.getUser(SessionManager.getCurrentSessionId());
        if (currentUser != null) {
            int idPartenaire = currentUser.getIdUtilisateur();
            demandeDataList = FXCollections.observableArrayList(demandeDao.getDemandesByPartenaire(idPartenaire));
        } else {
            demandeDataList = FXCollections.observableArrayList();
        }
        filteredData = new FilteredList<>(demandeDataList, p -> true);

        // Lier les colonnes aux données
        demandeurColumn.setCellValueFactory(new PropertyValueFactory<>("idClient"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("typeAide"));
        adresseColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        MontantColumn.setCellValueFactory(new PropertyValueFactory<>("montantDemande"));
        dateDemandeColumn.setCellValueFactory(new PropertyValueFactory<>("dateDemande"));
        StatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        preuveColumn.setCellValueFactory(new PropertyValueFactory<>("preuves"));

        // Définir une cellule personnalisée pour la colonne "Preuve"
        preuveColumn.setCellFactory(column -> new TableCell<DemandeData, String>() {
            private final ImageView imageView = new ImageView();
            private final Button downloadButton = new Button("Télécharger");

            {
                imageView.setFitWidth(100);
                imageView.setFitHeight(100);
                setGraphic(imageView);
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                downloadButton.getStyleClass().add("download-button"); // Ajouter la classe CSS
                downloadButton.setOnAction(event -> {
                    DemandeData data = getTableRow().getItem();
                    if (data != null) {
                        downloadFile(data.getPreuves());
                    }
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    if (item.toLowerCase().endsWith(".pdf")) {
                        imageView.setImage(new Image(getClass().getResourceAsStream("/images/crayon.png")));
                    } else {
                        File file = new File("uploads/" + item);
                        if (file.exists()) {
                            imageView.setImage(new Image(file.toURI().toString()));
                        }
                    }
                    setGraphic(imageView);
                    setGraphic(downloadButton);
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
                // Convertir LocalDateTime en String
                String dateDemandeStr = demande.getDateDemande().toString().toLowerCase();

                return String.valueOf(demande.getIdClient()).toLowerCase().contains(lowerCaseFilter) ||
                        demande.getTypeAide().toLowerCase().contains(lowerCaseFilter) ||
                        demande.getDescription().toLowerCase().contains(lowerCaseFilter) ||
                        String.valueOf(demande.getMontantDemande()).toLowerCase().contains(lowerCaseFilter) ||
                        dateDemandeStr.contains(lowerCaseFilter) ||
                        demande.getStatus().toLowerCase().contains(lowerCaseFilter);
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
        downloadButton.getStyleClass().add("download-button"); // Ajouter la classe CSS

        downloadButton.setOnAction(event -> generateAndDownloadPdf(data)); // Ajouter action pour générer et télécharger le PDF

        // Disposition des boutons
        HBox buttonLayout = new HBox(20, downloadButton);
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
        TextField clientNameField = new TextField();
        clientNameField.setPromptText("Nom du Client");

        // Utiliser une ComboBox pour le type d'aide
        ComboBox<String> typeAideComboBox = new ComboBox<>();
        typeAideComboBox.setPromptText("Type d'Aide");
        typeAideComboBox.getItems().addAll("financiere", "non_financiere");
        typeAideComboBox.setPrefWidth(400); // Définir la largeur de la ComboBox

        TextArea descriptionField = new TextArea();
        descriptionField.setText("Description");

        TextField montantDemandeField = new TextField();
        montantDemandeField.setPromptText("Montant de la Demande");

        DatePicker dateDemandePicker = new DatePicker();
        dateDemandePicker.setPromptText("Date de Demande");
        dateDemandePicker.setPrefWidth(400);

        // Champ pour l'importation d'image ou PDF
        Button importFileButton = new Button("Importer un fichier");
        importFileButton.setPrefWidth(400); // Définir la largeur du bouton
        importFileButton.getStyleClass().add("import-button"); // Ajouter une classe CSS

        Label importedFileLabel = new Label("Aucun fichier sélectionné");

        importFileButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choisir un fichier");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Images et PDF", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.pdf")
            );
            selectedFile = fileChooser.showOpenDialog(new Stage());
            if (selectedFile != null) {
                importedFileLabel.setText("Fichier sélectionné : " + selectedFile.getName());
                System.out.println("Fichier sélectionné : " + selectedFile.getName());
            }
        });

        // Bouton Enregistrer en vert
        Button saveButton = new Button("Enregistrer");
        saveButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;"); // Vert avec texte blanc
        saveButton.setOnAction(event -> {
            // Récupérer l'utilisateur courant depuis la session
            User currentUser = SessionManager.getUser(SessionManager.getCurrentSessionId());
            if (currentUser != null) {
                int idPartenaire = currentUser.getIdUtilisateur();

                // Récupérer l'ID du client à partir de son nom
                String clientName = clientNameField.getText();
                int idClient = demandeDao.getClientIdByName(clientName);
                if (idClient == -1) {
                    showAlert("Erreur", "Client non trouvé.");
                    return;
                }

                DemandeData newData = new DemandeData();
                newData.setIdClient(idClient); // Utiliser l'ID du client récupéré
                newData.setIdPartenaire(idPartenaire); // Utiliser l'idPartenaire récupéré depuis la session
                newData.setTypeAide(typeAideComboBox.getValue()); // Utiliser la valeur sélectionnée dans la ComboBox
                newData.setDescription(descriptionField.getText());
                newData.setMontantDemande(Double.parseDouble(montantDemandeField.getText()));
                newData.setDateDemande(dateDemandePicker.getValue().atStartOfDay());
                newData.setStatus("en_attente"); // Définir le statut par défaut à "en attente"

                if (selectedFile != null) {
                    String fileName = saveFileToUploads(selectedFile);
                    newData.setPreuves(fileName);
                }

                demandeDao.insertDemande(newData);
                demandeDataList.add(newData);
                modal.close();
            } else {
                showAlert("Erreur", "Utilisateur courant non trouvé.");
            }
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
                clientNameField,
                typeAideComboBox, // Utiliser ComboBox pour le type d'aide
                descriptionField,
                montantDemandeField,
                dateDemandePicker,
                importFileButton, // Bouton d'importation
                importedFileLabel, // Label pour afficher le nom du fichier importé
                buttonBox // Bouton centré
        );
        Scene scene = new Scene(modalLayout, 400, 600);
        modal.setScene(scene);
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.show();
    }

    private String saveFileToUploads(File sourceFile) {
        String uploadsDir = "uploads";
        File uploadsDirectory = new File(uploadsDir);
        if (!uploadsDirectory.exists()) {
            uploadsDirectory.mkdirs(); // Crée le dossier s'il n'existe pas
        }

        // Générer un nom de fichier unique pour éviter les conflits
        String fileName = sourceFile.getName();
        File destFile = new File(uploadsDirectory, fileName);

        try {
            // Copier le fichier dans le dossier uploads
            Files.copy(sourceFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            // Retourner uniquement le nom du fichier
            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Échec de l'enregistrement du fichier.");
            return null;
        }
    }

    private void downloadFile(String fileName) {
        File file = new File("uploads/" + fileName);
        if (file.exists()) {
            // Open the file with the default application
            try {
                new ProcessBuilder("cmd", "/c", file.getAbsolutePath()).start();
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Erreur", "Échec de l'ouverture du fichier.");
            }
        } else {
            showAlert("Erreur", "Le fichier n'existe pas.");
        }
    }

    private void generateAndDownloadPdf(DemandeData data) {
        String formattedDate = data.getDateDemande().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String fileName = "dossier_" + data.getIdClient() + "_" + formattedDate + ".pdf";
        String uploadsDir = "uploads";
        File uploadsDirectory = new File(uploadsDir);
        if (!uploadsDirectory.exists()) {
            uploadsDirectory.mkdirs(); // Crée le dossier s'il n'existe pas
        }

        File pdfFile = new File(uploadsDirectory, fileName);
        try (FileOutputStream fos = new FileOutputStream(pdfFile)) {
            Document document = new Document();
            PdfWriter.getInstance(document, fos);
            document.open();

            // Ajouter le titre
            Paragraph title = new Paragraph("Dossier de Demande d'Aide", new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD));
            title.setSpacingAfter(20);
            document.add(title);

            // Ajouter les informations de la demande
            document.add(new Paragraph("ID Client: " + data.getIdClient()));
            document.add(new Paragraph("ID Partenaire: " + data.getIdPartenaire()));
            document.add(new Paragraph("Type d'Aide: " + data.getTypeAide()));
            document.add(new Paragraph("Description: " + data.getDescription()));
            document.add(new Paragraph("Montant de la Demande: " + data.getMontantDemande()));
            document.add(new Paragraph("Date de Demande: " + data.getDateDemande().toString()));
            document.add(new Paragraph("Status: " + data.getStatus()));

            document.close();
            showAlert("Succès", "Le fichier PDF a été généré et enregistré avec succès.");

            // Ouvrir automatiquement le fichier PDF généré
            if (pdfFile.exists()) {
                Desktop.getDesktop().open(pdfFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Échec de la génération du fichier PDF.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}