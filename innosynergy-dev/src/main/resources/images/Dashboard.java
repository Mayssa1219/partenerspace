package images;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Dashboard extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Sidebar
        VBox sidebar = new VBox(20);
        sidebar.setPadding(new Insets(5));
        sidebar.setStyle("-fx-background-color: #ffffff; -fx-pref-width: 200px;");

        // Buttons with different small images
        Button dashboardButton = createButton("Tableau de bord", "images/chart.png", "images/Rectangle 3.png");
        Button usersButton = createButton("Utilisateurs", "images/usericon.png", "images/Rectangle 3.png");
        Button statsButton = createButton("Statistiques", "images/demandeicon.png", "images/Rectangle 3.png");
        Button eventsButton = createButton("Événements", "images/eventicon.png", "images/Rectangle 3.png");
        Button messagesButton = createButton("Messages", "images/messageicon.png", "images/Rectangle 3.png");
        Button settingsButton = createButton("Paramètres", "images/settingicon.png", "images/Rectangle 3.png");

        // Add buttons to the sidebar
        sidebar.getChildren().addAll(dashboardButton, usersButton, statsButton, eventsButton, messagesButton, settingsButton, logoutButton);

        // Navbar
        HBox navbar = new HBox(20);
        navbar.setPadding(new Insets(10));
        navbar.setStyle("-fx-background-color: #ffffff; -fx-pref-height: 60px;");
        navbar.setAlignment(Pos.CENTER_RIGHT);

        // Logo and MiraVia text
        ImageView logo = new ImageView(new Image("images/logo miravia 1 3.png"));
        logo.setFitWidth(150);
        logo.setFitHeight(50);

// Create a container for the logo and apply padding
        HBox logoBox = new HBox(10, logo);  // Ajouter ici un espace de 10px entre les éléments si nécessaire
        logoBox.setPadding(new Insets(0, 0, 0, 20));  // Padding à gauche de 20px
        logoBox.setAlignment(Pos.CENTER_LEFT);
        Label logoText = new Label("Tableau de bord");
        logoText.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #013A71; -fx-pref-width: 287px; -fx-pref-height: 50px; -fx-padding: 0 0 0 35px;");
        HBox logoBox = new HBox(10, logo, logoText);
        logoBox.setAlignment(Pos.CENTER_LEFT);

        // Search bar
        HBox searchBox = new HBox(10);
        searchBox.setStyle("-fx-background-color: #F9FAFB; -fx-pref-width: 628px; -fx-pref-height: 57px; -fx-padding: 0 0 0 35px;");
        searchBox.setAlignment(Pos.CENTER_LEFT);

        ImageView searchIcon = new ImageView(new Image("images/serach_icon-removebg-preview.png"));
        searchIcon.setFitWidth(32);
        searchIcon.setFitHeight(32);

        TextField searchField = new TextField();
        searchField.setPromptText("Search here...");
        searchField.setStyle("-fx-background-color: #F9FAFB; -fx-border: none; -fx-pref-width: 500px; -fx-pref-height: 57px;");

        searchBox.getChildren().addAll(searchIcon, searchField);

        ImageView userAvatar = new ImageView(new Image("images/profile image.png"));
        userAvatar.setFitWidth(40);
        userAvatar.setFitHeight(40);
        userAvatar.setStyle("-fx-background-radius: 50%;");

        Label userName = new Label("Maddy Adams");
        userName.setStyle("-fx-font-size: 16px;");

        HBox spacer = new HBox();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

        navbar.getChildren().addAll(logoBox, spacer, searchBox, userAvatar, userName);

        // Main layout
        BorderPane mainLayout = new BorderPane();
        mainLayout.setLeft(sidebar);
        mainLayout.setTop(navbar);

        Scene scene = new Scene(mainLayout, 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Tableau de bord");
        primaryStage.show();
    }

    // Helper method to create buttons with specific small icons
    private Button createButton(String text, String smallImagePath, String rectangleImagePath) {
        ImageView rectangleImageView = new ImageView(new Image(rectangleImagePath));
        rectangleImageView.setFitWidth(50);
        rectangleImageView.setFitHeight(50);

        Image smallImage = new Image(smallImagePath);
        ImageView smallImageView = new ImageView(smallImage);
        smallImageView.setFitWidth(15);
        smallImageView.setFitHeight(15);

        // StackPane to layer the 50x50 rectangle and the 15x15 small image
        StackPane iconPane = new StackPane();
        iconPane.getChildren().addAll(rectangleImageView, smallImageView);
        StackPane.setMargin(smallImageView, new Insets(0, 0, 0, 0)); // Adjust the position of the small icon

        Button button = new Button(text);
        button.setStyle("-fx-text-fill: #4b5563; -fx-background-color: transparent; -fx-font-size: 15px;");
        button.setGraphic(iconPane);

        return button;
    }

    public static void main(String[] args) {
        launch(args);
    }
}