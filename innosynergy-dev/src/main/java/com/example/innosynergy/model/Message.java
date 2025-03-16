package com.example.innosynergy.model;

import java.sql.Timestamp;


public class Message {
    private int idMessage;
    private int idExpediteur;
    private int idDestinataire;
    private String message;
    private String reponseChatbot;
    private Timestamp dateEnvoi;    private String typeMessage;  // 'utilisateur' ou 'chatbot'
    private String statut;  // 'envoyé', 'reçu', 'lu'
    private int idConversation;  // La conversation à laquelle appartient ce message
public Message(){}
    // Constructor
    public Message(int idMessage, int idExpediteur, int idDestinataire, String message, String reponseChatbot,
                   Timestamp dateEnvoi, String typeMessage, String statut, int idConversation) {
        this.idMessage = idMessage;
        this.idExpediteur = idExpediteur;
        this.idDestinataire = idDestinataire;
        this.message = message;
        this.reponseChatbot = reponseChatbot;
        this.dateEnvoi = dateEnvoi;
        this.typeMessage = typeMessage;
        this.statut = statut;
        this.idConversation = idConversation;
    }

    // Getters and Setters
    public int getIdMessage() { return idMessage; }
    public void setIdMessage(int idMessage) { this.idMessage = idMessage; }

    public int getIdExpediteur() { return idExpediteur; }
    public void setIdExpediteur(int idExpediteur) { this.idExpediteur = idExpediteur; }

    public int getIdDestinataire() { return idDestinataire; }
    public void setIdDestinataire(int idDestinataire) { this.idDestinataire = idDestinataire; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getReponseChatbot() { return reponseChatbot; }
    public void setReponseChatbot(String reponseChatbot) { this.reponseChatbot = reponseChatbot; }

    public Timestamp getDateEnvoi() { return dateEnvoi; }
    public void setDateEnvoi(Timestamp dateEnvoi) { this.dateEnvoi = dateEnvoi; }

    public String getTypeMessage() { return typeMessage; }
    public void setTypeMessage(String typeMessage) { this.typeMessage = typeMessage; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public int getIdConversation() { return idConversation; }
    public void setIdConversation(int idConversation) { this.idConversation = idConversation; }
}
