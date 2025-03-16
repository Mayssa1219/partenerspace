package com.example.innosynergy.dao;

import com.example.innosynergy.model.User;
import com.example.innosynergy.config.ConnexionBD;
import com.example.innosynergy.utils.PasswordUtil;

import java.awt.*;
import java.util.List; // Assurez-vous que cette classe est importée
import java.net.URI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoImpl implements UserDao {
    private ConnexionBD connexionBD;
    private static final Logger logger = Logger.getLogger(UserDaoImpl.class.getName());

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
    public boolean isEmailRegistered(String email) {
        String query = "SELECT COUNT(*) FROM utilisateurs WHERE email = ?";
        try (Connection connection = connexionBD.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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

    @Override
    public boolean updatePassword(String email, String newPassword) {
        String sql = "UPDATE utilisateurs SET mot_de_passe = ? WHERE email = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connexionBD.getConnection();
            if (connection == null || connection.isClosed()) {
                System.out.println("La connexion est fermée ou non disponible.");
                return false;
            }

            preparedStatement = connection.prepareStatement(sql);
            String hashedPassword = PasswordUtil.hashPassword(newPassword);
            preparedStatement.setString(1, hashedPassword);
            preparedStatement.setString(2, email);

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error updating password: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

     @Override
    public User getUserByEmail(String email) {

        User user = null;

        String query = "SELECT * FROM utilisateurs WHERE email = ?";
        try (Connection connection = connexionBD.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user = new User();
                user.setIdUtilisateur(resultSet.getInt("id_utilisateur"));
                user.setNom(resultSet.getString("nom"));
                user.setEmail(resultSet.getString("email"));
                user.setTypeUtilisateur(resultSet.getString("type_utilisateur"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception
        }

        return user;
    }
    @Override
    public String findNameByEmail(String email) {
        String query = "SELECT nom FROM utilisateurs WHERE email = ?";
        try (Connection connection = connexionBD.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("nom");
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error fetching name by email", e);
        }
        return null;
    }

    @Override
    public boolean registerUser(User user) {
        String query = "INSERT INTO utilisateurs (nom, prenom, email, mot_de_passe, telephone, avatar, statut_verification, status, type_utilisateur) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = connexionBD.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user.getPrenom());
            preparedStatement.setString(2, user.getNom());
            preparedStatement.setString(3, user.getEmail());

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
    public boolean updatePasswordProfil(String email, String newPassword) {
        String sql = "UPDATE utilisateurs SET mot_de_passe = ? WHERE email = ?";
        try (Connection connection = connexionBD.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            String hashedPassword = PasswordUtil.hashPassword(newPassword);
            preparedStatement.setString(1, hashedPassword);
            preparedStatement.setString(2, email);

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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
    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM utilisateurs";

        try (Connection connection = connexionBD.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                User user = new User();
                user.setIdUtilisateur(resultSet.getInt("id_utilisateur"));
                user.setNom(resultSet.getString("nom"));
                user.setPrenom(resultSet.getString("prenom"));
                user.setEmail(resultSet.getString("email"));
                user.setMotDePasse(resultSet.getString("mot_de_passe"));
                user.setTelephone(resultSet.getString("telephone"));
                user.setAvatar(resultSet.getString("avatar"));
                user.setStatutVerification(resultSet.getString("statut_verification"));
                user.setStatus(resultSet.getString("status"));
                user.setTypeUtilisateur(resultSet.getString("type_utilisateur"));

                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception
        }

        return users;
    }
    @Override
    public User getUserById(int id) {
        User user = null;
        String query = "SELECT * FROM utilisateurs WHERE id_utilisateur = ?";

        try (Connection connection = connexionBD.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    user = new User();
                    user.setIdUtilisateur(resultSet.getInt("id_utilisateur"));
                    user.setNom(resultSet.getString("nom"));
                    user.setPrenom(resultSet.getString("prenom"));
                    user.setEmail(resultSet.getString("email"));
                    user.setMotDePasse(resultSet.getString("mot_de_passe"));
                    user.setTelephone(resultSet.getString("telephone"));
                    user.setAvatar(resultSet.getString("avatar"));
                    user.setStatutVerification(resultSet.getString("statut_verification"));
                    user.setStatus(resultSet.getString("status"));
                    user.setTypeUtilisateur(resultSet.getString("type_utilisateur"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception
        }

        return user;
    }


}