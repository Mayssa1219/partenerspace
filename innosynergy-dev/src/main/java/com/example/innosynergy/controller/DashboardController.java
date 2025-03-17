package com.example.innosynergy.controller;

import com.example.innosynergy.dao.DashboardDao;
import com.example.innosynergy.dao.DashboardDaoImpl;
import com.example.innosynergy.model.Event;
import com.example.innosynergy.utils.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
    private AreaChart<Number, Number> areaChart; // Remplacer LineChart par AreaChart

    private final DashboardDao dashboardDao = new DashboardDaoImpl();
    private int idPartenaire;

    @FXML
    public void initialize() {
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
            // Créer les séries de données
            XYChart.Series<Number, Number> seriesPerches = new XYChart.Series<>();
            seriesPerches.setName("Perches");
            seriesPerches.getData().add(new XYChart.Data<>(2011, 15));
            seriesPerches.getData().add(new XYChart.Data<>(2012, 20));
            seriesPerches.getData().add(new XYChart.Data<>(2013, 19));
            seriesPerches.getData().add(new XYChart.Data<>(2014, 22));

            XYChart.Series<Number, Number> seriesBrochets = new XYChart.Series<>();
            seriesBrochets.setName("Brochets");
            seriesBrochets.getData().add(new XYChart.Data<>(2011, 26));
            seriesBrochets.getData().add(new XYChart.Data<>(2012, 24));
            seriesBrochets.getData().add(new XYChart.Data<>(2013, 8));
            seriesBrochets.getData().add(new XYChart.Data<>(2014, 7));

            XYChart.Series<Number, Number> seriesTruites = new XYChart.Series<>();
            seriesTruites.setName("Truites");
            seriesTruites.getData().add(new XYChart.Data<>(2011, 5));
            seriesTruites.getData().add(new XYChart.Data<>(2012, 0));
            seriesTruites.getData().add(new XYChart.Data<>(2013, 8));
            seriesTruites.getData().add(new XYChart.Data<>(2014, 12));

            // Ajouter les séries au graphique
            areaChart.getData().clear();
            areaChart.getData().addAll(seriesPerches, seriesBrochets, seriesTruites);
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
