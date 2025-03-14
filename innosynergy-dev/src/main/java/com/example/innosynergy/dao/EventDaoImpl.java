package com.example.innosynergy.dao;

import com.example.innosynergy.config.ConnexionBD;
import com.example.innosynergy.model.Event;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventDaoImpl implements EventDao {
    private static final String INSERT_EVENT_SQL = "INSERT INTO evenements (titre, description, date_evenement, status) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_EVENTS_SQL = "SELECT * FROM evenements";
    private static final String SELECT_EVENT_BY_ID_SQL = "SELECT * FROM evenements WHERE id_evenement = ?";
    private static final String SEARCH_EVENTS_SQL = "SELECT * FROM evenements WHERE titre LIKE ? OR description LIKE ?";

    private ConnexionBD connexionBD;

    public EventDaoImpl() {
        this.connexionBD = new ConnexionBD();
    }

    @Override
    public void insertEvent(Event event) {
        try (Connection connection = connexionBD.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EVENT_SQL)) {
            preparedStatement.setString(1, event.getTitre());
            preparedStatement.setString(2, event.getDescription());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(event.getDateEvenement()));
            preparedStatement.setString(4, event.getLieu()); // Ajouter le lieu
            preparedStatement.setString(5, event.getStatus());

            // Exécuter la requête SQL
            int rowsInserted = preparedStatement.executeUpdate();

            // Afficher un message de succès si l'insertion a réussi
            if (rowsInserted > 0) {
                System.out.println("Événement ajouté avec succès : " + event.getTitre());
            } else {
                System.out.println("Aucune ligne insérée. Vérifiez la requête SQL.");
            }
        } catch (SQLException e) {
            // Afficher l'erreur SQL dans le terminal
            System.err.println("Erreur lors de l'ajout de l'événement : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<Event> listEvents() {
        List<Event> events = new ArrayList<>();
        try (Connection connection = connexionBD.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_EVENTS_SQL);
             ResultSet rs = preparedStatement.executeQuery()) {

            System.out.println("Requête SQL exécutée : " + SELECT_ALL_EVENTS_SQL); // Message de débogage

            while (rs.next()) {
                Event event = new Event();
                event.setIdEvenement(rs.getInt("id_evenement"));
                event.setTitre(rs.getString("titre"));
                event.setDescription(rs.getString("description"));
                event.setDateEvenement(rs.getTimestamp("date_evenement").toLocalDateTime());
                event.setLieu(rs.getString("lieu")); // Récupérer le lieu
                event.setStatus(rs.getString("status"));
                events.add(event);

                // Afficher les détails de l'événement dans le terminal
                System.out.println("Événement récupéré : " + event.getTitre() + " - " + event.getLieu());
            }
            System.out.println("Nombre total d'événements récupérés : " + events.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }

    @Override
    public Event findEventById(int idEvent) {
        Event event = null;
        try (Connection connection = connexionBD.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EVENT_BY_ID_SQL)) {
            preparedStatement.setInt(1, idEvent);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    event = new Event();
                    event.setIdEvenement(rs.getInt("id_evenement"));
                    event.setTitre(rs.getString("titre"));
                    event.setDescription(rs.getString("description"));
                    event.setDateEvenement(rs.getTimestamp("date_evenement").toLocalDateTime());
                    event.setStatus(rs.getString("status"));

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return event;
    }

    @Override
    public List<Event> searchEvents(String keyword) {
        List<Event> events = new ArrayList<>();
        try (Connection connection = connexionBD.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_EVENTS_SQL)) {

            String searchKeyword = "%" + keyword + "%";
            preparedStatement.setString(1, searchKeyword);
            preparedStatement.setString(2, searchKeyword);

            System.out.println("Requête SQL exécutée : " + SEARCH_EVENTS_SQL); // Message de débogage
            System.out.println("Mot-clé de recherche : " + searchKeyword); // Message de débogage

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    Event event = new Event();
                    event.setIdEvenement(rs.getInt("id_evenement"));
                    event.setTitre(rs.getString("titre"));
                    event.setDescription(rs.getString("description"));
                    event.setDateEvenement(rs.getTimestamp("date_evenement").toLocalDateTime());
                    event.setLieu(rs.getString("lieu")); // Récupérer le lieu
                    event.setStatus(rs.getString("status"));
                    events.add(event);

                    // Afficher les détails de l'événement dans le terminal
                    System.out.println("Événement trouvé : " + event.getTitre() + " - " + event.getLieu());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }
}