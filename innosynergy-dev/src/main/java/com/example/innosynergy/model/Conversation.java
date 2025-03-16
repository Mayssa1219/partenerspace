package com.example.innosynergy.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Conversation {
    private int idConversation;
    private int idUser1;
    private int idUser2;
    private String sujet;
    private Timestamp dateCreation;
    private List<Message> messages;
public  Conversation(){}
    // Constructor
    public Conversation(int idConversation, int idUser1, int idUser2, String sujet, Timestamp dateCreation, List<Message> messages) {
        this.idConversation = idConversation;
        this.idUser1 = idUser1;
        this.idUser2 = idUser2;
        this.sujet = sujet;
        this.dateCreation = dateCreation;
        // Assurez-vous que la liste de messages n'est jamais null
        this.messages = messages != null ? messages : new ArrayList<>();
    }


    // Getters and Setters
    public int getIdConversation() { return idConversation; }
    public void setIdConversation(int idConversation) { this.idConversation = idConversation; }

    public int getIdUser1() { return idUser1; }
    public void setIdUser1(int idUser1) { this.idUser1 = idUser1; }

    public int getIdUser2() { return idUser2; }
    public void setIdUser2(int idUser2) { this.idUser2 = idUser2; }

    public String getSujet() { return sujet; }
    public void setSujet(String sujet) { this.sujet = sujet; }

    public Timestamp getDateCreation() { return dateCreation; }
    public void setDateCreation(Timestamp dateCreation) { this.dateCreation = dateCreation; }

    public List<Message> getMessages() { return messages; }
    public void setMessages(List<Message> messages) {
        this.messages = messages != null ? messages : new ArrayList<>();
    }

    // Méthode pour ajouter un message à la conversation
    public void addMessage(Message message) {
        if (this.messages == null) {
            this.messages = new ArrayList<>();
        }
        this.messages.add(message);
    }

    // Méthode toString pour une représentation lisible de la conversation
    @Override
    public String toString() {
        return "Conversation{" +
                "idConversation=" + idConversation +
                ", idUser1=" + idUser1 +
                ", idUser2=" + idUser2 +
                ", sujet='" + sujet + '\'' +
                ", dateCreation=" + dateCreation +
                ", messages=" + messages.size() + " messages" +
                '}';
    }
}
