package com.example.innosynergy.controller;

import com.example.innosynergy.utils.OpenAIChat;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;

public class ChatbotController {

    @FXML
    private VBox chatBox; // Conteneur pour les messages
    @FXML
    private TextField messageField; // Champ de saisie du message

    private final OpenAIChat openAIchat = new OpenAIChat();

    @FXML
    private void handleSendMessage() {
        String message = messageField.getText().trim();
        if (!message.isEmpty()) {
            // Ajouter le message de l'utilisateur
            addMessage("Vous: " + message, true);

            // Envoyer le message à l'API OpenAI
            try {
                String botResponse = openAIchat.sendMessage(message);
                addMessage("Chatbot: " + botResponse, false);
            } catch (IOException e) {
                e.printStackTrace();
                addMessage("Chatbot: Désolé, une erreur s'est produite.", false);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            // Effacer le champ de saisie
            messageField.clear();
        }
    }

    private void addMessage(String message, boolean isUser) {
        Text text = new Text(message);
        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle("-fx-background-color: " + (isUser ? "#d1e7dd" : "#f8d7da") +
                "; -fx-padding: 10; -fx-background-radius: 10;");
        chatBox.getChildren().add(textFlow);
    }
}