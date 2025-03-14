package com.example.innosynergy.controller;

import com.example.innosynergy.dao.DashboardDao;
import com.example.innosynergy.dao.DashboardDaoImlp;
import com.example.innosynergy.model.Event;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class DashboardController {

    @FXML
    private LineChart<Number, Number> lineChart;

    @FXML
    private TableView<Event> eventTable;

    @FXML
    private TableColumn<Event, String> numberCol;

    @FXML
    private TableColumn<Event, String> titleCol;

    @FXML
    private TableColumn<Event, String> descCol;

    @FXML
    private TableColumn<Event, String> dateCol;

    @FXML
    private TableColumn<Event, String> placeCol;

    @FXML
    private TableColumn<Event, String> partnerCol;

    @FXML
    private TableColumn<Event, String> statusCol;

    @FXML
    private Label clientCountLabel;

    @FXML
    private Label partnerCountLabel;

    @FXML
    private Label helpRequestCountLabel;

    private final DashboardDao dashboardDao = new DashboardDaoImlp();

    @FXML
    public void initialize() {
        loadDashboardData();
    }

    private void loadDashboardData() {
        loadStatistics();
        loadLineChartData();
        loadEventTableData();
    }

    private void loadStatistics() {
        clientCountLabel.setText(String.valueOf(dashboardDao.getClientCount()));
        partnerCountLabel.setText(String.valueOf(dashboardDao.getPartnerCount()));
        helpRequestCountLabel.setText(String.valueOf(dashboardDao.getHelpRequestCount()));
    }

    private void loadLineChartData() {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        List<XYChart.Data<Number, Number>> data = dashboardDao.getLineChartData();
        series.getData().addAll(data);
        lineChart.getData().add(series);
    }

    private void loadEventTableData() {
        numberCol.setCellValueFactory(new PropertyValueFactory<>("number"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("dateEvenement"));
        placeCol.setCellValueFactory(new PropertyValueFactory<>("place"));
        partnerCol.setCellValueFactory(new PropertyValueFactory<>("partner"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        List<Event> events = dashboardDao.getEventTableData();
        eventTable.getItems().addAll(events);
    }
}