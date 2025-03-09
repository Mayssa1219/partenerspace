package com.example.innosynergy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Auth extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/MiraVia/LoginView.fxml"));
        primaryStage.setTitle("Miravia Authentification Page");
        primaryStage.setScene(new Scene(root, 1100, 700));
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
