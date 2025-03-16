package com.example.innosynergy.dao;

import com.example.innosynergy.model.Conversation;

import java.sql.SQLException;
import java.util.List;

public interface ConversationDao {
    Conversation getConversationByUsers(int userId1, int userId2) throws SQLException;
    // Méthode pour créer une conversation
    void createConversation(Conversation conversation) throws SQLException;

    // Méthode pour récupérer une conversation par son ID
    Conversation getConversationById(int idConversation) throws SQLException;

    // Méthode pour obtenir toutes les conversations
    List<Conversation> getAllConversations() throws SQLException;
}
