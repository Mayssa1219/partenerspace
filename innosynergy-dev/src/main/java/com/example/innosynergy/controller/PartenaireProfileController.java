package com.example.innosynergy.controller;

import com.example.innosynergy.model.PartenaireProfileModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Date;

public class PartenaireProfileController {
    private PartenaireProfileModel partenaireprofile;

    @FXML
    private ImageView profileImage;
    @FXML
    private Circle clip;  // Ensure this is linked to your FXML
    @FXML
    private Label userNameLabel;
    @FXML
    private Label locationLabel;
    @FXML
    private TextField nameField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField locationField;
    @FXML
    private TextField siteWebField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;

    public PartenaireProfileController() {
        // Initialize with some default data
        partenaireprofile = new PartenaireProfileModel(1, "TravelBooking", "Agence","www.example.com", "Tunisie", "+216-55-698-714", "", 1, new Date());
    }

    @FXML
    private void initialize() {
        profileImage.setImage(new Image("/images/user.png"));

        if (clip != null) {
            clip.setRadius(50.0);
        } else {
            System.out.println("clip is null");
        }

        // Initialize UI with model data
        userNameLabel.setText(partenaireprofile.getNomEntreprise());
        locationLabel.setText(partenaireprofile.getAdresse());
        nameField.setText(partenaireprofile.getNomEntreprise());
        phoneField.setText(partenaireprofile.getTelephone());
        locationField.setText(partenaireprofile.getAdresse());
        siteWebField.setText(partenaireprofile.getSiteWeb());
        emailField.setText(""); // Assuming email is not part of PartenaireProfileModel
        passwordField.setText(""); // Assuming password is not part of PartenaireProfileModel
        confirmPasswordField.setText(""); // Assuming password is not part of PartenaireProfileModel
    }

    @FXML
    private void uploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            Image newImage = new Image(selectedFile.toURI().toString());
            profileImage.setImage(newImage);
            updateProfileImage(selectedFile.toURI().toString());
        }
    }

    @FXML
    private void savePersonalInfo() {
        partenaireprofile.setNomEntreprise(nameField.getText());
        partenaireprofile.setTelephone(phoneField.getText());
        partenaireprofile.setAdresse(locationField.getText());
        partenaireprofile.setSiteWeb(siteWebField.getText());
        showAlert("Succès", "Informations personnelles mises à jour avec succès.");
    }

    @FXML
    private void saveSecurityInfo() {
        // Assuming email and password are not part of PartenaireProfileModel
        showAlert("Succès", "Informations de sécurité mises à jour avec succès.");
    }

    private void updateProfileImage(String imagePath) {
        // Update the profile image path in the model
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public PartenaireProfileModel getPartenaireprofile() {
        return partenaireprofile;
    }

    public Image getProfileImage() {
        return new Image("/images/user.png");
    }
}