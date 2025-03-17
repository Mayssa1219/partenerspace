package com.example.innosynergy.dao;

import com.example.innosynergy.config.ConnexionBD;
import com.example.innosynergy.model.DemandeData;
import com.example.innosynergy.model.Event;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public  class DemandeDaoImpl implements DemandeDao {
    private static final String INSERT_DEMANDE_SQL = "INSERT INTO demandes_aide " +
            "(id_client, id_partenaire, type_aide, " +
            "description, montant_demande, date_demande, status, preuves) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_DEMANDES_SQL = "SELECT * FROM demandes_aide";
    private static final String SELECT_DEMANDE_BY_ID_SQL = "SELECT * FROM demandes_aide " +
            "WHERE id_demande = ?";
    private static final String SEARCH_DEMANDES_SQL = "SELECT * FROM demandes_aide " +
            "WHERE type_aide LIKE ? " +
            "OR description LIKE ?";
    private static final String UPDATE_DEMANDE_SQL = "UPDATE demandes_aide SET id_client = ?, " +
            "id_partenaire = ?, " +
            "type_aide = ?, description = ?, montant_demande = ?, date_demande = ?, status = ?, " +
            "preuves = ? " +
            "WHERE id_demande = ?";

    private static final String DELETE_DEMANDE_SQL = "DELETE FROM demandes_aide WHERE id_demande = ?";

    private static final String SELECT_ALL_DEMANDES_BY_PARTENAIRE_SQL = "SELECT * FROM demandes_aide " +
            "WHERE id_partenaire = ?";

    private static final String SELECT_CLIENT_ID_BY_NAME_SQL = "SELECT utilisateurs.id_utilisateur " +
            "FROM clients JOIN utilisateurs ON clients.id_client = utilisateurs.id_utilisateur WHERE utilisateurs.nom = ?";


    private ConnexionBD connexionBD;

    public DemandeDaoImpl() {
        this.connexionBD = new ConnexionBD();
    }
    @Override
    public int getClientIdByName(String clientName) {
        int clientId = -1;
        try (Connection connection = connexionBD.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CLIENT_ID_BY_NAME_SQL)) {
            preparedStatement.setString(1, clientName);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    clientId = rs.getInt("id_utilisateur");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientId;
    }

    @Override
    public List<DemandeData> getDemandesByPartenaire(int idPartenaire) {
        List<DemandeData> demandes = new ArrayList<>();
        try (Connection connection = connexionBD.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_DEMANDES_BY_PARTENAIRE_SQL)) {
            preparedStatement.setInt(1, idPartenaire);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    DemandeData demande = new DemandeData();
                    demande.setIdDemande(rs.getInt("id_demande"));
                    demande.setIdClient(rs.getInt("id_client"));
                    demande.setIdPartenaire(rs.getInt("id_partenaire"));
                    demande.setTypeAide(rs.getString("type_aide"));
                    demande.setDescription(rs.getString("description"));
                    demande.setMontantDemande(rs.getDouble("montant_demande"));
                    demande.setDateDemande(rs.getTimestamp("date_demande").toLocalDateTime());
                    demande.setStatus(rs.getString("status"));
                    demande.setPreuves(rs.getString("preuves"));
                    demandes.add(demande);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return demandes;
    }

    @Override
    public void insertDemande(DemandeData demande) {
        try (Connection connection = connexionBD.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_DEMANDE_SQL)) {
            preparedStatement.setInt(1, demande.getIdClient());
            preparedStatement.setInt(2, demande.getIdPartenaire());
            preparedStatement.setString(3, demande.getTypeAide());
            preparedStatement.setString(4, demande.getDescription());
            preparedStatement.setDouble(5, demande.getMontantDemande());
            preparedStatement.setTimestamp(6, Timestamp.valueOf(demande.getDateDemande()));
            preparedStatement.setString(7, demande.getStatus());
            preparedStatement.setString(8, demande.getPreuves());

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Demande ajoutée avec succès : " + demande.getIdDemande());
            } else {
                System.out.println("Aucune ligne insérée. Vérifiez la requête SQL.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de la demande : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<DemandeData> listDemandes() {
        List<DemandeData> demandes = new ArrayList<>();
        try (Connection connection = connexionBD.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_DEMANDES_SQL);
             ResultSet rs = preparedStatement.executeQuery()) {

            System.out.println("Requête SQL exécutée : " + SELECT_ALL_DEMANDES_SQL);

            while (rs.next()) {
                DemandeData demande = new DemandeData();
                demande.setIdDemande(rs.getInt("id_demande"));
                demande.setIdClient(rs.getInt("id_client"));
                demande.setIdPartenaire(rs.getInt("id_partenaire"));
                demande.setTypeAide(rs.getString("type_aide"));
                demande.setDescription(rs.getString("description"));
                demande.setMontantDemande(rs.getDouble("montant_demande"));
                demande.setDateDemande(rs.getTimestamp("date_demande").toLocalDateTime());
                demande.setStatus(rs.getString("status"));
                demande.setPreuves(rs.getString("preuves"));
                demandes.add(demande);

                System.out.println("Demande récupérée : " + demande.getIdDemande() + " - " + demande.getTypeAide());
            }
            System.out.println("Nombre total de demandes récupérées : " + demandes.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return demandes;
    }

    @Override
    public DemandeData findDemandeById(int idDemande) {
        DemandeData demande = null;
        try (Connection connection = connexionBD.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_DEMANDE_BY_ID_SQL)) {
            preparedStatement.setInt(1, idDemande);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    demande = new DemandeData();
                    demande.setIdDemande(rs.getInt("id_demande"));
                    demande.setIdClient(rs.getInt("id_client"));
                    demande.setIdPartenaire(rs.getInt("id_partenaire"));
                    demande.setTypeAide(rs.getString("type_aide"));
                    demande.setDescription(rs.getString("description"));
                    demande.setMontantDemande(rs.getDouble("montant_demande"));
                    demande.setDateDemande(rs.getTimestamp("date_demande").toLocalDateTime());
                    demande.setStatus(rs.getString("status"));
                    demande.setPreuves(rs.getString("preuves"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return demande;
    }

    @Override
    public List<DemandeData> searchDemandes(String keyword) {
        List<DemandeData> demandes = new ArrayList<>();
        try (Connection connection = connexionBD.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_DEMANDES_SQL)) {

            String searchKeyword = "%" + keyword + "%";
            preparedStatement.setString(1, searchKeyword);
            preparedStatement.setString(2, searchKeyword);

            System.out.println("Requête SQL exécutée : " + SEARCH_DEMANDES_SQL);
            System.out.println("Mot-clé de recherche : " + searchKeyword);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    DemandeData demande = new DemandeData();
                    demande.setIdDemande(rs.getInt("id_demande"));
                    demande.setIdClient(rs.getInt("id_client"));
                    demande.setIdPartenaire(rs.getInt("id_partenaire"));
                    demande.setTypeAide(rs.getString("type_aide"));
                    demande.setDescription(rs.getString("description"));
                    demande.setMontantDemande(rs.getDouble("montant_demande"));
                    demande.setDateDemande(rs.getTimestamp("date_demande").toLocalDateTime());
                    demande.setStatus(rs.getString("status"));
                    demande.setPreuves(rs.getString("preuves"));
                    demandes.add(demande);

                    System.out.println("Demande trouvée : " + demande.getIdDemande() + " - " + demande.getTypeAide());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return demandes;
    }

    @Override
    public void updateDemande(DemandeData demande) {
        try (Connection connection = connexionBD.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_DEMANDE_SQL)) {
            preparedStatement.setInt(1, demande.getIdClient());
            preparedStatement.setInt(2, demande.getIdPartenaire());
            preparedStatement.setString(3, demande.getTypeAide());
            preparedStatement.setString(4, demande.getDescription());
            preparedStatement.setDouble(5, demande.getMontantDemande());
            preparedStatement.setTimestamp(6, Timestamp.valueOf(demande.getDateDemande()));
            preparedStatement.setString(7, demande.getStatus());
            preparedStatement.setString(8, demande.getPreuves());
            preparedStatement.setInt(9, demande.getIdDemande());

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Demande mise à jour avec succès : " + demande.getIdDemande());
            } else {
                System.out.println("Aucune ligne mise à jour. Vérifiez la requête SQL.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour de la demande : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void deleteDemande(int idDemande) {
        try (Connection connection = connexionBD.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_DEMANDE_SQL)) {
            preparedStatement.setInt(1, idDemande);

            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Demande supprimée avec succès : ID " + idDemande);
            } else {
                System.out.println("Aucune ligne supprimée. Vérifiez la requête SQL.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de la demande : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<Event> getEventTableData(int idPartenaire) {
        return List.of();
    }
}