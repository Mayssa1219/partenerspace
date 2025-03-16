package com.example.innosynergy.controller;

import com.example.innosynergy.dao.ConversationDaoImpl;
import com.example.innosynergy.dao.MessageDaoImpl;
import com.example.innosynergy.dao.UserDaoImpl;
import com.example.innosynergy.model.Conversation;
import com.example.innosynergy.model.Message;
import com.example.innosynergy.model.User;
import com.example.innosynergy.utils.SessionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.List;

public class MessengerController {

    private SessionManager sessionManager;
    private ConversationDaoImpl conversationDao;
    private MessageDaoImpl messageDao;
    private UserDaoImpl utilisateurDao;

    @FXML
    private ListView<User> contactList;

    @FXML
    private VBox chatBox;

    @FXML
    private TextField messageField;

    @FXML
    private Button sendButton;

    @FXML
    private Button attachmentButton;

    @FXML
    private ImageView recipientImageView;

    @FXML
    private Label chatHeaderLabel;

    @FXML
    private Label statusLabel;

    @FXML
    private ScrollPane chatScrollPane;

    private ObservableList<User> utilisateurs;
    @FXML
    private TextField searchField;

    @FXML
    private void initialize() {
        // Initialiser les labels et l'image à vide
        chatHeaderLabel.setText("");
        statusLabel.setText("");
        recipientImageView.setImage(null); // Pas d'image par défaut
        //Initialisation du champ de recherche
        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterUsers(newValue));

        // Initialiser le SessionManager
        sessionManager = new SessionManager();

        // Initialiser les DAOs
        conversationDao = new ConversationDaoImpl();
        messageDao = new MessageDaoImpl();
        utilisateurDao = new UserDaoImpl();

        int userId = SessionManager.getUserId(sessionManager.getCurrentSessionId());
        System.out.println("Utilisateur connecté : " + userId);

        // Initialisation des boutons
        initializeButtonIcons();

        // Récupérer la liste des utilisateurs depuis la base de données
        utilisateurs = FXCollections.observableArrayList(utilisateurDao.getAllUsers());

        // Remplir la ListView avec les utilisateurs
        contactList.setItems(utilisateurs);
        contactList.setCellFactory(param -> new ContactListCell());

