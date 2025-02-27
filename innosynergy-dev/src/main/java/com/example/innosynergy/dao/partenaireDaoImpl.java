package com.example.innosynergy.dao;

import com.example.innosynergy.config.ConnexionBD;
import com.example.innosynergy.model.PartenaireData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class partenaireDaoImpl implements PartenaireDao {
    private ConnexionBD connexionBD;

    public partenaireDaoImpl() {
        this.connexionBD = new ConnexionBD();
    }

    @Override
    public void addPartenaire(PartenaireData partenaire) {
        String query = "INSERT INTO partenaires (id_partenaire, nom_entreprise, type_activite, site_web, adresse, telephone, autres_documents, etat, date_expiration) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = connexionBD.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, partenaire.getIdPartenaire());
            preparedStatement.setString(2, partenaire.getNomEntreprise());
            preparedStatement.setString(3, partenaire.getTypeActivite());
            preparedStatement.setString(4, partenaire.getSiteWeb());
            preparedStatement.setString(5, partenaire.getAdresse());
            preparedStatement.setString(6, partenaire.getTelephone());
            preparedStatement.setString(7, partenaire.getAutresDocuments());
            preparedStatement.setInt(8, partenaire.getEtat());
            preparedStatement.setDate(9, new java.sql.Date(partenaire.getDateExpiration().getTime()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public PartenaireData getPartenaire(int id) {
        String query = "SELECT * FROM partenaires WHERE id_partenaire = ?";
        try (Connection connection = connexionBD.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new PartenaireData(
                        resultSet.getInt("id_partenaire"),
                        resultSet.getString("nom_entreprise"),
                        resultSet.getString("type_activite"),
                        resultSet.getString("site_web"),
                        resultSet.getString("adresse"),
                        resultSet.getString("telephone"),
                        resultSet.getString("autres_documents"),
                        resultSet.getInt("etat"),
                        resultSet.getDate("date_expiration")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<PartenaireData> getAllPartenaires() {
        List<PartenaireData> partenaires = new ArrayList<>();
        String query = "SELECT * FROM partenaires";
        try (Connection connection = connexionBD.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                partenaires.add(new PartenaireData(
                        resultSet.getInt("id_partenaire"),
                        resultSet.getString("nom_entreprise"),
                        resultSet.getString("type_activite"),
                        resultSet.getString("site_web"),
                        resultSet.getString("adresse"),
                        resultSet.getString("telephone"),
                        resultSet.getString("autres_documents"),
                        resultSet.getInt("etat"),
                        resultSet.getDate("date_expiration")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return partenaires;
    }

    @Override
    public void updatePartenaire(PartenaireData partenaire) {
        String query = "UPDATE partenaires SET nom_entreprise = ?, type_activite = ?, site_web = ?, adresse = ?, telephone = ?, autres_documents = ?, etat = ?, date_expiration = ? WHERE id_partenaire = ?";
        try (Connection connection = connexionBD.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, partenaire.getNomEntreprise());
            preparedStatement.setString(2, partenaire.getTypeActivite());
            preparedStatement.setString(3, partenaire.getSiteWeb());
            preparedStatement.setString(4, partenaire.getAdresse());
            preparedStatement.setString(5, partenaire.getTelephone());
            preparedStatement.setString(6, partenaire.getAutresDocuments());
            preparedStatement.setInt(7, partenaire.getEtat());
            preparedStatement.setDate(8, new java.sql.Date(partenaire.getDateExpiration().getTime()));
            preparedStatement.setInt(9, partenaire.getIdPartenaire());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletePartenaire(int id) {
        String query = "DELETE FROM partenaires WHERE id_partenaire = ?";
        try (Connection connection = connexionBD.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}