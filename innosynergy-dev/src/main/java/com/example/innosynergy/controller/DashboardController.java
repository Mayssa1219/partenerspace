package com.example.innosynergy.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DashboardController {

    @FXML
    private LineChart<Number, Number> usageLineChart;
    @FXML
    private LineChart<Number, Number> contractsLineChart;
    @FXML
    private Label clockLabel;

    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @FXML
    private void initialize() {
        loadUsageData();
        loadContractData();
        startClock();
    }

    private void loadUsageData() {
        // Simuler des données d'utilisation
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Utilisation");
        series.getData().add(new XYChart.Data<>(1, 5));
        series.getData().add(new XYChart.Data<>(2, 15));
        series.getData().add(new XYChart.Data<>(3, 10));
        series.getData().add(new XYChart.Data<>(4, 20));
        series.getData().add(new XYChart.Data<>(5, 25));
        usageLineChart.getData().add(series);
    }

    private void loadContractData() {
        // Simuler des données de contrats
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Contrats");
        series.getData().add(new XYChart.Data<>(1, 2));
        series.getData().add(new XYChart.Data<>(2, 3));
        series.getData().add(new XYChart.Data<>(3, 5));
        series.getData().add(new XYChart.Data<>(4, 4));
        series.getData().add(new XYChart.Data<>(5, 6));
        contractsLineChart.getData().add(series);
    }

    private void startClock() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalDateTime currentTime = LocalDateTime.now();
            clockLabel.setText(currentTime.format(timeFormatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
}