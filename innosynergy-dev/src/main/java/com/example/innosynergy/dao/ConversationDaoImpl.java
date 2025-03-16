package com.example.innosynergy.dao;

import com.example.innosynergy.config.ConnexionBD;
import com.example.innosynergy.model.Conversation;
import com.example.innosynergy.model.Message;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConversationDaoImpl implements ConversationDao {

    private ConnexionBD connexionBD;
    public ConversationDaoImpl() {

    }
    public ConversationDaoImpl(ConnexionBD connexionBD) {
        this.connexionBD = connexionBD;
    }


    @Override
    public Conversation getConversationByUsers(int userId1, int userId2) throws SQLException {
        String query = "SELECT * FROM conversations WHERE (id_user1 = ? AND id_user2 = ?) OR (id_user1 = ? AND id_user2 = ?)";

        try (Connection connection = connexionBD.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId1);
            stmt.setInt(2, userId2);
            stmt.setInt(3, userId2);
            stmt.setInt(4, userId1);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int idConversation = rs.getInt("id_conversation");
                    String sujet = rs.getString("sujet");
                    Timestamp dateCreation = rs.getTimestamp("date_creation");
                    List<Message> messages = getMessagesByConversationId(idConversation);
                    return new Conversation(idConversation, userId1, userId2, sujet, dateCreation, messages);
                }
            }
        }
        return null;  // Retourner null si la conversation n'existe pas
    }


    // Méthode pour créer une conversation
    @Override
    public void createConversation(Conversation conversation) throws SQLException {
        String query = "INSERT INTO conversations (sujet, date_creation, id_user1, id_user2) VALUES (?, ?, ?, ?)";

        try (Connection connection = connexionBD.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            // Paramétrage des valeurs pour l'insertion
            stmt.setString(1, conversation.getSujet());
            stmt.setTimestamp(2, conversation.getDateCreation());  // Utilisation de Timestamp si date_creation est un Timestamp
            stmt.setInt(3, conversation.getIdUser1());  // Assurez-vous d'avoir idUser1 dans le modèle Conversation
            stmt.setInt(4, conversation.getIdUser2());  // Assurez-vous d'avoir idUser2 dans le modèle Conversation

            // Exécution de la requête d'insertion
            stmt.executeUpdate();

            // Récupérer l'ID auto-généré
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);  // Récupérer l'ID généré
                    conversation.setIdConversation(generatedId);  // Assigner l'ID généré à l'objet Conversation
                }
            }
        }
    }


    // Méthode pour récupérer une conversation par son ID
    @Override
    public Conversation getConversationById(int idConversation) throws SQLException {
        String query = "SELECT * FROM conversations WHERE id_conversation = ?";

        try (Connection connection = connexionBD.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idConversation);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String sujet = rs.getString("sujet");
                    Timestamp dateCreation = rs.getTimestamp("date_creation");  // Utiliser Timestamp pour la date
                    List<Message> messages = getMessagesByConversationId(idConversation);
                    int idUser1 = rs.getInt("id_user1");
                    int idUser2 = rs.getInt("id_user2");
                    return new Conversation(idConversation, idUser1, idUser2, sujet, dateCreation, messages);  // Passer les utilisateurs au constructeur
                }
            }
        }
        return null;
    }

    // Méthode pour récupérer les messages d'une conversation
    private List<Message> getMessagesByConversationId(int idConversation) throws SQLException {
        List<Message> messages = new ArrayList<>();
        String query = "SELECT * FROM messages WHERE id_conversation = ? ORDER BY date_envoi ASC";

        try (Connection connection = connexionBD.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idConversation);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int idMessage = rs.getInt("id_message");
                    int idExpediteur = rs.getInt("id_expediteur");
                    int idDestinataire = rs.getInt("id_destinataire");
                    String messageText = rs.getString("message");
                    String reponseChatbot = rs.getString("reponse_chatbot");
                    Timestamp dateEnvoi = rs.getTimestamp("date_envoi");
                    String typeMessage = rs.getString("type_message");
                    String statut = rs.getString("statut");
                    messages.add(new Message(idMessage, idExpediteur, idDestinataire, messageText, reponseChatbot, dateEnvoi, typeMessage, statut, idConversation));
                }
            }
        }
        return messages;
    }

    // Méthode pour obtenir toutes les conversations
    @Override
    public List<Conversation> getAllConversations() throws SQLException {
        List<Conversation> conversations = new ArrayList<>();
        String query = "SELECT * FROM conversations";

        try (Connection connection = connexionBD.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int idConversation = rs.getInt("id_conversation");
                String sujet = rs.getString("sujet");
                Timestamp dateCreation = rs.getTimestamp("date_creation");
                List<Message> messages = getMessagesByConversationId(idConversation);
                int idUser1 = rs.getInt("id_user1");
                int idUser2 = rs.getInt("id_user2");
                conversations.add(new Conversation(idConversation, idUser1, idUser2, sujet, dateCreation, messages));
            }
        }
        return conversations;
    }
}
