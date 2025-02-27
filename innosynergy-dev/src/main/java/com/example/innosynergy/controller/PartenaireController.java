package com.example.innosynergy.controller;

import com.example.innosynergy.model.PartenaireData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PartenaireController {
    private ObservableList<PartenaireData> partenaireDataList;

    public PartenaireController() {
        partenaireDataList = FXCollections.observableArrayList();
        // Exemple d'ajout de données de partenaire
        partenaireDataList.add(new PartenaireData("1", "Partenaire 1", "1234567890", "email1@example.com", "Adresse 1", "actif", "Entreprise 1", "2022-01-01", "2023-01-01", "Activité 1"));
        partenaireDataList.add(new PartenaireData("2", "Partenaire 2", "0987654321", "email2@example.com", "Adresse 2", "bloqué", "Entreprise 2", "2022-02-01", "2023-02-01", "Activité 2"));
        partenaireDataList.add(new PartenaireData("3", "Partenaire 1", "1234567890", "email1@example.com", "Adresse 1", "actif", "Entreprise 1", "2022-01-01", "2023-01-01", "Activité 1"));
        partenaireDataList.add(new PartenaireData("4", "Partenaire 2", "0987654321", "email2@example.com", "Adresse 2", "bloqué", "Entreprise 2", "2022-02-01", "2023-02-01", "Activité 2"));
        partenaireDataList.add(new PartenaireData("5", "Partenaire 1", "1234567890", "email1@example.com", "Adresse 1", "actif", "Entreprise 1", "2022-01-01", "2023-01-01", "Activité 1"));
        partenaireDataList.add(new PartenaireData("6", "Partenaire 2", "0987654321", "email2@example.com", "Adresse 2", "bloqué", "Entreprise 2", "2022-02-01", "2023-02-01", "Activité 2"));
        partenaireDataList.add(new PartenaireData("7", "Partenaire 1", "1234567890", "email1@example.com", "Adresse 1", "actif", "Entreprise 1", "2022-01-01", "2023-01-01", "Activité 1"));
        partenaireDataList.add(new PartenaireData("8", "Partenaire 2", "0987654321", "email2@example.com", "Adresse 2", "bloqué", "Entreprise 2", "2022-02-01", "2023-02-01", "Activité 2"));
        partenaireDataList.add(new PartenaireData("9", "Partenaire 1", "1234567890", "email1@example.com", "Adresse 1", "actif", "Entreprise 1", "2022-01-01", "2023-01-01", "Activité 1"));
        partenaireDataList.add(new PartenaireData("10", "Partenaire 2", "0987654321", "email2@example.com", "Adresse 2", "bloqué", "Entreprise 2", "2022-02-01", "2023-02-01", "Activité 2"));
        partenaireDataList.add(new PartenaireData("11", "Partenaire 1", "1234567890", "email1@example.com", "Adresse 1", "actif", "Entreprise 1", "2022-01-01", "2023-01-01", "Activité 1"));
        partenaireDataList.add(new PartenaireData("12", "Partenaire 2", "0987654321", "email2@example.com", "Adresse 2", "bloqué", "Entreprise 2", "2022-02-01", "2023-02-01", "Activité 2"));
        partenaireDataList.add(new PartenaireData("13", "Partenaire 1", "1234567890", "email1@example.com", "Adresse 1", "actif", "Entreprise 1", "2022-01-01", "2023-01-01", "Activité 1"));
        partenaireDataList.add(new PartenaireData("14", "Partenaire 2", "0987654321", "email2@example.com", "Adresse 2", "bloqué", "Entreprise 2", "2022-02-01", "2023-02-01", "Activité 2"));
        partenaireDataList.add(new PartenaireData("15", "Partenaire 1", "1234567890", "email1@example.com", "Adresse 1", "actif", "Entreprise 1", "2022-01-01", "2023-01-01", "Activité 1"));
        partenaireDataList.add(new PartenaireData("2", "Partenaire 2", "0987654321", "email2@example.com", "Adresse 2", "bloqué", "Entreprise 2", "2022-02-01", "2023-02-01", "Activité 2"));
        partenaireDataList.add(new PartenaireData("1", "Partenaire 1", "1234567890", "email1@example.com", "Adresse 1", "actif", "Entreprise 1", "2022-01-01", "2023-01-01", "Activité 1"));
        partenaireDataList.add(new PartenaireData("2", "Partenaire 2", "0987654321", "email2@example.com", "Adresse 2", "bloqué", "Entreprise 2", "2022-02-01", "2023-02-01", "Activité 2"));
        partenaireDataList.add(new PartenaireData("1", "Partenaire 1", "1234567890", "email1@example.com", "Adresse 1", "actif", "Entreprise 1", "2022-01-01", "2023-01-01", "Activité 1"));
        partenaireDataList.add(new PartenaireData("2", "Partenaire 2", "0987654321", "email2@example.com", "Adresse 2", "bloqué", "Entreprise 2", "2022-02-01", "2023-02-01", "Activité 2"));
    }

    public ObservableList<PartenaireData> getPartenaireDataList() {
        return partenaireDataList;
    }

    public void addPartenaire(PartenaireData partenaire) {
        partenaireDataList.add(partenaire);
    }

    public void removePartenaire(PartenaireData partenaire) {
        partenaireDataList.remove(partenaire);
    }

    public void updatePartenaire(PartenaireData oldPartenaire, PartenaireData newPartenaire) {
        int index = partenaireDataList.indexOf(oldPartenaire);
        if (index != -1) {
            partenaireDataList.set(index, newPartenaire);
        }
    }
}