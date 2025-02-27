package com.example.innosynergy.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;

public class parameter extends StackPane {

    public parameter() {
        // Création de l'image
        Image image = new Image("images/Frame.png"); // Remplace par le chemin correct de l'image
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(200);  // Ajuste la largeur de l'image
        imageView.setFitHeight(200); // Ajuste la hauteur de l'image

        // Création du texte
        Label dashboardLabel = new Label("Parameters!");
        dashboardLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: #013A71;");

        // Ajouter l'image et le texte dans le StackPane
        this.getChildren().addAll(imageView, dashboardLabel);
    }
}