        // Gestion de la sélection d’un utilisateur
        contactList.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                loadChatForUser(newSelection);
            }
        });
    }


    private void initializeButtonIcons() {
        // Icône pour le bouton d'envoi
        sendButton.setGraphic(createButtonIcon("/images/send_icon.png"));
        sendButton.setText(""); // Supprime le texte du bouton

        // Icône pour le bouton de pièce jointe
        attachmentButton.setGraphic(createButtonIcon("/images/attachment_icon.png"));
        attachmentButton.setText(""); // Supprime le texte du bouton
    }

    private ImageView createButtonIcon(String imagePath) {
        Image icon = new Image(getClass().getResourceAsStream(imagePath));
        ImageView iconView = new ImageView(icon);
        iconView.setFitHeight(20);
        iconView.setFitWidth(20);
        return iconView;
    }

    @FXML
    private void handleSendButtonAction() {
        String messageText = messageField.getText();
        if (!messageText.isEmpty()) {
            User selectedUser = contactList.getSelectionModel().getSelectedItem();
            if (selectedUser != null) {
                // Vérifier si une conversation existe déjà entre l'utilisateur et le destinataire
                Conversation conversation = getOrCreateConversation(selectedUser);

                // Créer le message
                Message message = new Message();
                int expediteurId = SessionManager.getUserId(sessionManager.getCurrentSessionId());

                // Définir l'expéditeur et le destinataire
                message.setIdExpediteur(expediteurId);
                message.setIdDestinataire(selectedUser.getIdUtilisateur());
                message.setMessage(messageText);
                message.setTypeMessage("utilisateur");
                message.setStatut("envoyé");
                message.setIdConversation(conversation.getIdConversation());

                // Enregistrer le message dans la base de données
                try {
                    messageDao.envoyerMessage(message);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                // Afficher le message dans le chat
                chatBox.getChildren().add(createMessageBubble(messageText, true)); // Message de l'utilisateur (droite)
                messageField.clear();

                // Recharger les messages de la conversation après l'envoi du message
                loadChatForUser(selectedUser);
            }
        }
    }

    private Conversation getOrCreateConversation(User selectedUser) {
        Conversation conversation = null;

        // Vérifier si une conversation existe entre l'utilisateur connecté et le destinataire
        try {
            conversation = conversationDao.getConversationByUsers(SessionManager.getUserId(sessionManager.getCurrentSessionId()), selectedUser.getIdUtilisateur());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Si aucune conversation n'existe, créer une nouvelle conversation
        if (conversation == null) {
            try {
                conversation = new Conversation();
                conversation.setSujet("Conversation entre " + selectedUser.getNom() + " et " + utilisateurDao.getUserById(SessionManager.getUserId(sessionManager.getCurrentSessionId())).getNom());
                conversation.setIdUser1(SessionManager.getUserId(sessionManager.getCurrentSessionId()));
                conversation.setIdUser2(selectedUser.getIdUtilisateur());
                conversation.setDateCreation(new java.sql.Timestamp(System.currentTimeMillis()));
                conversationDao.createConversation(conversation);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conversation;
    }

    // Méthode pour charger les informations du destinataire dans le chat
    public void loadChatForUser(User utilisateur) {
        // Effacer les messages précédents pour éviter la duplication
        chatBox.getChildren().clear();

        // Mettre à jour le nom du destinataire
        chatHeaderLabel.setText(utilisateur.getNom() + " " + utilisateur.getPrenom());

        // Mettre à jour le statut de l'utilisateur
        statusLabel.setText("actif".equals(utilisateur.getStatus()) ? "Online" : "Offline");

        // Charger l'image de l'avatar du destinataire
        String imageUrl = utilisateur.getAvatar();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            recipientImageView.setImage(new Image("file:uploads/" + imageUrl));
        } else {
            recipientImageView.setImage(new Image(getClass().getResourceAsStream("/images/user.png")));
        }

        // Charger les messages
        List<Message> messages = messageDao.getMessagesForUser(
                SessionManager.getUserId(sessionManager.getCurrentSessionId()),
                utilisateur.getIdUtilisateur()
        );

        for (Message message : messages) {
            boolean isUser = message.getIdExpediteur() == SessionManager.getUserId(sessionManager.getCurrentSessionId());
            chatBox.getChildren().add(createMessageBubble(message.getMessage(), isUser));
        }

        // Faire défiler le chat vers le bas après le chargement des messages
        chatScrollPane.setVvalue(1.0);
    }



    private HBox createMessageBubble(String message, boolean isUser) {
        HBox messageBox = new HBox();
        messageBox.setStyle(isUser ? "-fx-alignment: BASELINE_RIGHT; -fx-padding: 5;" : "-fx-alignment: BASELINE_LEFT; -fx-padding: 5;");
        Label messageLabel = new Label(message);
        messageLabel.setStyle(isUser ?
                "-fx-background-color: #013A71; -fx-text-fill: white; -fx-padding: 10; -fx-background-radius: 15px;" :
                "-fx-background-color: #F3F3F3; -fx-padding: 10; -fx-background-radius: 15px;");
        messageBox.getChildren().add(messageLabel);
        return messageBox;
    }
    private void filterUsers(String keyword) {
        ObservableList<User> filteredUsers = FXCollections.observableArrayList();

        for (User user : utilisateurs) {
            if (user.getNom().toLowerCase().contains(keyword.toLowerCase()) || user.getPrenom().toLowerCase().contains(keyword.toLowerCase())) {
                filteredUsers.add(user);
            }
        }

        contactList.setItems(filteredUsers);
    }

    private class ContactListCell extends ListCell<User> {
        @Override
        protected void updateItem(User utilisateur, boolean empty) {
            super.updateItem(utilisateur, empty);

            if (empty || utilisateur == null) {
                setGraphic(null);
                return;
            }

            HBox container = new HBox(10);

            ImageView avatar = new ImageView();
            avatar.setFitHeight(45);
            avatar.setFitWidth(45);

            String avatarPath = utilisateur.getAvatar();
            if (avatarPath == null || avatarPath.isEmpty()) {
                avatar.setImage(new Image(getClass().getResourceAsStream("/images/user.png")));
            } else {
                try {
                    File file = new File("uploads/" + avatarPath);
                    if (file.exists()) {
                        Image image = new Image(file.toURI().toString(), 45, 45, true, true);
                        avatar.setImage(image);
                    } else {
                        avatar.setImage(new Image(getClass().getResourceAsStream("/images/user.png")));
                    }
                } catch (Exception e) {
                    avatar.setImage(new Image(getClass().getResourceAsStream("/images/user.png")));
                }
            }

            VBox textContainer = new VBox();
            Label nameLabel = new Label(utilisateur.getNom() + " " + utilisateur.getPrenom());
            nameLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

            // Récupérer les derniers messages pour l'utilisateur
            List<Message> messages = messageDao.getMessagesForUser(utilisateur.getIdUtilisateur(), SessionManager.getUserId(sessionManager.getCurrentSessionId()));

            Label messageLabel = new Label("Aucun message");
            String timeLabelText = ""; // Initialiser l'heure comme vide

            // Vérifier s'il y a des messages
            if (messages != null && !messages.isEmpty()) {
                Message dernierMessage = messages.get(messages.size() - 1);
                messageLabel.setText(dernierMessage.getMessage());

                // Extraire l'heure du dernier message
                java.sql.Timestamp timestamp = dernierMessage.getDateEnvoi();
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm");
                timeLabelText = sdf.format(timestamp);
            }

            messageLabel.setStyle("-fx-text-fill: gray; -fx-font-size: 12px;");
            Label timeLabel = new Label(timeLabelText);
            timeLabel.setStyle("-fx-text-fill: gray; -fx-font-size: 10px;");

            textContainer.getChildren().addAll(nameLabel, messageLabel);
            container.getChildren().addAll(avatar, textContainer, timeLabel);

            setGraphic(container);
        }
    }
}
