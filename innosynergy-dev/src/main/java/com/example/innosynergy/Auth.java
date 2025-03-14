package com.example.innosynergy;

import com.example.innosynergy.controller.NotificationBarController;
import com.example.innosynergy.utils.SessionManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Auth extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the NotificationBarController
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MiraVia/NotificationBarView.fxml"));
        Parent notificationRoot = loader.load();
        NotificationBarController notificationBarController = loader.getController();

        // Initialize the SessionManager with the NotificationBarController
        SessionManager.initialize(notificationBarController);

        // Load the login view
        Parent root = FXMLLoader.load(getClass().getResource("/MiraVia/LoginView.fxml"));
        primaryStage.setTitle("Miravia Plateforme");
        primaryStage.setScene(new Scene(root, 1100, 700));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}