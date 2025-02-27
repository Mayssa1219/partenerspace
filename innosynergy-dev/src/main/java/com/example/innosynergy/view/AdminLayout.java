package com.example.innosynergy.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminLayout extends Application {
    private BorderPane mainLayout;
    private Label titleLabel; // Titre dynamique dans la navbar
    private VBox sidebar;
    private Button selectedButton; // Bouton actuellement sélectionné
    private ScrollPane scrollPane; // ScrollPane pour le contenu central
    private boolean isSidebarVisible = true; // État de visibilité de la sidebar

    // Constantes de style pour les boutons de la sidebar
    private static final String DEFAULT_BUTTON_STYLE = "-fx-text-fill: #747374; -fx-background-color: transparent; -fx-font-size: 15px; -fx-pref-width: 220px; -fx-pref-height: 54px;";
    private static final String SELECTED_BUTTON_STYLE = "-fx-text-fill: #ffffff; -fx-background-color: #81A8C5; -fx-font-size: 15px; -fx-pref-width: 400px; -fx-pref-height: 25px; -fx-background-radius: 15px;";
    private static final String DROPDOWN_BUTTON_STYLE = "-fx-text-fill: #81A8C5; -fx-background-color: transparent; -fx-font-size: 15px; -fx-pref-width: 220px; -fx-pref-height: 54px; -fx-padding: 0 0 0 20px;";
    private static final String BLACK_TEXT_STYLE = "-fx-text-fill: black;";

    @Override
    public void start(Stage primaryStage) {
        // Création de la navbar
        HBox navbar = new HBox(20);
        navbar.setPadding(new Insets(10));
        navbar.setStyle("-fx-background-color: #ffffff; -fx-pref-height: 60px;");
        navbar.setAlignment(Pos.CENTER_RIGHT);

        // Logo + titre dynamique
        ImageView logo = new ImageView(new Image("images/logo miravia 1 3.png"));
        logo.setFitWidth(150);
        logo.setFitHeight(45);

        Label title = new Label("Tableau de bord");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #013A71;");
        this.titleLabel = title;
        title.setPadding(new Insets(0, 0, 0, 80));  // Ajout du padding à gauche

        HBox logoBox = new HBox(10, logo, title);
        logoBox.setAlignment(Pos.CENTER_LEFT);
        logoBox.setPadding(new Insets(0, 0, 0, 30));
        logoBox.setOnMouseClicked(event -> toggleSidebarVisibility()); // Ajout de l'événement de clic pour le logo

        // Barre de recherche
        HBox searchBox = new HBox(10);
        searchBox.setStyle("-fx-background-color: #F9FAFB; -fx-pref-width: 400px; -fx-background-radius: 16px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);");
        searchBox.setAlignment(Pos.CENTER_LEFT);

        ImageView searchIcon = new ImageView(new Image("images/icons_search.png"));
        searchIcon.setFitWidth(28);
        searchIcon.setFitHeight(28);

        TextField searchField = new TextField();
        searchField.setPromptText("Rechercher...");
        searchField.setStyle("-fx-background-color: #F9FAFB; -fx-border: none; -fx-pref-width: 400px; -fx-background-radius: 16px");

        searchBox.getChildren().addAll(searchIcon, searchField);

        // Avatar utilisateur
        ImageView userAvatar = new ImageView(new Image("images/profile image.png"));
        userAvatar.setFitWidth(40);
        userAvatar.setFitHeight(40);

        // Ajout du gestionnaire d'événements pour l'image de profil
        userAvatar.setOnMouseClicked(event -> {
            setProfileContent();
        });

        Label userName = new Label("Maddy Adams");

        // Icône des notifications
        ImageView notificationsIcon = new ImageView(new Image("images/icons8-notification-128.png"));
        notificationsIcon.setFitWidth(30);
        notificationsIcon.setFitHeight(30);

        // Espaceur pour aligner les éléments
        Region spacerNavbar = new Region();
        HBox.setHgrow(spacerNavbar, Priority.ALWAYS);

        // Ajouter les éléments dans l'ordre correct dans la navbar
        navbar.getChildren().addAll(logoBox, spacerNavbar, searchBox, notificationsIcon, userAvatar, userName);

        // Création de la sidebar
        sidebar = new VBox(0);
        sidebar.setPadding(new Insets(5, 0, 35, 5)); // Ajout du padding en bas
        sidebar.setStyle("-fx-background-color: #ffffff; -fx-pref-width: 280px;");
        sidebar.setMaxWidth(Region.USE_PREF_SIZE);

        // Création des boutons de la sidebar
        Button dashboardButton = createButton("Tableau de bord", "images/Dashboard.png", 20, 20, "images/Rectangle 3.png", 50, 50);
        VBox usersDropdown = createDropdownButton("Gérer Utilisateur", "images/user-groups.png", 22, 22, "images/Rectangle 3.png", 50, 50);
        Button partnersButton = createButton("Demandes Partenaire", "images/demande_partenaire.png", 25, 25, "images/Rectangle 3.png", 50, 50);
        Button helpButton = createButton("Demandes d'aide", "images/ask.png", 20, 20, "images/Rectangle 3.png", 50, 50);
        Button donButton = createButton("Don", "images/icon_money.png", 25, 25, "images/Rectangle 3.png", 50, 50);
        Button eventsButton = createButton("Événements", "images/event.png", 20, 20, "images/Rectangle 3.png", 50, 50);
        Button BenevolButton = createButton("Bénévolat", "images/don.png", 26, 26, "images/Rectangle 3.png", 50, 50);
        Button settingsButton = createButton("Paramètres", "images/settings.png", 24, 24, "images/Rectangle 3.png", 50, 50);
        Button messagesButton = createButton("Messagerie", "images/chat.png", 22, 22, "images/Rectangle 3.png", 50, 50);

        sidebar.getChildren().addAll(dashboardButton, usersDropdown, partnersButton, helpButton, donButton, eventsButton, BenevolButton, settingsButton, messagesButton);

        // Setup scroll pane for sidebar
        ScrollPane sidebarScrollPane = new ScrollPane(sidebar);
        sidebarScrollPane.setFitToWidth(true);
        sidebarScrollPane.setStyle("-fx-background-color: transparent;");

        // Mise en place du layout principal
        mainLayout = new BorderPane();
        mainLayout.setLeft(sidebarScrollPane);
        mainLayout.setTop(navbar);

        // Création du ScrollPane pour le contenu central
        scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        mainLayout.setCenter(scrollPane);

        // Définir par défaut le contenu central sur le tableau de bord
        setDashboardContent();

        // Créer la scène et l'afficher
        primaryStage.setScene(getScene());
        primaryStage.setTitle("Admin Dashboard");
        primaryStage.show();
    }

    /**
     * Crée un bouton avec une icône et du texte alignés correctement.
     */
    private Button createButton(String text, String smallImagePath, double smallImageWidth, double smallImageHeight, String rectangleImagePath, double rectangleImageWidth, double rectangleImageHeight) {
        // Image de fond (rectangle)
        ImageView rectangleImageView = new ImageView(new Image(rectangleImagePath));
        rectangleImageView.setFitWidth(rectangleImageWidth);
        rectangleImageView.setFitHeight(rectangleImageHeight);

        // Petite image centrée dans le rectangle
        ImageView smallImageView = new ImageView(new Image(smallImagePath));
        smallImageView.setFitWidth(smallImageWidth);
        smallImageView.setFitHeight(smallImageHeight);

        StackPane iconPane = new StackPane(rectangleImageView, smallImageView);
        StackPane.setAlignment(smallImageView, Pos.TOP_CENTER);
        StackPane.setMargin(smallImageView, new Insets(5, 0, 17, 0));

        StackPane.setAlignment(smallImageView, Pos.CENTER);

        // Conteneur du bouton avec icône et texte alignés correctement
        Label buttonText = new Label(text);
        HBox buttonContent = new HBox(10, iconPane, buttonText);
        buttonContent.setAlignment(Pos.CENTER_LEFT);
        buttonContent.setPadding(new Insets(5, 0, 5, 15));

        // Création du bouton
        Button button = new Button();
        button.setGraphic(buttonContent);
        button.setStyle(DEFAULT_BUTTON_STYLE);
        button.setMaxWidth(Double.MAX_VALUE); // Permet au bouton de s'étirer en largeur

        // Gestion du clic : mise à jour du titre et du style du bouton sélectionné
        button.setOnAction(e -> {
            updateTitle(text);
            updateSelectedButton(button);
            updateContentBasedOnButton(text); // Mise à jour du contenu en fonction du bouton
        });

        return button;
    }

    /**
     * Crée un VBox contenant un bouton principal et des sous-boutons dropdown.
     */
    private VBox createDropdownButton(String text, String smallImagePath, double smallImageWidth, double smallImageHeight, String rectangleImagePath, double rectangleImageWidth, double rectangleImageHeight) {
        VBox container = new VBox(0);

        // Image de fond (rectangle)
        ImageView rectangleImageView = new ImageView(new Image(rectangleImagePath));
        rectangleImageView.setFitWidth(rectangleImageWidth);
        rectangleImageView.setFitHeight(rectangleImageHeight);

        // Petite image centrée dans le rectangle
        ImageView smallImageView = new ImageView(new Image(smallImagePath));
        smallImageView.setFitWidth(smallImageWidth);
        smallImageView.setFitHeight(smallImageHeight);

        StackPane iconPane = new StackPane(rectangleImageView, smallImageView);
        StackPane.setAlignment(smallImageView, Pos.CENTER);

        // Conteneur du bouton avec icône et texte alignés correctement
        Label buttonText = new Label(text);
        buttonText.setStyle(BLACK_TEXT_STYLE); // Set text color to black
        HBox buttonContent = new HBox(10, iconPane, buttonText);
        buttonContent.setAlignment(Pos.CENTER_LEFT);
        buttonContent.setPadding(new Insets(5, 0, 5, 15));

        // Création du bouton principal
        Button mainButton = new Button();
        mainButton.setGraphic(buttonContent);
        mainButton.setStyle(DEFAULT_BUTTON_STYLE);
        mainButton.setMaxWidth(Double.MAX_VALUE); // Permet au bouton de s'étirer en largeur

        // Sous-boutons du dropdown
        Button partnersButton = createDropdownSubButton("Partenaires");
        Button clientsButton = createDropdownSubButton("Clients");

        // Conteneur pour les sous-boutons (initialement masqué)
        VBox dropdownContent = new VBox(0, partnersButton, clientsButton);
        dropdownContent.setPadding(new Insets(0, 0, 0, 0)); // No padding to avoid extra space
        dropdownContent.setSpacing(0); // No spacing to avoid extra space
        dropdownContent.setVisible(false);

        // Afficher/masquer le dropdown lors du clic sur le bouton principal
        mainButton.setOnAction(e -> {
            boolean isVisible = !dropdownContent.isVisible();
            dropdownContent.setVisible(isVisible);
            if (isVisible) {
                container.getChildren().add(dropdownContent);
                mainButton.setStyle(SELECTED_BUTTON_STYLE);
                updateSelectedButton(mainButton);
            } else {
                container.getChildren().remove(dropdownContent);
                mainButton.setStyle(DEFAULT_BUTTON_STYLE);
            }
        });

        container.getChildren().addAll(mainButton);

        return container;
    }

    private Button createDropdownSubButton(String text) {
        Button button = new Button(text);
        button.setStyle(DROPDOWN_BUTTON_STYLE);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setOnAction(e -> {
            updateTitle(text);
            updateContentBasedOnButton(text);
            updateSelectedButton(button); // Update the selected button style
        });
        return button;
    }

    // Met à jour le titre de la navbar
    private void updateTitle(String newTitle) {
        titleLabel.setText(newTitle);
    }

    // Gère le changement de style du bouton sélectionné
    private void updateSelectedButton(Button button) {
        if (selectedButton != null) {
            selectedButton.setStyle(DEFAULT_BUTTON_STYLE);
        }
        selectedButton = button;
        selectedButton.setStyle(SELECTED_BUTTON_STYLE);
    }

    // Met à jour le contenu central de l'application en fonction du bouton sélectionné
    private void updateContentBasedOnButton(String buttonText) {
        switch (buttonText) {
            case "Tableau de bord":
                setDashboardContent();
                break;
            case "Profil":
                setProfileContent();
                break;
            case "Gérer Utilisateur":
            case "Partenaires":
            case "Clients":
                setUserManagementContent(buttonText);
                break;
            case "Demandes Partenaire":
                setDemandeContent();
                break;
            case "Demandes d'aide":
                setDemandeAideContent();
                break;
            case "Événements":
                setEventContent();
                break;
            case "Bénévolat":
                setBenevolContent();
                break;
            case "Paramètres":
                setSettingContent();
                break;
            case "Messagerie":
                setMessengerContent();
                break;

            // Ajoutez d'autres cases selon les besoins
        }
    }

    // Change le contenu central pour le tableau de bord
    private void setDashboardContent() {
        StackPane dashboardContent = new StackPane(new Label("Contenu du tableau de bord"));
        setCenterContent(dashboardContent);
        setCenterContent(new Dashboard());
    }


    // Change le contenu central pour le profil
    private void setProfileContent() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MiraVia/ProfileView.fxml"));
            Parent messengerContent = loader.load(); // Utiliser Parent au lieu de VBox
            setCenterContent(messengerContent); // Ajouter à la zone centrale
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    // Change le contenu central pour la gestion des utilisateurs
    private void setUserManagementContent(String userType) {
        if (userType.equals("Partenaires")) {
            setCenterContent(new GererPartenaire());
        } else {
            setCenterContent(new GererClient());
        }
    }


    // Change le contenu central pour la gestion des demandes
    private void setDemandeContent() {
        StackPane demandeContent = new StackPane(new Label("Demandes de partenaires"));
        setCenterContent(demandeContent);
        setCenterContent(new Demande());
    }

    private void setDemandeAideContent() {
        StackPane demandeAideContent = new StackPane(new Label("Demandes d'aide"));
        setCenterContent(demandeAideContent);
        setCenterContent(new DemandeAide());
    }

    // Change le contenu central pour les événements
    private void setEventContent() {
        StackPane eventContent = new StackPane(new Label("Événements"));
        setCenterContent(eventContent);
        setCenterContent(new EventView());
    }

    // Change le contenu central pour le bénévolat
    private void setBenevolContent() {
        StackPane benevolContent = new StackPane(new Label("Bénévolat"));
        setCenterContent(benevolContent);
        setCenterContent(new BenevolView());
    }

    // Change le contenu central pour les paramètres
    private void setSettingContent() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MiraVia/SettingsView.fxml"));
            Parent messengerContent = loader.load(); // Utiliser Parent au lieu de VBox
            setCenterContent(messengerContent); // Ajouter à la zone centrale
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Modifier la méthode pour accepter n'importe quel type de Node
    public void setCenterContent(Node content) {
        scrollPane.setContent(content);
    }

    // Change le contenu central pour la messagerie
    private void setMessengerContent() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MiraVia/MessagerieView.fxml"));
            Parent messengerContent = loader.load(); // Utiliser Parent au lieu de VBox
            setCenterContent(messengerContent); // Ajouter à la zone centrale
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Met le contenu dans la zone centrale du layout
    private void setCenterContent(StackPane content) {
        scrollPane.setContent(content);
    }

    // Retourne la scène principale
    private Scene getScene() {
        return new Scene(mainLayout, 1200, 800);
    }

    /**
     * Bascule la visibilité de la sidebar.
     */
    private void toggleSidebarVisibility() {
        if (isSidebarVisible) {
            mainLayout.setLeft(null);
        } else {
            mainLayout.setLeft(new ScrollPane(sidebar));
        }
        isSidebarVisible = !isSidebarVisible;
    }

    public static void main(String[] args) {
        launch(args);
    }
}