package com.example.innosynergy.controller;

import com.example.innosynergy.model.PartenaireProfileModel;
import com.example.innosynergy.dao.PartenaireDaoImpl;
import com.example.innosynergy.dao.UserDaoImpl;
import com.example.innosynergy.model.User;
import com.example.innosynergy.utils.FileUtil;
import com.example.innosynergy.utils.SessionManager;
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
import java.io.IOException;

public class PartenaireProfileController {
    private PartenaireProfileModel partenaireprofile;
    private PartenaireDaoImpl partenaireDao;
    private UserDaoImpl userDao;

    @FXML
    private ImageView profileImage;
    @FXML
    private Circle clip;
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
        partenaireDao = new PartenaireDaoImpl();
        userDao = new UserDaoImpl();
        String sessionId = SessionManager.getCurrentSessionId();
        User user = SessionManager.getUser(sessionId);
        if (user != null) {
            partenaireprofile = partenaireDao.getPartenaireByUserId(user.getIdUtilisateur());
        }
    }

    @FXML
    private void initialize() {
        profileImage.setImage(new Image("/images/user.png"));
        if (clip != null) {
            clip.setRadius(50.0);
        } else {
            System.out.println("clip is null");
        }
        if (partenaireprofile != null) {
            nameField.setText(partenaireprofile.getNomEntreprise());
            phoneField.setText(partenaireprofile.getTelephone());
            locationField.setText(partenaireprofile.getAdresse());
            siteWebField.setText(partenaireprofile.getSiteWeb());
        }
        emailField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
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
            if (partenaireprofile != null) {
                updateProfileImage(selectedFile, partenaireprofile.getIdPartenaire());
            }
        }
    }

    @FXML
    private void savePersonalInfo() {
        if (partenaireprofile != null) {
            partenaireprofile.setNomEntreprise(nameField.getText());
            partenaireprofile.setTelephone(phoneField.getText());
            partenaireprofile.setAdresse(locationField.getText());
            partenaireprofile.setSiteWeb(siteWebField.getText());
            partenaireDao.updatePartenaire(partenaireprofile);
            showAlert("Succès", "Informations personnelles mises à jour avec succès.");
        }
    }

    @FXML
    private void saveSecurityInfo() {
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (!password.equals(confirmPassword)) {
            showAlert("Erreur", "Les mots de passe ne correspondent pas.");
            return;
        }

        User user = new User();
        user.setEmail(email);
        user.setMotDePasse(password);
        userDao.updatePassword(email, password);
        showAlert("Succès", "Informations de sécurité mises à jour avec succès.");
    }

    private void updateProfileImage(File sourceFile, int idPartenaire) {
        try {
            String newImageName = FileUtil.saveFile(sourceFile);
            PartenaireProfileModel partenaireprofile = partenaireDao.getPartenaireById(idPartenaire);
            if (partenaireprofile != null) {
                System.out.println("New image name: " + newImageName);
                partenaireprofile.setAvatar(newImageName); // Save the file name in the database
                partenaireDao.updatePartenaire(partenaireprofile);
                showAlert("Succès", "Image de profil mise à jour avec succès.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Échec de la mise à jour de l'image de profil.");
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