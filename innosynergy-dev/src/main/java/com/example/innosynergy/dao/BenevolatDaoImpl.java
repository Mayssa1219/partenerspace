package com.example.innosynergy.dao;

import com.example.innosynergy.config.ConnexionBD;
import com.example.innosynergy.model.Benevolat;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BenevolatDaoImpl implements BenevolatDao {

    private static final String INSERT_BENEVOLAT_SQL = "INSERT INTO benevolat (id_partenaire, titre, description, " +
            "date_benevolat, statut, imageName, lieu) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_BENEVOLATS_SQL = "SELECT * FROM benevolat";
    private static final String SELECT_BENEVOLAT_BY_ID_SQL = "SELECT * FROM benevolat WHERE id_benevolat = ?";
    private static final String SEARCH_BENEVOLATS_SQL = "SELECT * FROM benevolat WHERE titre LIKE ? OR description LIKE ?";
    private static final String UPDATE_BENEVOLAT_SQL = "UPDATE benevolat SET id_partenaire = ?, titre = ?, " +
            "description = ?, date_benevolat = ?, statut = ?, imageName = ?, lieu = ? WHERE id_benevolat = ?";
    private static final String DELETE_BENEVOLAT_SQL = "DELETE FROM benevolat WHERE id_benevolat = ?";

    private ConnexionBD connexionBD;

    // Constructeur pour initialiser la connexion à la base de données
    public BenevolatDaoImpl(Connection connection) {
        this.connexionBD = new ConnexionBD();
    }

    public BenevolatDaoImpl() {
    }

    @Override
    public void insertBenevolat(Benevolat benevolat) {
        try (Connection connection = connexionBD.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_BENEVOLAT_SQL)) {
            statement.setInt(1, benevolat.getIdPartenaire());
            statement.setString(2, benevolat.getTitre());
            statement.setString(3, benevolat.getDescription());
            statement.setTimestamp(4, Timestamp.valueOf(benevolat.getDateBenevolat())); // Conversion LocalDateTime en Timestamp
            statement.setString(5, benevolat.getStatut());
            statement.setString(6, benevolat.getImageName());
            statement.setString(7, benevolat.getLieu()); // Ajout du lieu

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Log error or handle it accordingly
        }
    }

    @Override
    public List<Benevolat> listBenevolat() {
        List<Benevolat> benevolats = new ArrayList<>();
        try (Connection connection = connexionBD.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_BENEVOLATS_SQL)) {

            while (resultSet.next()) {
                Benevolat benevolat = mapResultSetToBenevolat(resultSet);
                benevolats.add(benevolat);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log error or handle it accordingly
        }
        return benevolats;
    }

    @Override
    public Benevolat findBenevolatById(int idBenevolat) {
        try (Connection connection = connexionBD.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BENEVOLAT_BY_ID_SQL)) {
            statement.setInt(1, idBenevolat);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToBenevolat(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log error or handle it accordingly
        }
        return null;
    }

    @Override
    public List<Benevolat> searchBenevolats(String keyword) {
        List<Benevolat> benevolats = new ArrayList<>();
        try (Connection connection = connexionBD.getConnection();
             PreparedStatement statement = connection.prepareStatement(SEARCH_BENEVOLATS_SQL)) {
            String searchKeyword = "%" + keyword + "%";
            statement.setString(1, searchKeyword);
            statement.setString(2, searchKeyword);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Benevolat benevolat = mapResultSetToBenevolat(resultSet);
                    benevolats.add(benevolat);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log error or handle it accordingly
        }
        return benevolats;
    }

    @Override
    public void updateBenevolat(Benevolat benevolat) {
        try (Connection connection = connexionBD.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_BENEVOLAT_SQL)) {
            statement.setInt(1, benevolat.getIdPartenaire());
            statement.setString(2, benevolat.getTitre());
            statement.setString(3, benevolat.getDescription());
            statement.setTimestamp(4, Timestamp.valueOf(benevolat.getDateBenevolat())); // Conversion LocalDateTime en Timestamp
            statement.setString(5, benevolat.getStatut());
            statement.setString(6, benevolat.getImageName());
            statement.setString(7, benevolat.getLieu()); // Ajout du lieu
            statement.setInt(8, benevolat.getIdBenevolat()); // Mise à jour de l'ID du bénévole

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Log error or handle it accordingly
        }
    }

    @Override
    public void deleteBenevolat(int idBenevolat) {
        try (Connection connection = connexionBD.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_BENEVOLAT_SQL)) {
            statement.setInt(1, idBenevolat);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Log error or handle it accordingly
        }
    }

    // Méthode utilitaire pour mapper le ResultSet à un objet Benevolat
    private Benevolat mapResultSetToBenevolat(ResultSet resultSet) throws SQLException {
        int idBenevolat = resultSet.getInt("id_benevolat");
        int idPartenaire = resultSet.getInt("id_partenaire");
        String titre = resultSet.getString("titre");
        String description = resultSet.getString("description");
        LocalDateTime dateBenevolat = resultSet.getTimestamp("date_benevolat").toLocalDateTime();
        String statut = resultSet.getString("statut");
        String imageName = resultSet.getString("imageName");
        String lieu = resultSet.getString("lieu"); // Lecture du lieu depuis la base de données

        return new Benevolat(idBenevolat, idPartenaire, titre, description, dateBenevolat, statut, imageName, lieu);
    }
}
