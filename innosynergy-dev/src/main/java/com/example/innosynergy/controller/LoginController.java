package com.example.innosynergy.controller;

import com.example.innosynergy.dao.UserDao;
import com.example.innosynergy.dao.UserDaoImpl;
import com.example.innosynergy.model.LoginModel;
import com.example.innosynergy.model.User;
import com.example.innosynergy.utils.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.regex.Pattern;

public class LoginController {
    private LoginModel model;

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ImageView leftImage;
    @FXML
    private ImageView logoImage;
    @FXML
    private ImageView vectorImage;
    @FXML
    private ImageView googleImage;
    @FXML
    private ImageView leftImageBottom;
    @FXML
    private ImageView rightImageBottom;
    @FXML
    private Button loginButton;
    @FXML
    private Button googleButton;
    @FXML
    private Label forgotPassword;
    @FXML
    private Label signUpLabel;
    @FXML
    private Label errorMessage;

    private UserDao userDao;

    public LoginController() {
        this.userDao = new UserDaoImpl();
        this.model = new LoginModel();
    }

    @FXML
    private void initialize() {
        loadImages();
        addButtonHoverEffect();
    }

    private void loadImages() {
        leftImage.setImage(new Image(getClass().getResource("/images/pexels-daniel-14373733 1 (1).png").toString()));
        logoImage.setImage(new Image(getClass().getResource("/images/logo miravia 1 3.png").toString()));
        vectorImage.setImage(new Image(getClass().getResource("/images/Vector.png").toString()));
        googleImage.setImage(new Image(getClass().getResource("/images/Google.png").toString()));
    }

    private void addButtonHoverEffect() {
        // Implement hover effect for buttons if needed
    }

    @FXML
    private void handleLoginButtonAction(javafx.event.ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (validateCredentials(email, password)) {
            handleLogin(email, password);
        }
    }

    private boolean validateCredentials(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            showError("Veuillez remplir tous les champs.");
            return false;
        }

        if (!isValidEmail(email)) {
            showError("Veuillez entrer une adresse email valide.");
            return false;
        }

        if (password.length() < 8) {
            showError("Le mot de passe doit contenir au moins 8 caractères.");
            return false;
        }

        hideError();
        return true;
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        return pat.matcher(email).matches();
    }

    private void showError(String message) {
        errorMessage.setText(message);
        errorMessage.setVisible(true);
    }

    private void hideError() {
        errorMessage.setVisible(false);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void handleForgotPassword() {
        String email = emailField.getText();
        if (isValidEmail(email)) {
            sendPasswordResetEmail(email);
            showError("Un email de réinitialisation du mot de passe a été envoyé.");
        } else {
            showError("Veuillez entrer une adresse email valide.");
        }
    }

    public void sendPasswordResetEmail(String email) {
        // Logic to send password reset email
        // This is a placeholder implementation
        System.out.println("Password reset email sent to: " + email);
    }

    @FXML
    public void handleSignUpLinkClick() {
        try {
            Parent signUpView = FXMLLoader.load(getClass().getResource("/MiraVia/signUpView.fxml"));
            Scene signUpScene = new Scene(signUpView);
            Stage primaryStage = (Stage) signUpLabel.getScene().getWindow();
            primaryStage.setScene(signUpScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleGoogleLoginButtonAction() {
        String googleLoginUrl = "https://accounts.google.com/o/oauth2/auth";
        userDao.initiateGoogleLogin(googleLoginUrl);
    }

    private void handleLogin(String email, String password) {
        User user = userDao.authenticateUser(email, password);
        if (user != null) {
            if ("actif".equals(user.getStatus())) {
                SessionManager.createSession(user);
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Connexion réussie !");
                // Redirect to the main application page based on user type
                try {
                    if ("partenaire".equals(user.getTypeUtilisateur())) {
                        Parent partenaireView = FXMLLoader.load(getClass().getResource("/MiraVia/PartnerView.fxml"));
                        Scene partenaireScene = new Scene(partenaireView);
                        Stage primaryStage = (Stage) loginButton.getScene().getWindow();
                        primaryStage.setScene(partenaireScene);
                    } else {
                        // Redirect to regular user dashboard
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite lors du chargement de la vue.");
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Votre compte n'est pas actif.");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Email ou mot de passe incorrect.");
        }
    }
}