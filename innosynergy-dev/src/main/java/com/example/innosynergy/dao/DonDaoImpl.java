package com.example.innosynergy.dao;

import com.example.innosynergy.config.ConnexionBD;
import com.example.innosynergy.model.Don;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DonDaoImpl implements DonDao {
    private static final String INSERT_DON_SQL = "INSERT INTO dons (id_client, id_partenaire, montant, date_don) VALUES (?, ?, ?, ?)";
    private static final String SELECT_DONS_BY_PARTENAIRE_SQL =
            "SELECT d.*, u.nom as client_nom " +
                    "FROM dons d " +
                    "JOIN clients c ON d.id_client = c.id_client " +
                    "JOIN utilisateurs u ON c.id_client = u.id_utilisateur " +
                    "WHERE d.id_partenaire = ?";
    private static final String SELECT_CLIENT_NAME_SQL = "SELECT u.nom FROM clients c JOIN utilisateurs u ON c.id_client = u.id_utilisateur WHERE c.id_client = ?";
    private static final String SELECT_CLIENT_ID_BY_NAME_SQL = "SELECT c.id_client FROM clients c JOIN utilisateurs u ON c.id_client = u.id_utilisateur WHERE u.nom = ?";
    private static final String SEARCH_DONS_SQL =
            "SELECT d.*, u.nom as client_nom " +
                    "FROM dons d " +
                    "JOIN clients c ON d.id_client = c.id_client " +
                    "JOIN utilisateurs u ON c.id_client = u.id_utilisateur " +
                    "WHERE u.nom LIKE ? OR d.montant LIKE ?";

    private ConnexionBD connexionBD;

    public DonDaoImpl() {
        this.connexionBD = new ConnexionBD();
    }

    @Override
    public void insertDon(Don don) {
        try (Connection connection = connexionBD.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_DON_SQL)) {
            preparedStatement.setInt(1, don.getIdClient());
            if (don.getIdPartenaire() != null) {
                preparedStatement.setInt(2, don.getIdPartenaire());
            } else {
                preparedStatement.setNull(2, Types.INTEGER);
            }
            preparedStatement.setBigDecimal(3, don.getMontant());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(don.getDateDon()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Don> listDonsByPartenaire(int idPartenaire) {
        List<Don> dons = new ArrayList<>();
        try (Connection connection = connexionBD.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_DONS_BY_PARTENAIRE_SQL)) {
            preparedStatement.setInt(1, idPartenaire);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    Don don = new Don();
                    don.setIdDon(rs.getInt("id_don"));
                    don.setIdClient(rs.getInt("id_client"));
                    don.setIdPartenaire(rs.getInt("id_partenaire"));
                    don.setMontant(rs.getBigDecimal("montant"));
                    don.setDateDon(rs.getTimestamp("date_don").toLocalDateTime());
                    don.setStatus(rs.getString("status"));
                    don.setClientName(rs.getString("client_nom")); // Directement récupéré dans la requête SQL
                    dons.add(don);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dons;
    }

    @Override
    public String findClientNameById(int idClient) {
        String clientName = null;
        try (Connection connection = connexionBD.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CLIENT_NAME_SQL)) {
            preparedStatement.setInt(1, idClient);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    clientName = rs.getString("nom");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientName;
    }

    @Override
    public int findClientIdByName(String clientName) {
        int clientId = -1;
        try (Connection connection = connexionBD.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CLIENT_ID_BY_NAME_SQL)) {
            preparedStatement.setString(1, clientName);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    clientId = rs.getInt("id_client");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientId;
    }

    @Override
    public List<Don> searchDons(String keyword) {
        List<Don> dons = new ArrayList<>();
        try (Connection connection = connexionBD.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_DONS_SQL)) {
            String searchKeyword = "%" + keyword + "%";
            preparedStatement.setString(1, searchKeyword);
            preparedStatement.setString(2, searchKeyword);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    Don don = new Don();
                    don.setIdDon(rs.getInt("id_don"));
                    don.setIdClient(rs.getInt("id_client"));
                    don.setIdPartenaire(rs.getInt("id_partenaire"));
                    don.setMontant(rs.getBigDecimal("montant"));
                    don.setDateDon(rs.getTimestamp("date_don").toLocalDateTime());
                    don.setStatus(rs.getString("status"));
                    don.setClientName(rs.getString("client_nom"));
                    dons.add(don);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dons;
    }
}