package com.example.innosynergy;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class Partner extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MiraVia/PartnerView.fxml"));
            Parent root = loader.load();
            primaryStage.setTitle("Tableau de bord Partenaire");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement du fichier FXML.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
