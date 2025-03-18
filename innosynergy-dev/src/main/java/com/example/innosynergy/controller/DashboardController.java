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
        legendBox.getChildren().clear();

        legendBox.getChildren().addAll(
                createLegendItem("Dons mensuels", "#FF6384"),   // Rouge Rosé
                createLegendItem("Dons ponctuels", "#FFCE56"),   // Jaune Clair
                createLegendItem("Dons en ligne", "#4BC0C0")      // Bleu-Vert Pastel
        );
    }

    private HBox createLegendItem(String text, String color) {
        Label colorBox = new Label(" ");
        colorBox.setMinSize(12, 12);
        colorBox.setStyle("-fx-background-color: " + color + "; -fx-border-color: black;");

        Label label = new Label(text);
        label.setStyle("-fx-font-size: 14px; -fx-padding: 5px;");

        HBox legendItem = new HBox(5, colorBox, label);
        legendItem.setAlignment(Pos.CENTER_LEFT);
        return legendItem;
    }




    private void loadAreaChartData() {
        try {
            // Nettoyer l'ancien graphique
            areaChart.getData().clear();

            // Créer les séries de données pour les demandes de dons par mois
            XYChart.Series<String, Number> seriesDemandesDons = new XYChart.Series<>();
            seriesDemandesDons.setName("Demandes de Dons");

            // Récupérer les données réelles depuis la base de données (par exemple, dashboardDao.getDonsDataByMonth())
            List<XYChart.Data<String, Number>> data = dashboardDao.getDonsDataByMonth(idPartenaire);  // Méthode à implémenter dans le DAO

            // Ajouter les données à la série
            seriesDemandesDons.getData().addAll(data);

            // Ajouter la série au graphique
            areaChart.getData().add(seriesDemandesDons);

            // Ajouter des classes CSS à la série (si besoin)
            seriesDemandesDons.getNode().getStyleClass().add("demandesDons");

            System.out.println("Graphique AreaChart mis à jour avec les données de demandes de dons !");
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
