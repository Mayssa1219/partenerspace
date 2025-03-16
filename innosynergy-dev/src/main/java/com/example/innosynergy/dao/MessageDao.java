package com.example.innosynergy.dao;

import com.example.innosynergy.model.Message;

import java.sql.SQLException;
import java.util.List;

public interface MessageDao {
     List<Message> getMessages(int idExpediteur, int idDestinataire);
    void envoyerMessage(Message message) throws SQLException;
    List<Message> getMessagesForUser(int idExpediteur, int idDestinataire);
}
