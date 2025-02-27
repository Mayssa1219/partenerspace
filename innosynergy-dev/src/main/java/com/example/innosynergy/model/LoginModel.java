package com.example.innosynergy.model;

public class LoginModel {

    // Méthode pour authentifier l'utilisateur
    public boolean authenticateUser(String email, String password) {
        // Exemple simple de validation des informations de connexion
        // Vous pouvez remplacer cette logique par une connexion à une base de données
        if ("test@example.com".equals(email) && "password123".equals(password)) {
            return true;  // Connexion réussie
        } else {
            return false;  // Connexion échouée
        }
    }

    // Méthode pour envoyer un email de réinitialisation du mot de passe
    public void sendPasswordResetEmail(String email) {
        // Logique pour envoyer un email de réinitialisation de mot de passe
        // Cette méthode pourrait appeler un service d'envoi d'email
        System.out.println("Un email de réinitialisation du mot de passe a été envoyé à " + email);
    }
}