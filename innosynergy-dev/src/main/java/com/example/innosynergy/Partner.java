package com.example.innosynergy;

<<<<<<< HEAD
=======

>>>>>>> 8a4e100a679ff778893b7ac0d57fb1a952eb76d5
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class Partner extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
<<<<<<< HEAD
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MiraVia/PartnerView.fxml"));
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root));
=======
        BorderPane root = FXMLLoader.load(getClass().getResource("/MiraVia/PartnerView.fxml"));
        Scene scene = new Scene(root);

        primaryStage.setTitle("Tableau de bord avec BootstrapFX");
        primaryStage.setScene(scene);
>>>>>>> 8a4e100a679ff778893b7ac0d57fb1a952eb76d5
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}