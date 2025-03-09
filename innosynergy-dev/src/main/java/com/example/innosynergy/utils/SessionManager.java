package com.example.innosynergy.utils;

import com.example.innosynergy.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionManager {
    private static final long SESSION_TIMEOUT = 30 * 60 * 1000; // 30 minutes
    private static final Map<String, Session> sessions = new HashMap<>();

    public static String createSession(User user) {
        String sessionId = UUID.randomUUID().toString();
        sessions.put(sessionId, new Session(user, System.currentTimeMillis()));
        return sessionId;
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