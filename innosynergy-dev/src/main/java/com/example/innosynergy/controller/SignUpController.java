package com.example.innosynergy.controller;

import com.example.innosynergy.dao.UserDao;
import com.example.innosynergy.dao.UserDaoImpl;
import com.example.innosynergy.model.SignUpModel;
import com.example.innosynergy.model.User;
import com.example.innosynergy.utils.CaptchaGenerator;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.io.IOException;

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
    private ImageView captchaImageView;
    @FXML
    private TextField captchaField;
    @FXML
    private Label LoginLabel;

    private SignUpModel model;
    private String currentCaptcha;
    private UserDao userDao;

    public void initialize() {
        this.model = new SignUpModel();
        this.userDao = new UserDaoImpl(); // Initialize userDao
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
                try {
                    User user = new User(
                            0, // idUtilisateur
                            model.getLastName(), // nom
                            model.getFirstName(), // prenom
                            model.getEmail(), // email
                            model.getPassword(), // motDePasse
                            model.getPhone(), // telephone
                            null, // avatar
                            "en attente", // statutVerification
                            "actif", // status
                            "partenaire" // type_utilisateur
                    );
                    userDao.registerUser(user);
                    showAlert(Alert.AlertType.INFORMATION, "Succès", "Inscription réussie !");
                } catch (Exception e) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'inscription.");
                    e.printStackTrace();
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur CAPTCHA", "Le code CAPTCHA est incorrect.");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur de validation", "Veuillez remplir tous les champs obligatoires correctement.");
        }
    }

    @FXML
    private void handleRefreshCaptcha() {
        generateCaptcha();
    }

    private boolean validateInput() {
        if (firstNameField.getText() == null || firstNameField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur de validation", "Le champ 'Prénom' est vide.");
            return false;
        }
        if (lastNameField.getText() == null || lastNameField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur de validation", "Le champ 'Nom de famille' est vide.");
            return false;
        }
        if (emailField.getText() == null || emailField.getText().isEmpty() || !emailField.getText().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            showAlert(Alert.AlertType.ERROR, "Erreur de validation", "Le format de l'adresse e-mail est incorrect.");
            return false;
        }
        if (phoneField.getText() == null || !phoneField.getText().matches("^\\+?[0-9\\s]{7,15}$")) {
            showAlert(Alert.AlertType.ERROR, "Erreur de validation", "Le format du numéro de portable est incorrect.");
            return false;
        }
        if (passwordField.getText() == null || !passwordField.getText().matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
            showAlert(Alert.AlertType.ERROR, "Erreur de validation", "Le mot de passe doit contenir au moins 8 caractères, dont une majuscule, une minuscule, un chiffre et un caractère spécial.");
            return false;
        }
        if (confirmPasswordField.getText() == null || confirmPasswordField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur de validation", "Le champ 'Confirmer le mot de passe' est vide.");
            return false;
        }
        if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            showAlert(Alert.AlertType.ERROR, "Erreur de validation", "Les mots de passe ne correspondent pas.");
            return false;
        }
        if (addressField.getText() == null || addressField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur de validation", "Le champ 'Adresse' est vide.");
            return false;
        }
        if (!termsCheckBox.isSelected()) {
            showAlert(Alert.AlertType.ERROR, "Erreur de validation", "Vous devez accepter les conditions générales et la politique de confidentialité.");
            return false;
        }
        return true;
    }

    @FXML
    public void handleLoginLinkClick() {
        try {
            Parent signUpView = FXMLLoader.load(getClass().getResource("/MiraVia/LoginView.fxml"));
            Scene signUpScene = new Scene(signUpView);
            Stage primaryStage = (Stage) LoginLabel.getScene().getWindow();
            primaryStage.setScene(signUpScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean validateCaptcha() {
        return currentCaptcha.equals(captchaField.getText());
    }

    private void generateCaptcha() {
        currentCaptcha = CaptchaGenerator.generateCaptchaText();
        BufferedImage captchaImage = CaptchaGenerator.generateCaptchaImage(currentCaptcha);
        Image fxImage = SwingFXUtils.toFXImage(captchaImage, null);
        captchaImageView.setImage(fxImage);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}