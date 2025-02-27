package com.example.innosynergy.view;

import com.example.innosynergy.controller.DashboardController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class Dashboard extends StackPane {

    private DashboardController controller;

    public Dashboard() {
        controller = new DashboardController();

        // Création du label "Statistiques"
        Label statsLabel = new Label("Statistiques");
        statsLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: #05004E;");

        HBox statsBox = new HBox(20);
        statsBox.setPadding(new Insets(10));
        statsBox.getChildren().addAll(
                controller.createStatBox("1K", "Nombre de clients", "+15 hier", "#FFCDD2", "#E57373"),
                controller.createStatBox("300", "Nombre de partenaires", "+25 hier", "#FFE0B2", "#FFB74D"),
                controller.createStatBox("80", "Nombre d'évènements", "+12 hier", "#C8E6C9", "#81C784"),
                controller.createStatBox("20", "Nombre de demandes d'aide", "+5 hier", "#E1BEE7", "#BA68C8"),
                controller.createStatBox("2K", "Nombre de visiteurs", "+25 hier", "#BBDEFB", "#64B5F6")
        );

        VBox statsContainer = new VBox(10, statsLabel, statsBox);
        statsContainer.setStyle("-fx-background-color: white; -fx-background-radius: 20px; -fx-padding: 20px;");
        statsContainer.setPrefSize(1075, 370);

        Label satisfactionLabel = new Label("Satisfaction des clients");
        satisfactionLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: #05004E;");

        VBox graphContainer = new VBox(10, satisfactionLabel, controller.createLineChart());
        graphContainer.setStyle("-fx-background-color: white; -fx-background-radius: 20px; -fx-padding: 10px;");
        graphContainer.setPrefSize(600, 800);

        HBox eventsAndChartContainer = new HBox(20, controller.createEventTableContainer(), controller.createPieChart());
        eventsAndChartContainer.setPadding(new Insets(10));
        eventsAndChartContainer.setStyle("-fx-background-color: white; -fx-background-radius: 10px;");
        eventsAndChartContainer.setPrefSize(1075, 400);

        VBox mainLayout = new VBox(20);
        mainLayout.setPadding(new Insets(20));
        mainLayout.getChildren().addAll(statsContainer, graphContainer, eventsAndChartContainer);

        // Charger l'image du chatbot
        Image chatbotImage = new Image("images/pngtree-smart-chatbot-cartoon-clipart-png-image_9015126-removebg-preview.png"); // Ajuster le chemin si nécessaire
        ImageView chatbotImageView = new ImageView(chatbotImage);
        chatbotImageView.setFitWidth(60);
        chatbotImageView.setFitHeight(60);
        chatbotImageView.setPreserveRatio(true);
        chatbotImageView.setStyle("-fx-padding: 0, 0 ,0 ,100px;");

        // Appliquer une transition de rotation lorsque l'image du chatbot est cliquée
        chatbotImageView.setOnMouseClicked(e -> {
            controller.openChatbot();
        });

        // Ajouter un espace sous le chatbot
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Conteneur du bouton contenant le chatbot
        HBox buttonContainer = new HBox(10, chatbotImageView, spacer);
        buttonContainer.setAlignment(Pos.CENTER_RIGHT);
        buttonContainer.setPadding(new Insets(10));

        // Ajouter l'image du chatbot et l'espace sous l'image dans la disposition principale
        mainLayout.getChildren().addAll(buttonContainer);

        this.getChildren().addAll(mainLayout);
    }
}