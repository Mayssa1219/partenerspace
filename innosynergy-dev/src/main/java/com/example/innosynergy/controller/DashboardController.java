package com.example.innosynergy.controller;

import com.example.innosynergy.dao.DashboardDao;
import com.example.innosynergy.dao.DashboardDaoImpl;
import com.example.innosynergy.model.Event;
import com.example.innosynergy.utils.SessionManager;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.File;
import java.util.List;

public class DashboardController {

    @FXML
    private LineChart<String, Number> lineChart; // Correction du type

    @FXML
    private TableView<Event> eventTable;

    @FXML
    private TableColumn<Event, String> titleCol;

    @FXML
    private TableColumn<Event, String> descCol;

    @FXML
    private TableColumn<Event, String> dateCol;

    @FXML
    private TableColumn<Event, String> placeCol;

    @FXML
    private TableColumn<Event, String> imageCol;

    @FXML
    private TableColumn<Event, String> statusCol;

    @FXML
    private Label clientCountLabel;

    @FXML
    private Label helpRequestCountLabel;

    @FXML
    private Label eventCountLabel;

    @FXML
    private Label benevoleCountLabel;

    @FXML
    private AreaChart<String, Number> areaChart;

    private final DashboardDao dashboardDao = new DashboardDaoImpl();
    private int idPartenaire;

    @FXML
    public void initialize() {
        areaChart.getStylesheets().add(getClass().getResource("/MiraVia/styles/style.css").toExternalForm());
        createCustomLegend(); // Ajoute la légende après avoir chargé les données

        // Récupérer l'ID du partenaire courant
        String sessionId = SessionManager.getCurrentSessionId();
        idPartenaire = SessionManager.getUserId(sessionId);

        System.out.println("ID Partenaire: " + idPartenaire); // Debug

        // Charger les données
        loadDashboardData(idPartenaire);
    }

    private void loadDashboardData(int idPartenaire) {
        loadStatistics(idPartenaire);
        loadAreaChartData(); // Remplacer loadLineChartData par loadAreaChartData
        loadEventTableData(idPartenaire);
    }

    private void loadStatistics(int idPartenaire) {
        try {
            clientCountLabel.setText(String.valueOf(dashboardDao.getClientCount(idPartenaire)));
            helpRequestCountLabel.setText(String.valueOf(dashboardDao.getHelpRequestCountByPartenaire(idPartenaire)));
            eventCountLabel.setText(String.valueOf(dashboardDao.getEventCount(idPartenaire)));
            benevoleCountLabel.setText(String.valueOf(dashboardDao.getBenevoleCount(idPartenaire)));

            System.out.println("Statistiques mises à jour !");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private HBox legendBox; // Assurez-vous d'avoir un HBox dans votre FXML

    private void createCustomLegend() {
        legendBox.getChildren().clear(); // Nettoyer l'ancienne légende

        legendBox.getChildren().addAll(
                createLegendItem("Dons financières", "#FF6384"),   // Rouge Rosé
                createLegendItem("Dons non financières", "#FFCE56") // Jaune Clair
        );
    }

    private HBox createLegendItem(String text, String color) {
        Label colorBox = new Label(" ");
        colorBox.setMinSize(12, 12);
        colorBox.setStyle("-fx-background-color: " + color);

        Label label = new Label(text);
        label.setStyle("-fx-font-size: 14px; -fx-padding: 5px;");

        HBox legendItem = new HBox(5, colorBox, label);
        legendItem.setAlignment(Pos.CENTER_LEFT);
        return legendItem;
    }



    private void loadLineChartData() {
        try {
            // Nettoyer l'ancien graphique
            lineChart.getData().clear();

            // Créer une série
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Évolution des inscriptions");

            List<XYChart.Data<String, Number>> data = dashboardDao.getLineChartData();
            series.getData().addAll(data);

            // Ajouter la série au graphique
            lineChart.getData().add(series);

            System.out.println("Données du graphique mises à jour !");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadAreaChartData() {
        try {
            // Nettoyer l'ancien graphique
            areaChart.getData().clear();

            // Récupérer les données de dons par mois depuis le DAO
            List<XYChart.Data<Number, Number>> donationsData = dashboardDao.getDonationsByMonth(idPartenaire);

            // Créer une série de données pour les dons
            XYChart.Series<String, Number> seriesDons = new XYChart.Series<>();
            seriesDons.setName("Dons par mois");

            // Convertir les données en format compatible avec l'AreaChart
            String[] moisNoms = {"Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"};
            for (XYChart.Data<Number, Number> data : donationsData) {
                int mois = data.getXValue().intValue();
                int nombreDons = data.getYValue().intValue();
                seriesDons.getData().add(new XYChart.Data<>(moisNoms[mois - 1], nombreDons));
            }

            // Ajouter la série au graphique
            areaChart.getData().add(seriesDons);

            // Ajouter une classe CSS à la série
            seriesDons.getNode().getStyleClass().add("dons");

            System.out.println("Graphique AreaChart mis à jour avec les dons par mois !");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void loadEventTableData(int idPartenaire) {
        try {
            // Configurer les colonnes
            titleCol.setCellValueFactory(new PropertyValueFactory<>("titre"));
            descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            dateCol.setCellValueFactory(new PropertyValueFactory<>("dateEvenement"));
            placeCol.setCellValueFactory(new PropertyValueFactory<>("lieu"));
            statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

            // Gestion des images
            imageCol.setCellValueFactory(new PropertyValueFactory<>("imageName"));
            imageCol.setCellFactory(column -> new TableCell<Event, String>() {
                private final ImageView imageView = new ImageView();

                @Override
                protected void updateItem(String imageName, boolean empty) {
                    super.updateItem(imageName, empty);
                    if (empty || imageName == null) {
                        setGraphic(null);
                    } else {
                        File file = new File("uploads/" + imageName);
                        if (file.exists()) {
                            Image image = new Image(file.toURI().toString(), 50, 50, true, true);
                            imageView.setImage(image);
                            setGraphic(imageView);
                        } else {
                            setGraphic(new Label("Image introuvable"));
                        }
                    }
                }
            });

            // Charger les données
            List<Event> events = dashboardDao.getEventTableData(idPartenaire);
            eventTable.getItems().setAll(events);

            System.out.println("Événements chargés !");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}