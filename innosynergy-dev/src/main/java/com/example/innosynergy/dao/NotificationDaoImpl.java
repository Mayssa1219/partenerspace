package com.example.innosynergy.dao;

import com.example.innosynergy.config.ConnexionBD;
import com.example.innosynergy.controller.NotificationBarController;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class NotificationDaoImpl implements NotificationsDao {
    private final NotificationBarController notificationBarController;

    public NotificationDaoImpl(NotificationBarController notificationBarController) {
        this.notificationBarController = notificationBarController;
    }
    @Override
    public void insertNotification(int userId, String message, String type) {
        String sql = "INSERT INTO notifications (id_utilisateur, message, type_notification, date_envoi) VALUES (?, ?, ?, NOW())";
        try (Connection connection = ConnexionBD.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, message);
            preparedStatement.setString(3, type);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   @Override
    public void loadNotifications() {
        try (Connection connection = ConnexionBD.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT n.id_notification, n.message, n.type_notification, n.date_envoi, u.nom " +
                             "FROM notifications n " +
                             "JOIN utilisateurs u ON n.id_utilisateur = u.id_utilisateur")) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id_notification");
                String message = resultSet.getString("message");
                String type = resultSet.getString("type_notification");
                String date = resultSet.getString("date_envoi");
                String senderName = resultSet.getString("nom");

                VBox notificationBox = notificationBarController.createNotificationBox(senderName, message, type, date);
                notificationBarController.getNotificationContainer().getChildren().add(notificationBox);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}