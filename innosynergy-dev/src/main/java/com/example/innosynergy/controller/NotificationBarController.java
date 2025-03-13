package com.example.innosynergy.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import com.example.innosynergy.dao.NotificationDaoImpl;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class NotificationBarController implements Initializable {

    @FXML
    private VBox notificationContainer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        NotificationDaoImpl notificationDao = new NotificationDaoImpl(this);
        notificationDao.loadNotifications();
    }
    @FXML
    private Button loadNotificationsButton;

    @FXML
    private void handleLoadNotifications() {
        NotificationDaoImpl notificationDao = new NotificationDaoImpl(this);
        notificationDao.loadNotifications();
    }

    public VBox getNotificationContainer() {
        return notificationContainer;
    }

    public VBox createNotificationBox(String senderName, String message, String type, String date) {
        VBox vbox = new VBox(10);
        vbox.setStyle("-fx-background-color: white; -fx-padding: 10; -fx-background-radius: 15; -fx-border-color: #d1d5db; -fx-border-radius: 15;");
        vbox.setAlignment(Pos.CENTER_LEFT);

        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.CENTER_LEFT);

        VBox iconBox = new VBox();
        iconBox.setAlignment(Pos.CENTER);
        iconBox.setStyle("-fx-background-color: #ec4899; -fx-background-radius: 50%; -fx-min-width: 40; -fx-min-height: 40;");
        Label iconLabel = new Label(senderName.substring(0, 1).toUpperCase());
        iconLabel.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");
        iconBox.getChildren().add(iconLabel);

        VBox textBox = new VBox(5);
        textBox.setAlignment(Pos.TOP_LEFT);
        HBox titleBox = new HBox(10);
        titleBox.setAlignment(Pos.CENTER_LEFT);
        Label titleLabel = new Label(senderName + " - " + type);
        titleLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        Label dateLabel = new Label(date);
        dateLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #6b7280;");
        titleBox.getChildren().addAll(titleLabel, dateLabel);
        Label messageLabel = new Label(message);
        messageLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #4b5563;");
        textBox.getChildren().addAll(titleBox, messageLabel);

        hbox.getChildren().addAll(iconBox, textBox);
        vbox.getChildren().add(hbox);

        return vbox;
    }
}