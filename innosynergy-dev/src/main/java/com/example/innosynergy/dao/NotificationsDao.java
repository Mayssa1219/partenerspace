package com.example.innosynergy.dao;

public interface NotificationsDao {
    void insertNotification(int userId, String message, String type);
     void loadNotifications();
}
