package com.example.innosynergy.controller;

import com.example.innosynergy.model.ClientData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Date;

public class ClientController {
    private ObservableList<ClientData> clientDataList;

    public ClientController() {
        clientDataList = FXCollections.observableArrayList(
                new ClientData("#20462", "JCI", "+216 53183360", "contact@jci.com", "Monastir", new Date(), "actif"),
                new ClientData("#18933", "TravelToDo", "+216 53183360", "contact@travel.com", "Tunis", new Date(), "actif"),
                new ClientData("#45169", "Dominic", "+216 53183360", "contact@dominic.com", "Sousse", new Date(), "bloqué"),
                new ClientData("#20462", "JCI", "+216 53183360", "contact@jci.com", "Monastir", new Date(), "actif"),
                new ClientData("#18933", "TravelToDo", "+216 53183360", "contact@travel.com", "Tunis", new Date(), "actif"),
                new ClientData("#45169", "Dominic", "+216 53183360", "contact@dominic.com", "Sousse", new Date(), "bloqué"),
                new ClientData("#20462", "JCI", "+216 53183360", "contact@jci.com", "Monastir", new Date(), "actif"),
                new ClientData("#18933", "TravelToDo", "+216 53183360", "contact@travel.com", "Tunis", new Date(), "actif"),
                new ClientData("#45169", "Dominic", "+216 53183360", "contact@dominic.com", "Sousse", new Date(), "bloqué"),
                new ClientData("#20462", "JCI", "+216 53183360", "contact@jci.com", "Monastir", new Date(), "actif"),
                new ClientData("#18933", "TravelToDo", "+216 53183360", "contact@travel.com", "Tunis", new Date(), "actif"),
                new ClientData("#45169", "Dominic", "+216 53183360", "contact@dominic.com", "Sousse", new Date(), "bloqué"),
                new ClientData("#20462", "JCI", "+216 53183360", "contact@jci.com", "Monastir", new Date(), "actif"),
                new ClientData("#18933", "TravelToDo", "+216 53183360", "contact@travel.com", "Tunis", new Date(), "actif"),
                new ClientData("#45169", "Dominic", "+216 53183360", "contact@dominic.com", "Sousse", new Date(), "bloqué"),
                new ClientData("#20462", "JCI", "+216 53183360", "contact@jci.com", "Monastir", new Date(), "actif"),
                new ClientData("#18933", "TravelToDo", "+216 53183360", "contact@travel.com", "Tunis", new Date(), "actif"),
                new ClientData("#45169", "Dominic", "+216 53183360", "contact@dominic.com", "Sousse", new Date(), "bloqué"),
                new ClientData("#20462", "JCI", "+216 53183360", "contact@jci.com", "Monastir", new Date(), "actif"),
                new ClientData("#18933", "TravelToDo", "+216 53183360", "contact@travel.com", "Tunis", new Date(), "actif"),
                new ClientData("#45169", "Dominic", "+216 53183360", "contact@dominic.com", "Sousse", new Date(), "bloqué"),
                new ClientData("#20462", "JCI", "+216 53183360", "contact@jci.com", "Monastir", new Date(), "actif"),
                new ClientData("#18933", "TravelToDo", "+216 53183360", "contact@travel.com", "Tunis", new Date(), "actif"),
                new ClientData("#45169", "Dominic", "+216 53183360", "contact@dominic.com", "Sousse", new Date(), "bloqué")

                // Add more sample data as needed
        );
    }

    public ObservableList<ClientData> getClientDataList() {
        return clientDataList;
    }

    public void addClient(ClientData clientData) {
        clientDataList.add(clientData);
    }

    public void removeClient(ClientData clientData) {
        clientDataList.remove(clientData);
    }

    public void updateClient(ClientData oldClient, ClientData newClient) {
        int index = clientDataList.indexOf(oldClient);
        if (index >= 0) {
            clientDataList.set(index, newClient);
        }
    }
}