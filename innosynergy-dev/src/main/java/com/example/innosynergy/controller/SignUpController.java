package com.example.innosynergy.controller;

import com.example.innosynergy.model.SignUpModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

import java.util.Random;

public class SignUpController {
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private TextField addressField;

    @FXML
    private CheckBox termsCheckBox;
    @FXML
    private CheckBox updatesCheckBox;
    @FXML
    private Button registerButton;
    @FXML
    private Label captchaLabel;
    @FXML
    private TextField captchaField;

    private SignUpModel model;
    private String currentCaptcha;

    public void setModel(SignUpModel model) {
        this.model = model;
        bindModel();
        generateCaptcha();
    }

    private void bindModel() {
        firstNameField.textProperty().bindBidirectional(model.firstNameProperty());
        lastNameField.textProperty().bindBidirectional(model.lastNameProperty());
        emailField.textProperty().bindBidirectional(model.emailProperty());
        phoneField.textProperty().bindBidirectional(model.phoneProperty());
        passwordField.textProperty().bindBidirectional(model.passwordProperty());
        confirmPasswordField.textProperty().bindBidirectional(model.confirmPasswordProperty());
        addressField.textProperty().bindBidirectional(model.addressProperty());
    }



    @FXML
    private void handleRegisterButtonAction() {
        if (validateInput()) {
            if (validateCaptcha()) {
                showAlert(AlertType.INFORMATION, "Succès", "Inscription réussie !");
            } else {
                showAlert(AlertType.ERROR, "Erreur CAPTCHA", "Le code CAPTCHA est incorrect.");
            }
        } else {
            showAlert(AlertType.ERROR, "Erreur de validation", "Veuillez remplir tous les champs obligatoires correctement.");
        }
    }

    @FXML
    private void handleRefreshCaptcha() {
        generateCaptcha();
    }

    private boolean validateInput() {
        if (firstNameField.getText().isEmpty()) {
            showAlert(AlertType.ERROR, "Erreur de validation", "Le champ 'Prénom' est vide.");
            return false;
        }
        if (lastNameField.getText().isEmpty()) {
            showAlert(AlertType.ERROR, "Erreur de validation", "Le champ 'Nom de famille' est vide.");
            return false;
        }
        if (!emailField.getText().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            showAlert(AlertType.ERROR, "Erreur de validation", "Le format de l'adresse e-mail est incorrect.");
            return false;
        }

        if (!phoneField.getText().matches("^\\+?[0-9\\s]{7,15}$")) {
            showAlert(AlertType.ERROR, "Erreur de validation", "Le format du numéro de portable est incorrect.");
            return false;
        }

        if (!passwordField.getText().matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
            showAlert(AlertType.ERROR, "Erreur de validation", "Le mot de passe doit contenir au moins 8 caractères, dont une majuscule, une minuscule, un chiffre et un caractère spécial.");
            return false;
        }

        if (confirmPasswordField.getText().isEmpty()) {
            showAlert(AlertType.ERROR, "Erreur de validation", "Le champ 'Confirmer le mot de passe' est vide.");
            return false;
        }
        if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            showAlert(AlertType.ERROR, "Erreur de validation", "Les mots de passe ne correspondent pas.");
            return false;
        }
        if (addressField.getText().isEmpty()) {
            showAlert(AlertType.ERROR, "Erreur de validation", "Le champ 'Adresse' est vide.");
            return false;
        }
        if (!termsCheckBox.isSelected()) {
            showAlert(AlertType.ERROR, "Erreur de validation", "Vous devez accepter les conditions générales et la politique de confidentialité.");
            return false;
        }
        return true;
    }

    private boolean validateCaptcha() {
        return currentCaptcha.equals(captchaField.getText());
    }

    private void generateCaptcha() {
        currentCaptcha = generateRandomCaptcha();
        captchaLabel.setText(currentCaptcha);
    }

    private String generateRandomCaptcha() {
        Random random = new Random();
        int length = 6;
        StringBuilder captcha = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int rand = random.nextInt(62);
            char base = (rand < 10) ? '0' : (char) ((rand < 36) ? 'A' - 10 : 'a' - 36);
            captcha.append((char) (base + rand));
        }
        return captcha.toString();
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}