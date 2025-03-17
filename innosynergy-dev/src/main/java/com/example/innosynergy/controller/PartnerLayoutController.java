package com.example.innosynergy.controller;

import com.example.innosynergy.Main;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.MouseEvent;
import com.example.innosynergy.dao.DonDaoImpl;
import com.example.innosynergy.dao.EventDaoImpl;
import com.example.innosynergy.dao.PartenaireDaoImpl;
import com.example.innosynergy.model.Don;
import com.example.innosynergy.model.Event;
import com.example.innosynergy.model.User;
import com.example.innosynergy.utils.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class PartnerLayoutController {

    @FXML
    private BorderPane mainLayout;

    @FXML
    private HBox logoBox;

    @FXML
    private Label titleLabel;

    @FXML
    private ScrollPane sidebarScrollPane;

    @FXML
    private VBox sidebar;

    @FXML
    private Button dashboardButton, helpRequestsButton, consultButton, donButton,
            eventsButton, benevolButton, settingsButton, messagesButton;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private ImageView profileImageView, notificationsImageView, searchImageView, notificationIcon;

    @FXML
    private Label profileNameLabel;

    private PartenaireDaoImpl partenaireDao;

    private final Map<Button, String[]> buttonViewMappings = new HashMap<>();

    @FXML
    private ImageView chatbotImageView;

    private Button lastActiveButton = null; // Pour garder une référence au dernier bouton actif
    @FXML
    private Label timeLabel;

    private final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");

    @FXML
    private void initialize() {
        // Mise à jour de l'heure toutes les secondes
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> updateClock())
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        // Gestionnaire d'événements pour le chatbot
        chatbotImageView.setOnMouseClicked(event -> openChatbotWindow());

        // Associer les boutons aux vues correspondantes
        buttonViewMappings.put(dashboardButton, new String[]{"Tableau de bord", "/MiraVia/dashboard.fxml"});
        buttonViewMappings.put(helpRequestsButton, new String[]{"Demandes d'aide", "/MiraVia/DemandeAideView.fxml"});
        buttonViewMappings.put(consultButton, new String[]{"Consulter", null});
        buttonViewMappings.put(donButton, new String[]{"Don", "/MiraVia/DonView.fxml"});
        buttonViewMappings.put(eventsButton, new String[]{"Événements", "/MiraVia/EvenementView.fxml"});
        buttonViewMappings.put(benevolButton, new String[]{"Bénévolat", "/MiraVia/BenevolatView.fxml"});
        buttonViewMappings.put(settingsButton, new String[]{"Paramètres", "/MiraVia/SettingsView.fxml"});
        buttonViewMappings.put(messagesButton, new String[]{"Messagerie", "/MiraVia/MessagerieView.fxml"});

        // Attacher les événements à chaque bouton
        buttonViewMappings.forEach((button, viewInfo) -> {
            if (button != null) {
                button.setOnAction(event -> handleButtonClick(button));
            } else {
                System.err.println("Bouton non injecté dans FXML !");
            }
        });

        // Appliquer le style actif au premier bouton par défaut
        if (!sidebar.getChildren().isEmpty() && sidebar.getChildren().get(0) instanceof Button) {
            Button firstButton = (Button) sidebar.getChildren().get(0);
            firstButton.getStyleClass().add("active-button");
            lastActiveButton = firstButton;
        }

        // Charger le tableau de bord par défaut
        loadView("Tableau de bord", "/MiraVia/dashboard.fxml");

        // Gestionnaire d'événements pour l'icône des notifications
        notificationIcon.setOnMouseClicked(event -> {
            try {
                showNotificationPopup(notificationIcon); // Afficher la popup de notification
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        partenaireDao = new PartenaireDaoImpl();
        profileImageView.setImage(new Image("/images/user.png"));
        String sessionId = SessionManager.getCurrentSessionId();
        User currentUser = SessionManager.getUser(sessionId);
        if (currentUser != null) {
            String imageName = partenaireDao.getUserImage(currentUser.getIdUtilisateur());
            if (imageName != null) {
                profileImageView.setImage(new Image("file:uploads/" + imageName));
            }
            if (currentUser.getPrenom() != null && !currentUser.getPrenom().isEmpty()) {
                profileNameLabel.setText(currentUser.getNom() + " " + currentUser.getPrenom());
            } else {
                profileNameLabel.setText(currentUser.getNom());
            }
        }

        eventDao = new EventDaoImpl();
        donDao = new DonDaoImpl();
    }

    private void handleButtonClick(Button clickedButton) {
        // Réinitialiser le style du dernier bouton actif
        if (lastActiveButton != null) {
            lastActiveButton.getStyleClass().remove("active-button");
        }

        // Appliquer le style au bouton cliqué
        clickedButton.getStyleClass().add("active-button");

        // Mettre à jour la référence du dernier bouton actif
        lastActiveButton = clickedButton;

        // Charger la vue associée au bouton (si nécessaire)
        String[] viewInfo = buttonViewMappings.get(clickedButton);
        if (viewInfo != null) {
            loadView(viewInfo[0], viewInfo[1]);
        }
    }
    private void updateClock() {
        timeLabel.setText(LocalTime.now().format(timeFormat));
    }
    private Stage clockStage = null; // Stocke l'instance de la fenêtre de l'horloge

    public void openClock() {
        try {
            if (clockStage != null && clockStage.isShowing()) {
                clockStage.close(); // Ferme l'horloge si elle est déjà ouverte
                clockStage = null;
                return;
            }

            Main clockApp = new Main(); // Crée une instance de l'horloge
            clockStage = new Stage(); // Initialise la fenêtre

            clockApp.start(clockStage); // Démarre l'horloge

            clockStage.setOnHidden(event -> clockStage = null); // Réinitialise lorsque la fenêtre se ferme

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void openChatbotWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MiraVia/ChatbotView.fxml"));
            Scene chatbotScene = new Scene(loader.load());
            chatbotScene.getStylesheets().add(getClass().getResource("/MiraVia/styles/style.css").toExternalForm()); // Appliquer le CSS

            Stage chatbotStage = new Stage();
            chatbotStage.setTitle("Chatbot");
            chatbotStage.initModality(Modality.APPLICATION_MODAL);
            chatbotStage.setScene(chatbotScene);
            chatbotStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private EventDaoImpl eventDao;

    private DonDaoImpl donDao;

    @FXML
    private void handleLogout() {
        // Clear the session
        SessionManager.invalidateSession(SessionManager.getCurrentSessionId());

        // Load the login view
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MiraVia/LoginView.fxml"));
            Parent loginView = loader.load();
            Scene loginScene = new Scene(loginView);

            // Get the current stage
            Stage stage = (Stage) mainLayout.getScene().getWindow();
            stage.setScene(loginScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showNotificationPopup(Node anchor) throws IOException {
        // Charger le fichier FXML pour la barre de notification
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MiraVia/NotificationBarView.fxml"));
        Parent notificationContent = loader.load();

        // Créer un Popup et ajouter le contenu de notification
        Popup popup = new Popup();
        popup.getContent().add(notificationContent);
        popup.setAutoHide(true);

        // Positionner la popup sous l'icône de notification
        popup.show(anchor.getScene().getWindow(), anchor.getScene().getWindow().getX() + anchor.getLayoutX(), anchor.getScene().getWindow().getY() + anchor.getLayoutY() + anchor.getBoundsInParent().getHeight());
    }

    private void loadView(String title, String fxmlPath) {
        titleLabel.setText(title);
        if (fxmlPath == null) {
            scrollPane.setContent(new Label("Contenu à venir pour " + title));
            return;
        }
        try {
            Node view = FXMLLoader.load(getClass().getResource(fxmlPath));
            scrollPane.setContent(view);
        } catch (IOException e) {
            e.printStackTrace();
            scrollPane.setContent(new Label("Erreur lors du chargement de " + title));
        }
    }

    @FXML
    private void toggleSidebarVisibility() {
        sidebarScrollPane.setVisible(!sidebarScrollPane.isVisible());
    }
}