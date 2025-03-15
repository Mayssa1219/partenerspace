package com.example.innosynergy.dao;

import com.example.innosynergy.config.ConnexionBD;
import com.example.innosynergy.model.Event;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventDaoImpl implements EventDao {
    // Requête INSERT
    private static final String INSERT_EVENT_SQL = "INSERT INTO evenements (titre, description, " +
            "date_evenement, lieu, id_partenaire , status , imageName ) VALUES (?, ?, ?, ?, ?, ?, ? )";
    // Requête SELECT
    private static final String SELECT_ALL_EVENTS_SQL = "SELECT * FROM evenements";
    private static final String SELECT_EVENT_BY_ID_SQL = "SELECT * FROM evenements WHERE id_evenement = ?";
    private static final String SELECT_EVENTS_BY_PARTENAIRE_ID_SQL = "SELECT * FROM evenements WHERE id_partenaire = ?";

    private static final String SEARCH_EVENTS_SQL = "SELECT * FROM evenements WHERE titre LIKE ? " +
            "OR description LIKE ?";
    // Requête UPDATE
    private static final String UPDATE_EVENT_SQL = "UPDATE evenements SET titre = ?, " +
            "description = ?, date_evenement = ?, lieu = ?, id_partenaire = ?, status = ? , imageName = ? " +
            "WHERE id_evenement = ?";
    // Requête DELETE
    private static final String DELETE_EVENT_SQL = "DELETE FROM evenements WHERE id_evenement = ?";

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
            preparedStatement.setInt(5, event.getIdPartenaire());
            preparedStatement.setString(6, event.getStatus());// Ajouter l'ID du partenaire
            preparedStatement.setString(7, event.getImageName());

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

            System.out.println("Requête SQL exécutée : " + SELECT_ALL_EVENTS_SQL);

            while (rs.next()) {
                Event event = new Event();
                event.setIdEvenement(rs.getInt("id_evenement"));
                event.setTitre(rs.getString("titre"));
                event.setDescription(rs.getString("description"));
                event.setDateEvenement(rs.getTimestamp("date_evenement").toLocalDateTime());
                event.setLieu(rs.getString("lieu"));
                event.setStatus(rs.getString("status"));
                event.setImageName(rs.getString("imageName")); // Récupérer le champ imageName
                events.add(event);

                System.out.println("Événement récupéré : " + event.getTitre() + " - " + event.getLieu() + " - " + event.getImageName());
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
                    event.setLieu(rs.getString("lieu"));
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

            System.out.println("Requête SQL exécutée : " + SEARCH_EVENTS_SQL);
            System.out.println("Mot-clé de recherche : " + searchKeyword);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    Event event = new Event();
                    event.setIdEvenement(rs.getInt("id_evenement"));
                    event.setTitre(rs.getString("titre"));
                    event.setDescription(rs.getString("description"));
                    event.setDateEvenement(rs.getTimestamp("date_evenement").toLocalDateTime());
                    event.setLieu(rs.getString("lieu"));
                    event.setStatus(rs.getString("status"));
                    events.add(event);

                    System.out.println("Événement trouvé : " + event.getTitre() + " - " + event.getLieu());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }

    @Override
    public void updateEvent(Event event) {
        try (Connection connection = connexionBD.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_EVENT_SQL)) {
            preparedStatement.setString(1, event.getTitre());
            preparedStatement.setString(2, event.getDescription());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(event.getDateEvenement()));
            preparedStatement.setString(4, event.getLieu());
            preparedStatement.setInt(5, event.getIdPartenaire());
            preparedStatement.setString(6, event.getStatus());
            preparedStatement.setString(7, event.getImageName());
            preparedStatement.setInt(8, event.getIdEvenement());

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Événement mis à jour avec succès : " + event.getTitre());
            } else {
                System.out.println("Aucune ligne mise à jour. Vérifiez la requête SQL.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour de l'événement : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteEvent(int idEvenement) {
        try (Connection connection = connexionBD.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_EVENT_SQL)) {
            preparedStatement.setInt(1, idEvenement);

            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Événement supprimé avec succès : ID " + idEvenement);
            } else {
                System.out.println("Aucune ligne supprimée. Vérifiez la requête SQL.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de l'événement : " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Méthode pour lister les événements par id_partenaire
    public List<Event> listEventsByPartenaireId(int idPartenaire) {
        List<Event> events = new ArrayList<>();
        try (Connection connection = connexionBD.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EVENTS_BY_PARTENAIRE_ID_SQL)) {
            preparedStatement.setInt(1, idPartenaire);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                System.out.println("Requête SQL exécutée : " + SELECT_EVENTS_BY_PARTENAIRE_ID_SQL);

                while (rs.next()) {
                    Event event = new Event();
                    event.setIdEvenement(rs.getInt("id_evenement"));
                    event.setTitre(rs.getString("titre"));
                    event.setDescription(rs.getString("description"));
                    event.setDateEvenement(rs.getTimestamp("date_evenement").toLocalDateTime());
                    event.setLieu(rs.getString("lieu"));
                    event.setStatus(rs.getString("status"));
                    event.setImageName(rs.getString("imageName"));
                    events.add(event);

                    System.out.println("Événement récupéré : " + event.getTitre() + " - " + event.getLieu() + " - " + event.getImageName());
                }
                System.out.println("Nombre total d'événements récupérés : " + events.size());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }
}