package com.example.innosynergy.controller;

import com.example.innosynergy.model.LoginModel;
import com.example.innosynergy.view.LoginView;

public class LoginController {
    private final LoginModel model;
    private LoginView view;

    // Constructeur
    public LoginController(LoginModel model, LoginView view) {
        this.model = model;
        this.view = view;
    }

    // Méthode pour gérer la connexion
    public void handleLogin(String email, String password) {
        // Vérification des informations de connexion
        if (model.authenticateUser(email, password)) {
            System.out.println("Connexion réussie");
            // Gérer la redirection ou l'action après une connexion réussie
        } else {
            System.out.println("Identifiants incorrects");
            // Afficher un message d'erreur ou une autre action en cas d'échec
        }
    }

    // Méthode pour gérer la demande de réinitialisation de mot de passe
    public void handleForgotPassword() {
        System.out.println("Mot de passe oublié - Demande de réinitialisation");
        // Gérer la réinitialisation du mot de passe, par exemple envoyer un e-mail
    }

    // Setter pour la vue
    public void setView(LoginView view) {
        this.view = view;
    }
}