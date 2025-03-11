package com.example.innosynergy;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class Partner extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root = FXMLLoader.load(getClass().getResource("/MiraVia/PartnerView.fxml"));
        Scene scene = new Scene(root);

        primaryStage.setTitle("Tableau de bord avec BootstrapFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}