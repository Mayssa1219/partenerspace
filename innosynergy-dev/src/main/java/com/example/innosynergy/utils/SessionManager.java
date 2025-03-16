package com.example.innosynergy.utils;

import com.example.innosynergy.controller.NotificationBarController;
import com.example.innosynergy.dao.NotificationDaoImpl;
import com.example.innosynergy.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionManager {
    private static final long SESSION_TIMEOUT = 30 * 60 * 1000; // 30 minutes
    private static final Map<String, Session> sessions = new HashMap<>();
    private static String currentSessionId;
    private static NotificationDaoImpl notificationDao;

    public static void initialize(NotificationBarController notificationBarController) {
        notificationDao = new NotificationDaoImpl(notificationBarController);

    }

    public static String createSession(User user) {
        String sessionId = UUID.randomUUID().toString();
        sessions.put(sessionId, new Session(user, System.currentTimeMillis()));
        currentSessionId = sessionId;
        sendLoginNotification(user);
        return sessionId;
    }

    private static void sendLoginNotification(User user) {
        String message = "Bienvenue cher partenaire " + user.getNom()+" "+user.getPrenom() ;
        notificationDao.insertNotification(user.getIdUtilisateur(), message, "info");
    }

    public static User getUser(String sessionId) {
        Session session = sessions.get(sessionId);
        if (session != null && (System.currentTimeMillis() - session.getCreationTime()) < SESSION_TIMEOUT) {
            return session.getUser();
        } else {
            sessions.remove(sessionId);
            return null;
        }
    }

    public static void invalidateSession(String sessionId) {
        sessions.remove(sessionId);
        if (sessionId.equals(currentSessionId)) {
            currentSessionId = null;
        }
    }

    public static String getCurrentSessionId() {
        return currentSessionId;
    }
    // Exemple de méthode pour obtenir l'ID de l'utilisateur à partir de la session
    public static int getUserId(String sessionId) {
        User user = getUser(sessionId);  // Récupère l'utilisateur par sessionId
        return (user != null) ? user.getIdUtilisateur() : -1;  // Si utilisateur trouvé, retourne l'ID, sinon -1
    }

    private static class Session {
        private final User user;
        private final long creationTime;

        public Session(User user, long creationTime) {
            this.user = user;
            this.creationTime = creationTime;
        }

        public User getUser() {
            return user;
        }

        public long getCreationTime() {
            return creationTime;
        }
    }
}