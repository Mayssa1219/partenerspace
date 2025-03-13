package com.example.innosynergy.dao;

import com.example.innosynergy.config.ConnexionBD;
import com.example.innosynergy.model.PartenaireData;
import com.example.innosynergy.model.PartenaireProfileModel;
import com.example.innosynergy.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PartenaireDaoImpl implements PartenaireDao {
    private ConnexionBD connexionBD;

    public PartenaireDaoImpl() {
        this.connexionBD = new ConnexionBD();
    }

    @Override
    public PartenaireData getPartenaire(int id) {
        String query = "SELECT u.id_utilisateur, u.nom, u.prenom, u.email, u.telephone, u.date_inscription, u.type_utilisateur, u.avatar, u.statut_verification, u.status, " +
                "p.nom_entreprise, p.type_activite, p.site_web, p.adresse, p.telephone AS partenaire_telephone, p.autres_documents, p.etat, p.date_expiration " +
                "FROM utilisateurs u " +
                "JOIN partenaires p ON u.id_utilisateur = p.id_partenaire " +
                "WHERE p.id_partenaire = ?";
        try (Connection connection = connexionBD.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new PartenaireData(
                        resultSet.getInt("id_utilisateur"),
                        resultSet.getString("nom_entreprise"),
                        resultSet.getString("type_activite"),
                        resultSet.getString("site_web"),
                        resultSet.getString("adresse"),
                        resultSet.getString("partenaire_telephone"),
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
    public PartenaireData getPartenaireGenerale(int id) {
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
    public PartenaireProfileModel getPartenaireById(int id) {
        String query = "SELECT p.*, u.avatar FROM partenaires p JOIN utilisateurs u ON p.id_partenaire = u.id_utilisateur WHERE p.id_partenaire = ?";
        try (Connection connection = connexionBD.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new PartenaireProfileModel(
                        resultSet.getInt("id_partenaire"),
                        resultSet.getString("nom_entreprise"),
                        resultSet.getString("type_activite"),
                        resultSet.getString("site_web"),
                        resultSet.getString("adresse"),
                        resultSet.getString("telephone"),
                        resultSet.getString("autres_documents"),
                        resultSet.getInt("etat"),
                        resultSet.getDate("date_expiration"),
                        resultSet.getString("avatar")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public PartenaireProfileModel getPartenaireByUserId(int userId) {
        String query = "SELECT p.*, u.avatar FROM partenaires p JOIN utilisateurs u ON p.id_partenaire = u.id_utilisateur WHERE u.id_utilisateur = ?";
        try (Connection connection = connexionBD.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new PartenaireProfileModel(
                        resultSet.getInt("id_partenaire"),
                        resultSet.getString("nom_entreprise"),
                        resultSet.getString("type_activite"),
                        resultSet.getString("site_web"),
                        resultSet.getString("adresse"),
                        resultSet.getString("telephone"),
                        resultSet.getString("autres_documents"),
                        resultSet.getInt("etat"),
                        resultSet.getDate("date_expiration"),
                        resultSet.getString("avatar")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updatePartenaire(PartenaireProfileModel partenaire) {
        String updatePartenaireQuery = "UPDATE partenaires SET nom_entreprise = ?, telephone = ?, adresse = ?, site_web = ? WHERE id_partenaire = ?";

        try (Connection connection = connexionBD.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updatePartenaireQuery)) {
            preparedStatement.setString(1, partenaire.getNomEntreprise());
            preparedStatement.setString(2, partenaire.getTelephone());
            preparedStatement.setString(3, partenaire.getAdresse());
            preparedStatement.setString(4, partenaire.getSiteWeb());
            preparedStatement.setInt(5, partenaire.getIdPartenaire());

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

    @Override
    public void updateProfile(User user, PartenaireData partenaireData) {
        String updateUserQuery = "UPDATE utilisateurs SET nom = ?, prenom = ?, email = ?, mot_de_passe = ?, telephone = ?, avatar = ?, statut_verification = ?, status = ? WHERE id_utilisateur = ?";
        String updatePartenaireQuery = "UPDATE partenaires SET nom_entreprise = ?, type_activite = ?, site_web = ?, adresse = ?, telephone = ?, autres_documents = ?, etat = ?, date_expiration = ? WHERE id_partenaire = ?";

        try (Connection connection = connexionBD.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement userStatement = connection.prepareStatement(updateUserQuery);
                 PreparedStatement partenaireStatement = connection.prepareStatement(updatePartenaireQuery)) {

                // Update utilisateurs table
                userStatement.setString(1, user.getNom());
                userStatement.setString(2, user.getPrenom());
                userStatement.setString(3, user.getEmail());
                userStatement.setString(4, user.getMotDePasse());
                userStatement.setString(5, user.getTelephone());
                userStatement.setString(6, user.getAvatar());
                userStatement.setString(7, user.getStatutVerification());
                userStatement.setString(8, user.getStatus());
                userStatement.setInt(9, user.getIdUtilisateur());
                userStatement.executeUpdate();

                // Update partenaires table
                partenaireStatement.setString(1, partenaireData.getNomEntreprise());
                partenaireStatement.setString(2, partenaireData.getTypeActivite());
                partenaireStatement.setString(3, partenaireData.getSiteWeb());
                partenaireStatement.setString(4, partenaireData.getAdresse());
                partenaireStatement.setString(5, partenaireData.getTelephone());
                partenaireStatement.setString(6, partenaireData.getAutresDocuments());
                partenaireStatement.setInt(7, partenaireData.getEtat());
                partenaireStatement.setDate(8, new java.sql.Date(partenaireData.getDateExpiration().getTime()));
                partenaireStatement.setInt(9, partenaireData.getIdPartenaire());
                partenaireStatement.executeUpdate();

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}