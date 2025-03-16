package com.example.innosynergy.dao;

import com.example.innosynergy.config.ConnexionBD;
import com.example.innosynergy.model.Message;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDaoImpl implements MessageDao {

    private  ConnexionBD connexionBD = new ConnexionBD();


    public MessageDaoImpl() {
    }
    // Constructeur pour initialiser la connexion à la base de données
    public MessageDaoImpl(Connection connection) {
        this.connexionBD = new ConnexionBD();
    }
    @Override
// Méthode pour récupérer les messages pour un utilisateur
    public List<Message> getMessagesForUser(int idExpediteur, int idDestinataire) {
        List<Message> messages = new ArrayList<>();
        String sql = "SELECT * FROM messages WHERE (id_expediteur = ? AND id_destinataire = ?) " +
                "OR (id_expediteur = ? AND id_destinataire = ?) ORDER BY date_envoi ASC";

        try (Connection connection = connexionBD.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, idExpediteur);
            stmt.setInt(2, idDestinataire);
            stmt.setInt(3, idDestinataire);
            stmt.setInt(4, idExpediteur);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                messages.add(new Message(
                        rs.getInt("id_message"),
                        rs.getInt("id_expediteur"),
                        rs.getInt("id_destinataire"),
                        rs.getString("message"),
                        rs.getString("reponse_chatbot"),
                        rs.getTimestamp("date_envoi"),
                        rs.getString("type_message"),
                        rs.getString("statut"),
                        rs.getInt("id_conversation")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("❌ Erreur lors de la récupération des messages !");
        }
        return messages;
    }

    @Override
    // Méthode pour envoyer un message
    public void envoyerMessage(Message message) throws SQLException {
        String sql = "INSERT INTO messages (id_expediteur, id_destinataire, message, date_envoi, id_conversation, type_message, statut) " +
                "VALUES (?, ?, ?, NOW(), ?, ?, ?)";

        try (Connection connection = connexionBD.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, message.getIdExpediteur());
            stmt.setInt(2, message.getIdDestinataire());
            stmt.setString(3, message.getMessage());
            stmt.setInt(4, message.getIdConversation());
            stmt.setString(5, message.getTypeMessage());
            stmt.setString(6, message.getStatut());

            stmt.executeUpdate();
            System.out.println("✅ Message envoyé avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("❌ Erreur lors de l'envoi du message !");
        }
    }

    @Override
    // Méthode pour récupérer les messages entre deux utilisateurs
    public List<Message> getMessages(int idExpediteur, int idDestinataire) {
        List<Message> messages = new ArrayList<>();
        String sql = "SELECT * FROM messages WHERE (id_expediteur = ? AND id_destinataire = ?) " +
                "OR (id_expediteur = ? AND id_destinataire = ?) ORDER BY date_envoi ASC";

        try (Connection connection = connexionBD.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, idExpediteur);
            stmt.setInt(2, idDestinataire);
            stmt.setInt(3, idDestinataire);
            stmt.setInt(4, idExpediteur);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                messages.add(new Message(
                        rs.getInt("id_message"),
                        rs.getInt("id_expediteur"),
                        rs.getInt("id_destinataire"),
                        rs.getString("message"),
                        rs.getString("reponse_chatbot"),
                        rs.getTimestamp("date_envoi"),
                        rs.getString("type_message"),
                        rs.getString("statut"),
                        rs.getInt("id_conversation")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("❌ Erreur lors de la récupération des messages !");
        }
        return messages;
    }
}
