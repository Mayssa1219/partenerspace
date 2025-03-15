package com.example.innosynergy.controller;

import com.example.innosynergy.dao.UserDao;
import com.example.innosynergy.dao.UserDaoImpl;
import com.example.innosynergy.model.LoginModel;
import com.example.innosynergy.model.User;
import com.example.innosynergy.utils.SessionManager;
import com.google.api.client.auth.oauth2.Credential;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;

import com.google.api.client.json.jackson2.JacksonFactory;

import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfo;
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

import java.util.Arrays;
import java.util.Collections;
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
        try {
            Parent signUpView = FXMLLoader.load(getClass().getResource("/MiraVia/forgotpassword.fxml"));
            Scene signUpScene = new Scene(signUpView);
            Stage primaryStage = (Stage) signUpLabel.getScene().getWindow();
            primaryStage.setScene(signUpScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
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



    private static final String CLIENT_ID = "30208622387-rgen8ucrbgd8ive36b84ak3cdgps9eku.apps.googleusercontent.com";
    private static final String CLIENT_SECRET = "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";
    private static final String REDIRECT_URI = "http://localhost:8888/Callback";

    @FXML
    private void handleGoogleLoginButtonAction() {
        try {
            GoogleClientSecrets clientSecrets = new GoogleClientSecrets()
                    .setInstalled(new GoogleClientSecrets.Details()
                            .setClientId(CLIENT_ID)
                            .setClientSecret(CLIENT_SECRET));

            GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    JacksonFactory.getDefaultInstance(),
                    clientSecrets,
                    Arrays.asList("https://www.googleapis.com/auth/userinfo.profile", "https://www.googleapis.com/auth/userinfo.email"))
                    .setAccessType("offline")
                    .build();

            LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
            Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");

            Oauth2 oauth2 = new Oauth2.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    JacksonFactory.getDefaultInstance(),
                    credential)
                    .setApplicationName("MirVia Application")
                    .build();

            Userinfo userInfo = oauth2.userinfo().get().execute();
            System.out.println("User ID: " + userInfo.getId());
            System.out.println("User Name: " + userInfo.getName());
            System.out.println("User Email: " + userInfo.getEmail());

            // Handle the authenticated user information
            handleAuthenticatedUser(userInfo);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleAuthenticatedUser(Userinfo userInfo) {
        System.out.println("Authenticated User Email: " + userInfo.getEmail());

        // Recherche de l'utilisateur dans la base de données via l'email
        User user = userDao.getUserByEmail(userInfo.getEmail());

        if (user != null) {
            // L'utilisateur a été trouvé, on peut récupérer son ID
            System.out.println("Utilisateur trouvé avec ID: " + user.getIdUtilisateur());

            // On définit l'ID utilisateur comme celui récupéré
            user.setNom(userInfo.getName());
            user.setEmail(userInfo.getEmail());
            user.setTypeUtilisateur("partenaire");

            // Création de la session
            SessionManager.createSession(user);

            // Debug pour voir si la session est bien créée
            System.out.println("Session créée avec ID: " + SessionManager.getCurrentSessionId());

            // Redirection
            redirectToMainPage();
        } else {
            System.out.println("Aucun utilisateur trouvé pour cet email.");
            showAlert(Alert.AlertType.ERROR, "Erreur", "Utilisateur non trouvé.");
        }
    }




    private void redirectToMainPage() {
        try {
            String sessionId = SessionManager.getCurrentSessionId();
            System.out.println("Session ID récupéré: " + sessionId); // Debug

            User user = SessionManager.getUser(sessionId);
            System.out.println("Utilisateur récupéré: " + user); // Debug

            if (user != null) {
                String userType = user.getTypeUtilisateur();
                System.out.println("Type d'utilisateur: " + userType); // Debug

                String fxmlFile = "/MiraVia/PartnerView.fxml";//Page par défaut
                if ("partenaire".equals(userType)) {
                    fxmlFile = "/MiraVia/PartnerView.fxml";
                }

                Parent mainView = FXMLLoader.load(getClass().getResource(fxmlFile));
                Scene mainScene = new Scene(mainView);
                Stage primaryStage = (Stage) loginButton.getScene().getWindow();
                primaryStage.setScene(mainScene);
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Aucune session utilisateur trouvée.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite lors du chargement de la vue.");
        }
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