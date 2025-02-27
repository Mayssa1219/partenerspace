package com.example.innosynergy.view;

import com.example.innosynergy.controller.LoginController;
import javafx.animation.ScaleTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoginView {
    private final LoginController controller;

    public LoginView(LoginController controller) {
        this.controller = controller;
    }

    public Scene createScene(Stage stage) {
        // Left part (image part)
        ImageView leftImage = new ImageView(new Image(getClass().getResource("/images/pexels-daniel-14373733 1 (1).png").toString()));
        leftImage.setFitHeight(780);
        leftImage.setFitWidth(640);

        // Right part (login form)
        VBox rightPane = new VBox(20);
        rightPane.setStyle("-fx-background-color: white; -fx-border-radius: 20px;");
        rightPane.setAlignment(Pos.TOP_CENTER);
        rightPane.setMinWidth(540);
        rightPane.setPrefWidth(640);

        // Logo and welcome text
        HBox logoBox = new HBox(10);
        logoBox.setPadding(new javafx.geometry.Insets(10, 0, 0, 100));
        logoBox.setAlignment(Pos.CENTER);

        ImageView logoImage = new ImageView(new Image(getClass().getResource("/images/logo miravia 1 3.png").toString()));
        logoImage.setFitWidth(308);
        logoImage.setFitHeight(90);

        ImageView vectorImage = new ImageView(new Image(getClass().getResource("/images/Vector.png").toString()));
        vectorImage.setFitWidth(100);
        vectorImage.setFitHeight(50);

        logoBox.getChildren().addAll(logoImage, vectorImage);

        Label welcomeLabel = new Label("Bienvenue");
        welcomeLabel.setTextFill(Color.web("#013A71"));
        welcomeLabel.setFont(Font.font("Poppins", 50));
        welcomeLabel.setMinWidth(210);
        welcomeLabel.setMaxHeight(54);
        welcomeLabel.setAlignment(Pos.TOP_CENTER);

        // Email field
        Label emailLabel = new Label("Email Id:");
        TextField emailField = new TextField();
        emailField.setStyle("-fx-background-color: #EEEEEE; -fx-border-radius: 15px; -fx-padding: 10px;");
        emailField.setPromptText("input@gmail.com");
        emailField.setMinWidth(320);
        emailField.setMaxWidth(420);

        // Password field
        Label passwordLabel = new Label("Mot de passe:");
        PasswordField passwordField = new PasswordField();
        passwordField.setStyle("-fx-background-color: #EEEEEE; -fx-border-radius: 15px; -fx-padding: 10px;");
        passwordField.setPromptText("Enter your password");
        passwordField.setMinWidth(320);
        passwordField.setMaxWidth(420);

        // Forgot password link
        Label forgotPassword = new Label("Mot de passe oublié?");
        forgotPassword.setTextFill(Color.web("#013A71"));
        forgotPassword.setOnMouseClicked(e -> controller.handleForgotPassword());

        // Login button with hover animation
        Button loginButton = new Button("Se connecter");
        loginButton.setStyle("-fx-background-color: #013A71; -fx-text-fill: white; -fx-border-radius: 15px; -fx-padding: 10px;");
        loginButton.setMinWidth(350); // Augmentation de la largeur
        loginButton.setOnAction(e -> controller.handleLogin(emailField.getText(), passwordField.getText()));
        addButtonHoverEffect(loginButton);

        // Bottom text
        Label signUpLabel = new Label("Vous n'avez pas de compte ? Inscrivez-vous maintenant");
        signUpLabel.setTextFill(Color.BLACK);

        // Google sign-in button
        HBox googleSignInBox = new HBox(10);
        googleSignInBox.setStyle("-fx-border-color: #FFFFFF; -fx-background-color: #FFFFFF; -fx-padding: 5px;");
        googleSignInBox.setAlignment(Pos.CENTER);

        Button googleButton = new Button("Continuer avec Google");
        googleButton.setGraphic(new ImageView(new Image(getClass().getResource("/images/Google.png").toString())));
        googleButton.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: #013A71; -fx-border-radius: 25px; -fx-padding: 10px; -fx-border-color: #FFFFFF;");
        googleButton.setMinWidth(250); // Réduction de la largeur
        addButtonHoverEffect(googleButton);

        googleSignInBox.getChildren().add(googleButton);

        // Social media icons centered
        HBox socialMediaIcons = new HBox(20);
        socialMediaIcons.setAlignment(Pos.CENTER);
        ImageView fbIcon = new ImageView(new Image(getClass().getResource("/images/Group.png").toString()));
        ImageView igIcon = new ImageView(new Image(getClass().getResource("/images/Group (1).png").toString()));
        ImageView linkedinIcon = new ImageView(new Image(getClass().getResource("/images/Group (2).png").toString()));
        socialMediaIcons.getChildren().addAll(fbIcon, igIcon, linkedinIcon);

        // Create images for the left and right bottom corners
        ImageView leftImageBottom = new ImageView(new Image(getClass().getResource("/images/left.png").toString()));
        ImageView rightImageBottom = new ImageView(new Image(getClass().getResource("/images/right.png").toString()));

        // Create HBox for bottom images
        HBox bottomImagesBox = new HBox(20); // space between images
        bottomImagesBox.setAlignment(Pos.BOTTOM_LEFT);

        // Allow images to resize based on available space
        bottomImagesBox.getChildren().addAll(leftImageBottom, rightImageBottom);

        // Ensure both images scale properly if needed
        HBox.setHgrow(leftImageBottom, Priority.ALWAYS);
        HBox.setHgrow(rightImageBottom, Priority.ALWAYS);

        // Add all to the rightPane
        rightPane.getChildren().addAll(
                logoBox, welcomeLabel, emailLabel, emailField,
                passwordLabel, passwordField, forgotPassword,
                loginButton, signUpLabel, googleSignInBox, socialMediaIcons, bottomImagesBox
        );

        // Main layout
        BorderPane root = new BorderPane();
        root.setLeft(leftImage);
        root.setCenter(rightPane);
        root.setStyle("-fx-background-radius: 20px;");

        return new Scene(root, 1280, 720);
    }

    private void addButtonHoverEffect(Button button) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(150), button);
        scaleTransition.setByX(0.1);
        scaleTransition.setByY(0.1);
        scaleTransition.setCycleCount(2);
        scaleTransition.setAutoReverse(true);

        button.setOnMouseEntered(e -> scaleTransition.playFromStart());
        button.setOnMouseExited(e -> scaleTransition.stop());
    }

    public void setController(LoginController controller) {
        controller.setView(this);
    }
}
