package com.example.innosynergy.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;

import java.io.File;

public class Chatbot extends StackPane {
    private TextArea chatArea;
    private TextField userInput;

    public Chatbot() {
        // Top bar with close, minimize, maximize buttons
        HBox topBar = new HBox(5);
        topBar.setPadding(new Insets(5));
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setStyle("-fx-background-color: #F5F5F5;");
        Circle closeButton = new Circle(6, Color.web("#FFCDD2")); // Couleur rouge avec code hexadécimal
        Circle minimizeButton = new Circle(6, Color.web("#E1BEE7")); // Couleur jaune avec code hexadécimal
        Circle maximizeButton = new Circle(6, Color.web("#BBDEFB"));

        Label titleLabel = new Label("Chat with us!");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        titleLabel.setTextFill(Color.GRAY);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        topBar.getChildren().addAll(closeButton, minimizeButton, maximizeButton, spacer, titleLabel);

        // Chatbot info
        HBox chatbotInfo = new HBox(10);
        chatbotInfo.setPadding(new Insets(10));
        chatbotInfo.setAlignment(Pos.CENTER_LEFT);

        ImageView avatar = new ImageView(new Image("https://storage.googleapis.com/a1aa/image/IGKzp5As2ZmdTSe-bpil1PXllH1xR4pps_oSpF1MREc.jpg"));
        avatar.setFitWidth(40);
        avatar.setFitHeight(40);
        avatar.setClip(new Circle(20, 20, 20));

        VBox chatbotDetails = new VBox(2);
        Label chatbotName = new Label("Chatbot");
        chatbotName.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        chatbotName.setTextFill(Color.GRAY);

        Label chatbotRole = new Label("Support Agent");
        chatbotRole.setFont(Font.font("Arial", 12));
        chatbotRole.setTextFill(Color.LIGHTGRAY);

        chatbotDetails.getChildren().addAll(chatbotName, chatbotRole);

        HBox thumbs = new HBox(5);
        thumbs.setAlignment(Pos.CENTER_RIGHT);
        thumbs.getChildren().addAll(new Label("\uD83D\uDC4D"), new Label("\uD83D\uDC4E"));

        Region spacer2 = new Region();
        HBox.setHgrow(spacer2, Priority.ALWAYS);

        chatbotInfo.getChildren().addAll(avatar, chatbotDetails, spacer2, thumbs);

        // Drag and drop area
        VBox dragDropArea = new VBox(10);
        dragDropArea.setPadding(new Insets(20));
        dragDropArea.setAlignment(Pos.CENTER);
        dragDropArea.setStyle("-fx-border-color: #B3D4FC; -fx-border-width: 2; -fx-border-style: dashed; -fx-background-color: #EAF4FF;");

        Label cloudIcon = new Label("\u2601");
        cloudIcon.setFont(Font.font("Arial", FontWeight.BOLD, 40));
        cloudIcon.setTextFill(Color.LIGHTBLUE);

        Label dragDropText = new Label("Drag & drop files or Browse");
        dragDropText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        dragDropText.setTextFill(Color.LIGHTBLUE);

        // Action de clic sur le texte pour ouvrir le sélecteur de fichiers
        dragDropText.setOnMouseClicked(e -> openFileChooser());

        Label supportedFormats = new Label("Supported formats: PDF, Word, and PNG");
        supportedFormats.setFont(Font.font("Arial", 12));
        supportedFormats.setTextFill(Color.LIGHTGRAY);

        dragDropArea.getChildren().addAll(cloudIcon, dragDropText, supportedFormats);

        // Chat area
        chatArea = new TextArea();
        chatArea.setEditable(false);
        chatArea.setWrapText(true);
        chatArea.setStyle("-fx-background-color: #F5F5F5; -fx-font-size: 14px;");

        // User input area
        HBox userInputArea = new HBox(10);
        userInputArea.setPadding(new Insets(10));
        userInputArea.setAlignment(Pos.CENTER_LEFT);
        userInputArea.setStyle("-fx-background-color: #F5F5F5;");

        userInput = new TextField();
        userInput.setPromptText("Write a message...");
        userInput.setPrefWidth(300); // Augmenter la largeur de userInput
        userInput.setOnAction(e -> sendMessage());

        // Smile icon replaced with an image
        ImageView smileImage = new ImageView(new Image("images/smile icon.png"));  // Change the file path if needed
        smileImage.setFitHeight(20);
        smileImage.setFitWidth(20);

        // Send icon replaced with an image
        ImageView sendImage = new ImageView(new Image("images/send icon.png"));  // Change the file path if needed
        sendImage.setFitHeight(20);
        sendImage.setFitWidth(20);

        // Ajouter un gestionnaire d'événements pour envoyer le message lors du clic sur l'icône
        sendImage.setOnMouseClicked(e -> sendMessage());

        userInputArea.getChildren().addAll(userInput, smileImage, sendImage);

        // Footer
        Label footer = new Label("Powered by Sahil Dobariya");
        footer.setFont(Font.font("Arial", 10));
        footer.setTextFill(Color.LIGHTGRAY);
        footer.setPadding(new Insets(5));
        footer.setAlignment(Pos.CENTER);

        // Layout with rounded borders
        VBox layout = new VBox(topBar, chatbotInfo, dragDropArea, chatArea, userInputArea, footer);
        layout.setPadding(new Insets(10));
        layout.setSpacing(10);

        // Appliquer un border-radius et un fond arrondi sur toute la scène
        layout.setStyle("-fx-background-color: white; -fx-border-radius: 15; -fx-background-radius: 15; -fx-border-color: #B3D4FC; -fx-border-width: 2;");

        this.getChildren().add(layout);
    }

    private void openFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf"),
                new FileChooser.ExtensionFilter("Word Files", "*.docx"),
                new FileChooser.ExtensionFilter("PNG Files", "*.png")
        );
        File selectedFile = fileChooser.showOpenDialog(this.getScene().getWindow());
        if (selectedFile != null) {
            chatArea.appendText("You selected file: " + selectedFile.getName() + "\n");
            // Here you can add logic to send the file to the AI or server
        }
    }

    private void sendMessage() {
        String message = userInput.getText();
        if (!message.isEmpty()) {
            chatArea.appendText("You: " + message + "\n");
            userInput.clear();
            // Here you can add logic to get a response from the AI
            String response = getAIResponse(message);
            chatArea.appendText("AI: " + response + "\n");
        }
    }

    private String getAIResponse(String message) {
        // Placeholder for AI response logic
        return "This is a placeholder response to: " + message;
    }
}
