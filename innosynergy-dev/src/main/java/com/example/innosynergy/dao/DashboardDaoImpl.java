package com.example.innosynergy.dao;

import com.example.innosynergy.config.ConnexionBD;
import com.example.innosynergy.model.Event;
import javafx.scene.chart.XYChart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DashboardDaoImpl implements DashboardDao {

    private static final String SELECT_CLIENT_COUNT_SQL = "SELECT COUNT(*) AS client_count " +
            "FROM clients WHERE id_partenaire = ?";

    private static final String SELECT_PARTNER_COUNT_SQL = "SELECT COUNT(*) AS partner_count " +
            "FROM partenaires";

    private static final String SELECT_HELP_REQUEST_COUNT_BY_PARTENAIRE_SQL = "SELECT COUNT(*) AS nombre_demandes " +
            "FROM demandes_aide WHERE id_partenaire = ?";

    private static final String SELECT_EVENT_COUNT_SQL = "SELECT COUNT(*) AS event_count " +
            "FROM evenements WHERE status = 'actif' AND id_partenaire = ?" ;

    private static final String SELECT_LINE_CHART_DATA_SQL = "SELECT MONTH(date_don) AS mois, " +
            "SUM(montant) AS montant_total FROM dons GROUP BY mois";

    private static final String SELECT_ACTIVE_EVENTS_SQL = "SELECT * FROM evenements WHERE " +
            "status = 'actif' AND id_partenaire = ?"
            ;
    private static final String SELECT_BENEVOLE_COUNT_SQL = "SELECT COUNT(*) AS benevole_count " +
            "FROM benevolat WHERE id_partenaire = ?";

    private static final String SELECT_DONATIONS_BY_MONTH_SQL = "SELECT MONTH(date_don) AS mois, COUNT(*) AS nombre_dons " +
            "FROM dons WHERE id_partenaire = ? GROUP BY mois";

    private ConnexionBD DBConnection;

    @Override
    public int getBenevoleCount(int idPartenaire) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BENEVOLE_COUNT_SQL)) {
            preparedStatement.setInt(1, idPartenaire);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("benevole_count");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0; // Retourner 0 en cas d'erreur
    }

    @Override
    public int getHelpRequestCountByPartenaire(int idPartenaire) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_HELP_REQUEST_COUNT_BY_PARTENAIRE_SQL)) {
            preparedStatement.setInt(1, idPartenaire);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("nombre_demandes");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0; // Retourner 0 en cas d'erreur
    }

    @Override
    public int getEventCount(int idPartenaire) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EVENT_COUNT_SQL)) {
            // Définir le paramètre id_partenaire
            preparedStatement.setInt(1, idPartenaire);

            // Exécuter la requête
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Récupérer le nombre d'événements actifs
                    return resultSet.getInt("event_count");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0; // Retourner 0 en cas d'erreur
    }

    @Override
    public List<XYChart.Data<Number, Number>> getLineChartData() {
        List<XYChart.Data<Number, Number>> data = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_LINE_CHART_DATA_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int month = resultSet.getInt("mois");
                double montantTotal = resultSet.getDouble("montant_total");
                data.add(new XYChart.Data<>(month, montantTotal));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    public List<Event> getEventTableData(int idPartenaire) {
        List<Event> events = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ACTIVE_EVENTS_SQL)) {
            // Modify the query to filter by id_partenaire and status = 'actif'
            preparedStatement.setInt(1, idPartenaire);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                System.out.println("Requête SQL exécutée : " + SELECT_ACTIVE_EVENTS_SQL);

                while (rs.next()) {
                    Event event = new Event();
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

    @Override
    public int getClientCount(int idPartenaire) {
        // Nouvelle requête SQL pour compter les clients distincts
        String SELECT_CLIENT_COUNT_SQL = "SELECT COUNT(DISTINCT id_client) AS nombre_clients " +
                "FROM demandes_aide WHERE id_partenaire = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CLIENT_COUNT_SQL)) {
            // Définir le paramètre id_partenaire
            preparedStatement.setInt(1, idPartenaire);

            // Exécuter la requête
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Récupérer le nombre de clients distincts
                    return resultSet.getInt("nombre_clients");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0; // Retourner 0 en cas d'erreur
    }

    @Override
    public List<XYChart.Data<Number, Number>> getDonationsByMonth(int idPartenaire) {
        List<XYChart.Data<Number, Number>> data = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_DONATIONS_BY_MONTH_SQL)) {
            preparedStatement.setInt(1, idPartenaire);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int mois = resultSet.getInt("mois");
                    int nombreDons = resultSet.getInt("nombre_dons");
                    data.add(new XYChart.Data<>(mois, nombreDons));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
