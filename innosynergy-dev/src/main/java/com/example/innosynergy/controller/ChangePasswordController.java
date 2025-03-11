package com.example.innosynergy.controller;

import com.example.innosynergy.dao.UserDaoImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ChangePasswordController {

    @FXML
    private PasswordField newPasswordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private ImageView leftImage;
    @FXML
    private ImageView logoImage;
    @FXML
    private ImageView vectorImage;

    private UserDaoImpl userDAO = new UserDaoImpl();
    private String email; // Define the email variable

    @FXML
    private void initialize() {
        loadImages();
    }

    @FXML
    private void loadImages() {
        leftImage.setImage(new Image(getClass().getResource("/images/pexels-daniel-14373733 1 (1).png").toString()));
        logoImage.setImage(new Image(getClass().getResource("/images/logo miravia 1 3.png").toString()));
        vectorImage.setImage(new Image(getClass().getResource("/images/Vector.png").toString()));
    }

    public void setEmail(String email) {
        this.email = email; // Set the email variable
    }

    @FXML
    private void handleResetPasswordButtonAction() {
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (newPassword == null || newPassword.isEmpty() || confirmPassword == null || confirmPassword.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez entrer et confirmer votre nouveau mot de passe.");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Les mots de passe ne correspondent pas.");
            return;
        }

        // Update the password in the database
        boolean passwordUpdated = userDAO.updatePassword(email, newPassword);
        if (passwordUpdated) {
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Votre mot de passe a été réinitialisé avec succès.");
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite lors de la réinitialisation du mot de passe.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}