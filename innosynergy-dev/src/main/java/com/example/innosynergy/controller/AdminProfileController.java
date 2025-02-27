package com.example.innosynergy.controller;

import com.example.innosynergy.model.AdminProfileModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.time.LocalDate;

public class AdminProfileController {
    private AdminProfileModel adminProfileModel;

    @FXML
    private ImageView profileImage;
    @FXML
    private Circle clip;  // Ensure this is linked to your FXML
    @FXML
    private TextField nameField;
    @FXML
    private DatePicker birthField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField locationField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;

    public AdminProfileController() {
        // Initialize with some default data
        adminProfileModel = new AdminProfileModel("Ali Ben Salah", LocalDate.of(1986, 2, 15), "+216-55-698-714", "Tunisie", "AliBenSalah@gmail.com", "");
    }

    @FXML
    private void initialize() {
        profileImage.setImage(new Image("/images/user.png"));

        if (clip != null) {
            clip.setRadius(50.0);
        } else {
            System.out.println("clip is null");
        }

        nameField.setText(adminProfileModel.getName());
        birthField.setValue(adminProfileModel.getBirth());
        phoneField.setText(adminProfileModel.getPhone());
        locationField.setText(adminProfileModel.getLocation());
        emailField.setText(adminProfileModel.getEmail());
        passwordField.setText(adminProfileModel.getPassword());
        confirmPasswordField.setText(adminProfileModel.getPassword());
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
        adminProfileModel.setName(nameField.getText());
        adminProfileModel.setBirth(birthField.getValue());
        adminProfileModel.setPhone(phoneField.getText());
        adminProfileModel.setLocation(locationField.getText());
        showAlert("Succès", "Informations personnelles mises à jour avec succès.");
    }

    @FXML
    private void saveSecurityInfo() {
        if (passwordField.getText().equals(confirmPasswordField.getText())) {
            adminProfileModel.setEmail(emailField.getText());
            adminProfileModel.setPassword(passwordField.getText());
            showAlert("Succès", "Informations de sécurité mises à jour avec succès.");
        } else {
            showAlert("Erreur", "Les mots de passe ne correspondent pas. Veuillez vérifier et réessayer.");
        }
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

    public AdminProfileModel getAdminProfileModel() {
        return adminProfileModel;
    }

    public Image getProfileImage() {
        return new Image("/images/user.png");
    }

}