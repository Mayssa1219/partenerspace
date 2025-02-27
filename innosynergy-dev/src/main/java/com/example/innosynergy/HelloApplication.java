package com.example.innosynergy;

import com.example.innosynergy.controller.LoginController;
import com.example.innosynergy.model.LoginModel;
import com.example.innosynergy.view.LoginView;
import javafx.application.Application;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) {
        // Création du modèle de connexion
        LoginModel model = new LoginModel();

        // Création du contrôleur de connexion et enregistrement du modèle
        LoginController controller = new LoginController(model, null);

        // Création de la vue et enregistrement du contrôleur
        LoginView view = new LoginView(controller);
        controller.setView(view); // Le contrôleur connaît la vue

        // Titre de la fenêtre
        stage.setTitle("Login Page");

        // Initialisation de la scène avec la vue
        stage.setScene(view.createScene(stage));

        // Affichage de la fenêtre
        stage.show();
    }

    public static void main(String[] args) {
        // Lancer l'application JavaFX
        launch();
    }
}
