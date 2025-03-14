package com.example.innosynergy.controller;

import com.example.innosynergy.config.ConnexionBD;
import com.example.innosynergy.dao.NotificationDaoImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;

import java.net.URL;
import java.util.ResourceBundle;

public class NotificationBarController implements Initializable {
    private ConnexionBD DBConnection;
    @FXML
    private VBox notificationContainer;

    @FXML
    private ScrollPane scrollPane; // Boîte englobante pour cacher la barre de notifications

    @FXML
    private ImageView closeImage; // Bouton X rouge

    @FXML
    private ImageView closeBoxImage; // Image cliquable pour fermer la boîte

    private NotificationDaoImpl notificationDao;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        notificationDao = new NotificationDaoImpl(this);
        notificationDao.loadNotifications();

        // Cacher la boîte de notifications en cliquant sur l'icône X rouge
        closeImage.setOnMouseClicked(event -> {
            scrollPane.setVisible(false); // Cache toute la boîte de notifications
        });

        // Cacher la boîte de notifications en cliquant sur l'image cliquable
        closeBoxImage.setOnMouseClicked(event -> {
            scrollPane.setVisible(false); // Cache également la boîte
        });
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
        iconBox.setStyle("-fx-background-color: #013A71; -fx-background-radius: 50%; -fx-min-width: 40; -fx-min-height: 40;");
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