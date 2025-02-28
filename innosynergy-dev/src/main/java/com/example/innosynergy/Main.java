package com.example.innosynergy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/MiraVia/PartnerLayout.fxml"));
        primaryStage.setTitle("Miravia Partner Layout");
        primaryStage.setScene(new Scene(root, 1100, 700));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}