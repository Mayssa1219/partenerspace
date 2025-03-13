package com.example.innosynergy.controller;

import com.example.innosynergy.dao.DonDao;
import com.example.innosynergy.dao.DonDaoImpl;
import com.example.innosynergy.model.Don;
import com.example.innosynergy.utils.SessionManager;
import com.example.innosynergy.model.User;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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

    private DonDao donDao;

    public DonController() {
        this.donDao = new DonDaoImpl();
    }

    @FXML
    private void initialize() {
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
        String sessionId = SessionManager.getCurrentSessionId();
        Stage stage = new Stage();
        VBox vbox = new VBox();

        TextField clientNameField = new TextField();
        clientNameField.setPromptText("Nom Client");

        TextField montantField = new TextField();
        montantField.setPromptText("Montant");

        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("Date de Don");

        Button addButton = new Button("Ajouter");
        addButton.setOnAction(e -> {
            String clientName = clientNameField.getText();
            BigDecimal montant = new BigDecimal(montantField.getText());
            LocalDateTime dateDon = datePicker.getValue().atStartOfDay();

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
                loadDons();
                stage.close();
            }
        });

        vbox.getChildren().addAll(clientNameField, montantField, datePicker, addButton);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new javafx.scene.Scene(vbox, 300, 200));
        stage.show();
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