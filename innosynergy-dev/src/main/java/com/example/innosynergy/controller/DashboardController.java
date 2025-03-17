package com.example.innosynergy.controller;

import com.example.innosynergy.dao.DashboardDao;
import com.example.innosynergy.dao.DashboardDaoImpl;
import com.example.innosynergy.model.Event;
import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.TableCell;
import com.example.innosynergy.utils.SessionManager;
import javafx.scene.layout.GridPane;

import java.util.List;

public class DashboardController {

    @FXML
    private LineChart<Number, Number> lineChart;

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
    private Label benevoleCountLabel; // Ajoutez cette ligne

    @FXML
    private AreaChart<Number, Number> areaChart; // Remplacer LineChart par AreaChart

    private final DashboardDao dashboardDao = new DashboardDaoImpl();
    private int idPartenaire;

    @FXML
    public void initialize() {
        // Récupérer l'ID de session courant
        String sessionId = SessionManager.getCurrentSessionId();

        // Récupérer l'ID du partenaire courant à partir de la session
        idPartenaire = SessionManager.getUserId(sessionId);

        // Charger les données du tableau de bord avec l'ID du partenaire
        loadDashboardData(idPartenaire);
    }


    private void loadDashboardData(int idPartenaire) {
        loadStatistics(idPartenaire);
        loadAreaChartData(); // Remplacer loadLineChartData par loadAreaChartData
        loadEventTableData(idPartenaire);
    }

    private void loadStatistics(int idPartenaire) {
        try {
            // Mettre à jour les statistiques
            clientCountLabel.setText(String.valueOf(dashboardDao.getClientCount(idPartenaire)));
            helpRequestCountLabel.setText(String.valueOf(dashboardDao.getHelpRequestCountByPartenaire(idPartenaire))); // Utiliser la nouvelle méthode
            eventCountLabel.setText(String.valueOf(dashboardDao.getEventCount(idPartenaire))); // Passer idPartenaire
            benevoleCountLabel.setText(String.valueOf(dashboardDao.getBenevoleCount(idPartenaire))); // Ajoutez cette ligne
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadLineChartData() {
        try {
            XYChart.Series<Number, Number> series = new XYChart.Series<>();
            List<XYChart.Data<Number, Number>> data = dashboardDao.getLineChartData();
            series.getData().addAll(data);
            lineChart.getData().add(series);
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
            // Configurer les colonnes de la table
            titleCol.setCellValueFactory(new PropertyValueFactory<>("titre"));
            descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            dateCol.setCellValueFactory(new PropertyValueFactory<>("dateEvenement"));
            placeCol.setCellValueFactory(new PropertyValueFactory<>("lieu"));
            statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

            // Personnalisation de la colonne image pour afficher l'image
            imageCol.setCellValueFactory(new PropertyValueFactory<>("imageName"));
            imageCol.setCellFactory(column -> new TableCell<Event, String>() {
                @Override
                protected void updateItem(String imageName, boolean empty) {
                    super.updateItem(imageName, empty);
                    if (empty || imageName == null) {
                        setGraphic(null); // Si la cellule est vide, ne rien afficher
                    } else {
                        // Créez un ImageView avec l'image
                        Image image = new Image("file:uploads/" + imageName); // Chemin relatif ou absolu vers l'image
                        ImageView imageView = new ImageView(image);
                        imageView.setFitHeight(50);  // Définir la taille de l'image
                        imageView.setFitWidth(50);
                        setGraphic(imageView); // Afficher l'image dans la cellule
                    }
                }
            });

            // Charger les données de la table
            List<Event> events = dashboardDao.getEventTableData(idPartenaire);
            eventTable.getItems().clear(); // Effacer les données existantes
            eventTable.getItems().addAll(events);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}