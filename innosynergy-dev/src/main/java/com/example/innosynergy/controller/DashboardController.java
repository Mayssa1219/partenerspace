package com.example.innosynergy.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class DashboardController {

    @FXML
    private Label stat1Label;

    @FXML
    private Label stat2Label;

    @FXML
    private Label stat3Label;

    @FXML
    private ListView<String> recentActivityList;

    @FXML
    private ListView<String> notificationsList;

    @FXML
    private void initialize() {
        // Initialize statistics
        stat1Label.setText("123");
        stat2Label.setText("456");
        stat3Label.setText("789");

        // Initialize recent activities
        recentActivityList.getItems().addAll(
                "Activité récente 1",
                "Activité récente 2",
                "Activité récente 3"
        );

        // Initialize notifications
        notificationsList.getItems().addAll(
                "Notification 1",
                "Notification 2",
                "Notification 3"
        );
    }
}