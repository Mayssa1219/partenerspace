package com.example.innosynergy.controller;

import com.example.innosynergy.dao.UserDaoImpl;
import com.example.innosynergy.utils.EmailUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

public class ForgotPasswordController {

    @FXML
    private TextField emailField;
    @FXML
    private ImageView leftImage;
    @FXML
    private ImageView logoImage;
    @FXML
    private ImageView vectorImage;

    @FXML
    private Label loginPageLabel;

    private UserDaoImpl userDAO = new UserDaoImpl();

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

    @FXML
    public void handleLoginPage() {
        try {
            Parent loginPage = FXMLLoader.load(getClass().getResource("/MiraVia/LoginView.fxml"));
            Scene loginScene = new Scene(loginPage);
            Stage primaryStage = (Stage) loginPageLabel.getScene().getWindow();
            primaryStage.setScene(loginScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleResetPasswordButtonAction() {
        String email = emailField.getText();
        if (email == null || email.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez entrer votre adresse e-mail.");
            return;
        }

        // Check if the email exists
        if (!userDAO.isEmailRegistered(email)) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "L'adresse e-mail n'est pas enregistrée.");
            return;
        }

        // Call the method to send the reset password email
        boolean emailSent = sendResetPasswordEmail(email);
        if (emailSent) {
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Un e-mail de réinitialisation de mot de passe a été envoyé.");
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite lors de l'envoi de l'e-mail.");
        }
    }

    private boolean sendResetPasswordEmail(String email) {
        try (InputStream inputStream = getClass().getResourceAsStream("/MiraVia/EmailTemplate/reset_password_email.html")) {
            if (inputStream == null) {
                System.out.println("Template non trouvé !");
                return false;
            }
            String template = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

            // Generate a new random password
            String newPassword = generateRandomPassword(10);

            // Update the user's password in the database
            boolean isPasswordUpdated = userDAO.updatePassword(email, newPassword);
            if (!isPasswordUpdated) {
                return false;
            }

            // Fetch the user's name using the email
            String nom = userDAO.findNameByEmail(email);
            if (nom == null) {
                return false;
            }

            // Create the email content with the user's name
            String emailContent = template.replace("${username}", nom)
                    .replace("${newPassword}", newPassword);
            return EmailUtil.sendEmail(email, "Réinitialisation de mot de passe", emailContent);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String generateRandomPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }
        return password.toString();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}