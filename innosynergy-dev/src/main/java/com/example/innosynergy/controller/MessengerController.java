package com.example.innosynergy.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MessengerController {

    @FXML
    private ListView<Contact> contactList;

    @FXML
    private VBox chatBox;

    @FXML
    private TextField messageField;

    @FXML
    private Button sendButton;

    @FXML
    private Button attachmentButton;

    private ObservableList<Contact> contacts;

    @FXML
    private void initialize() {
        // Ajout de l'icône au bouton d'envoi
        Image sendIcon = new Image(getClass().getResourceAsStream("/images/send_icon.png"));
        ImageView sendIconView = new ImageView(sendIcon);
        sendIconView.setFitHeight(20);
        sendIconView.setFitWidth(20);

        sendButton.setGraphic(sendIconView);
        sendButton.setText(""); // Supprime le texte du bouton

        // Liste des contacts avec image, message et heure
        contacts = FXCollections.observableArrayList(
                new Contact("Anil", "April fool's day", "9:52pm", "/images/user.png"),
                new Contact("Chuuthiya", "Baag", "12:11pm", "/images/user.png"),
                new Contact("Mary ma’am", "You have to report it...", "2:40pm", "/images/user.png"),
                new Contact("Bill Gates", "Nevermind bro", "Yesterday", "/images/user.png"),
                new Contact("Victoria H", "Okay, brother. Let’s see...", "11:12am", "/images/user.png")
        );

        contactList.setItems(contacts);
        contactList.setCellFactory(param -> new ContactListCell());

        // Gestion de la sélection d’un contact
        contactList.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                loadChatForContact(newSelection);
            }
        });
        // Icône pour le bouton de pièce jointe
        Image attachmentIcon = new Image(getClass().getResourceAsStream("/images/attachment_icon.png"));
        ImageView attachmentIconView = new ImageView(attachmentIcon);
        attachmentIconView.setFitHeight(20);
        attachmentIconView.setFitWidth(20);
        attachmentButton.setGraphic(attachmentIconView);
        attachmentButton.setText(""); // Supprime le texte du bouton
    }

    @FXML
    private void handleSendButtonAction() {
        String message = messageField.getText();
        if (!message.isEmpty()) {
            chatBox.getChildren().add(createMessageBubble(message, true)); // Message utilisateur (droite)
            messageField.clear();
        }
    }

    private HBox createMessageBubble(String message, boolean isUser) {
        Label messageLabel = new Label(message);
        messageLabel.setWrapText(true);
        messageLabel.setMaxWidth(250);
        messageLabel.setStyle(isUser
                ? "-fx-background-color: #013A71; -fx-text-fill: white; -fx-padding: 10; -fx-background-radius: 10px;"
                : "-fx-background-color: #E0E0E0; -fx-padding: 10; -fx-background-radius: 10px;");

        HBox messageBox = new HBox();
        messageBox.getChildren().add(messageLabel);

        if (isUser) {
            messageBox.setStyle("-fx-alignment: CENTER-RIGHT; -fx-padding: 5;");
            messageBox.setTranslateX(150); // Décale vers la droite
        } else {
            messageBox.setStyle("-fx-alignment: CENTER-LEFT; -fx-padding: 5;");
        }

        return messageBox;
    }

    private void loadChatForContact(Contact contact) {
        chatBox.getChildren().clear();
        chatBox.getChildren().add(new Label("Chat with " + contact.getName())); // Message temporaire
    }

    // Classe interne pour stocker les contacts
    public static class Contact {
        private final String name;
        private final String message;
        private final String time;
        private final String imageUrl;

        public Contact(String name, String message, String time, String imageUrl) {
            this.name = name;
            this.message = message;
            this.time = time;
            this.imageUrl = imageUrl;
        }

        public String getName() { return name; }
        public String getMessage() { return message; }
        public String getTime() { return time; }
        public String getImageUrl() { return imageUrl; }
    }

    // Classe pour personnaliser la cellule de la liste des contacts
    private class ContactListCell extends ListCell<Contact> {
        @Override
        protected void updateItem(Contact contact, boolean empty) {
            super.updateItem(contact, empty);

            if (empty || contact == null) {
                setGraphic(null);
                return;
            }

            HBox container = new HBox(10);

            ImageView avatar = new ImageView();
            avatar.setFitHeight(45);
            avatar.setFitWidth(45);

            try {
                Image image = new Image(getClass().getResourceAsStream(contact.getImageUrl()), 45, 45, true, true);
                avatar.setImage(image);
            } catch (Exception e) {
                System.out.println("Erreur de chargement de l'image : " + contact.getImageUrl());
                avatar.setImage(new Image(getClass().getResourceAsStream("/images/default_user.png"))); // Image par défaut
            }

            VBox textContainer = new VBox();
            Label nameLabel = new Label(contact.getName());
            nameLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

            Label messageLabel = new Label(contact.getMessage());
            messageLabel.setStyle("-fx-text-fill: gray; -fx-font-size: 12px;");

            Label timeLabel = new Label(contact.getTime());
            timeLabel.setStyle("-fx-text-fill: gray; -fx-font-size: 10px;");

            textContainer.getChildren().addAll(nameLabel, messageLabel);
            container.getChildren().addAll(avatar, textContainer, timeLabel);

            setGraphic(container);
        }
    }
}
