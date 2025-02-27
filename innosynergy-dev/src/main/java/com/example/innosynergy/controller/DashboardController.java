package com.example.innosynergy.controller;

import com.example.innosynergy.model.Event;
import com.example.innosynergy.view.Chatbot;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class DashboardController {

    public VBox createStatBox(String value, String description, String change, String bgColor, String textColor) {
        Label valueLabel = new Label(value);
        valueLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: " + textColor + ";");

        Label descriptionLabel = new Label(description);
        descriptionLabel.setStyle("-fx-text-fill: #757575;");

        Label changeLabel = new Label(change);
        changeLabel.setStyle("-fx-text-fill: " + textColor + ";");

        VBox statBox = new VBox(5);
        statBox.setStyle("-fx-background-color: " + bgColor + "; -fx-padding: 10px; -fx-alignment: center; -fx-background-radius: 16px;");
        statBox.setPrefSize(180, 180);
        statBox.getChildren().addAll(valueLabel, descriptionLabel, changeLabel);

        return statBox;
    }

    public LineChart<Number, Number> createLineChart() {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);

        XYChart.Series<Number, Number> series1 = new XYChart.Series<>();
        series1.setName("Mois dernier");
        series1.getData().add(new XYChart.Data<>(1, 20));
        series1.getData().add(new XYChart.Data<>(2, 30));
        series1.getData().add(new XYChart.Data<>(3, 40));
        series1.getData().add(new XYChart.Data<>(4, 50));

        XYChart.Series<Number, Number> series2 = new XYChart.Series<>();
        series2.setName("Mois actuel");
        series2.getData().add(new XYChart.Data<>(1, 25));
        series2.getData().add(new XYChart.Data<>(2, 35));
        series2.getData().add(new XYChart.Data<>(3, 45));
        series2.getData().add(new XYChart.Data<>(4, 55));

        lineChart.getData().addAll(series1, series2);

        // Modifier les couleurs des lignes
        series1.getNode().setStyle("-fx-stroke: #FFCDD2;");  // Couleur de la première série
        series2.getNode().setStyle("-fx-stroke: #E1BEE7;");  // Couleur de la deuxième série

        return lineChart;
    }

    public PieChart createPieChart() {
        PieChart pieChart = new PieChart();
        pieChart.getData().addAll(
                new PieChart.Data("Europe", 30),
                new PieChart.Data("Asie", 40),
                new PieChart.Data("Afrique", 30)
        );

        // Modifications des couleurs des secteurs du pie chart
        for (PieChart.Data data : pieChart.getData()) {
            if (data.getName().equals("Europe")) {
                data.getNode().setStyle("-fx-fill: #FFCDD2;"); // Correspond à la couleur de StatBox
            } else if (data.getName().equals("Asie")) {
                data.getNode().setStyle("-fx-fill: #FFE0B2;"); // Correspond à la couleur de StatBox
            } else if (data.getName().equals("Afrique")) {
                data.getNode().setStyle("-fx-fill: #C8E6C9;"); // Correspond à la couleur de StatBox
            }
        }

        pieChart.setPrefWidth(400);
        pieChart.setPrefHeight(400);

        return pieChart;
    }

    public Rectangle createProgressBar(String color, double progress) {
        Rectangle rect = new Rectangle(100 * progress, 10);
        rect.setFill(Color.web(color)); // Utilisation de la couleur dynamique pour la barre de progression
        rect.setArcWidth(10);  // Ajouter un border-radius (arrondir les coins)
        rect.setArcHeight(10); // Ajouter un border-radius (arrondir les coins)
        return rect;
    }

    public void openChatbot() {
        Chatbot chatbot = new Chatbot();
        Stage chatbotStage = new Stage();
        chatbotStage.setTitle("Chatbot");
        chatbotStage.setScene(new Scene(chatbot, 350, 450));
        chatbotStage.show();
    }

    public VBox createEventTableContainer() {
        VBox tableContainer = new VBox();
        tableContainer.setStyle("-fx-background-color: white; -fx-background-radius: 20px; -fx-padding: 0px;");

        TableView<Event> table = createEventTable();

        tableContainer.getChildren().add(table);
        tableContainer.setPrefWidth(1000);
        tableContainer.setPrefHeight(900);
        return tableContainer;
    }

    public TableView<Event> createEventTable() {
        TableView<Event> table = new TableView<>();

        // Colonne pour le numéro
        TableColumn<Event, String> numberCol = new TableColumn<>("#");
        numberCol.setCellValueFactory(new PropertyValueFactory<>("number"));

        // Colonne pour le titre
        TableColumn<Event, String> titleCol = new TableColumn<>("Titre");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

        // Colonne pour la description
        TableColumn<Event, String> descCol = new TableColumn<>("Description");
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        // Colonne pour la date de l'événement
        TableColumn<Event, String> dateCol = new TableColumn<>("Date de l'événement");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("dateEvenement"));

        // Colonne pour le lieu
        TableColumn<Event, String> placeCol = new TableColumn<>("Lieu");
        placeCol.setCellValueFactory(new PropertyValueFactory<>("place"));

        // Colonne pour le partenaire
        TableColumn<Event, String> partnerCol = new TableColumn<>("Partenaire");
        partnerCol.setCellValueFactory(new PropertyValueFactory<>("partner"));

        // Colonne pour le statut
        TableColumn<Event, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Ajouter les colonnes à la table
        table.getColumns().addAll(numberCol, titleCol, descCol, dateCol, placeCol, partnerCol, statusCol);

        // Ajouter des données à la table
        table.getItems().addAll(
                new Event("1", "Conférence sur l'IA", "Description de la conférence sur l'IA", "2025-02-16 10:00:00", "Paris", "Partenaire A", "actif"),
                new Event("2", "Séminaire sur la Data", "Description du séminaire sur la Data", "2025-02-17 11:00:00", "Lyon", "Partenaire B", "annulé"),
                new Event("3", "Atelier de développement", "Description de l'atelier de développement", "2025-02-18 09:00:00", "Marseille", "Partenaire C", "terminé")
        );

        return table;
    }
}