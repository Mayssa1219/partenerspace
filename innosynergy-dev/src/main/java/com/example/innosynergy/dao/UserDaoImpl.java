package com.example.innosynergy.dao;

import com.example.innosynergy.model.User;
import com.example.innosynergy.config.ConnexionBD;
import com.example.innosynergy.utils.PasswordUtil;
import org.mindrot.jbcrypt.BCrypt;

import java.net.URI;
import java.awt.Desktop;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {
    private ConnexionBD connexionBD;

    public UserDaoImpl() {
        this.connexionBD = new ConnexionBD();
    }

    @Override
    public User authenticateUser(String email, String password) {
        String query = "SELECT * FROM utilisateurs WHERE email = ?";
        try (Connection connection = connexionBD.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String storedHash = resultSet.getString("mot_de_passe");
                if (PasswordUtil.checkPassword(password, storedHash)) {
                    return new User(
                            resultSet.getInt("id_utilisateur"),
                            resultSet.getString("nom"),
                            resultSet.getString("prenom"),
                            resultSet.getString("email"),
                            resultSet.getString("mot_de_passe"),
                            resultSet.getString("telephone"),
                            resultSet.getString("avatar"),
                            resultSet.getString("statut_verification"),
                            resultSet.getString("status"),
                            resultSet.getString("type_utilisateur")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public User authenticateSansHachage(String email, String password) {
        String query = "SELECT * FROM utilisateurs WHERE email = ? AND mot_de_passe = ?";
        try (Connection connection = connexionBD.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new User(
                        resultSet.getInt("id_utilisateur"),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getString("email"),
                        resultSet.getString("mot_de_passe"),
                        resultSet.getString("telephone"),
                        resultSet.getString("avatar"),
                        resultSet.getString("statut_verification"),
                        resultSet.getString("status"),
                        resultSet.getString("type_utilisateur")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean registerUser(User user) {
        String query = "INSERT INTO utilisateurs (nom, prenom, email, mot_de_passe, telephone, avatar, statut_verification, status, type_utilisateur) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = connexionBD.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user.getPrenom());
            preparedStatement.setString(2, user.getNom());
            preparedStatement.setString(3, user.getEmail());

            // Hash the password using PasswordUtil before storing it
            String hashedPassword = PasswordUtil.hashPassword(user.getMotDePasse());
            preparedStatement.setString(4, hashedPassword);

            preparedStatement.setString(5, user.getTelephone());
            preparedStatement.setString(6, user.getAvatar());
            preparedStatement.setString(7, user.getStatutVerification());
            preparedStatement.setString(8, user.getStatus());
            preparedStatement.setString(9, user.getTypeUtilisateur());

            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void sendPasswordResetEmail(String email) {
        // Logic to send a password reset email
        System.out.println("Un email de réinitialisation du mot de passe a été envoyé à " + email);
    }

    @Override
    public void initiateGoogleLogin(String googleLoginUrl) {
        try {
            URI uri = new URI(googleLoginUrl);
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(uri);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}